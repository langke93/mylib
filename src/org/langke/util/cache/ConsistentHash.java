package org.langke.util.cache;
 
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
 
import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;


public class ConsistentHash {

	private static final Integer EXE_TIMES = 10;
	
	private static final Integer VIRTUAL_NODE_COUNT = 160;
	
    private final  HashFunction hashFunction = new HashFunction() ;
    /**
     * 虚拟节点是实际节点在 hash 空间的复制品
     */
    private final int numberOfReplicas;
    /**
     * 环形hash空间
     */
    private final SortedMap<Long, String> circle = new TreeMap<Long, String>();
    
    public ConsistentHash(int numberOfReplicas, String[] servers) {
        this.numberOfReplicas = numberOfReplicas;
        for (int i = 0; i < servers.length; i++) {
            add(servers[i]);
        }
        System.out.println("circle:"+circle);
    }
    /**
     * 在hash空间添加虚拟节点对应的实际节点
     * @param server
     */
    public void add(String server) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(hashFunction.hash(server.toString() + "-" + i), server);
        }
    }
    /**
     * 删除hash空间中虚拟节点对应的实际节点
     * @param server
     */
    public void remove(String server) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hashFunction.hash(server.toString() + i));
        }
    }
    /**
     * 依据key查询对应的实际节点
     * @param key
     * @return
     */
    public String get(Object key) {
        if (circle.isEmpty()) {
            return null;
        }
        Long hash = hashFunction.hash(key.toString());
//        System.out.println(key+"-->"+hash);
        if (!circle.containsKey(hash)) {
            SortedMap<Long, String> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }
    
      void test(ConsistentHash consistentHash,String servers[] ,Integer weights[]){
        SockIOPool pool = SockIOPool.getInstance("test");
        pool.setHashingAlg(SockIOPool.CONSISTENT_HASH);
        //pool.setHashingAlg(SockIOPool.NEW_COMPAT_HASH);
        pool.setServers( servers );
        pool.setFailover( true );
        pool.setInitConn( 10 );
        pool.setMinConn( 5 );
        pool.setMaxConn( 250 );
        //pool.setMaintSleep( 30 );
        pool.setNagle( false );
        pool.setSocketTO( 3000 );
        pool.setAliveCheck( true );
        pool.setWeights(weights);
        pool.initialize();
        MemCachedClient mcc = new MemCachedClient("test");
        Map<String, Integer> nodeRecord = new HashMap<String, Integer>();
        //consistentHash.remove(servers[1]); 
        System.out.println("consistentHash.circle:"+consistentHash.circle);
        for (int i = 0; i < EXE_TIMES; i++) {
            String key = "object" + i;
            String obj1Cache = consistentHash.get(key);
            System.out.println(key + " node-->" + obj1Cache);
            //统计分布
            Integer counter = nodeRecord.get(obj1Cache);
            if(counter==null)
            	nodeRecord.put(obj1Cache, 1);
            else
            	nodeRecord.put(obj1Cache, counter+1);
            String value1 = key + " Hello!";
            String result = null;
            boolean success = false;
            result = (String) mcc.get(key);
            if(result==null){
            	success = mcc.set(key, value1);
            	result = (String) mcc.get(key);
            }
            System.out.println(String.format("set( %s ): %s", key, success));
            System.out.println(String.format("get( %s ): %s", key, result));
        } 
		for (Map.Entry<String, Integer> entry : nodeRecord.entrySet()) {
			System.out.println("Node name :" + entry.getKey() + " - Times : " + entry.getValue() + " - Percent : " + (float)entry.getValue() / EXE_TIMES * 100 + "%");
		}

        //System.out.println("flushALl:"+mcc.flushAll());
    }
    public static void main(String[] args) {
    	String[] servers = { "192.168.11.210:11211","192.168.200.121:11211","192.168.200.62:11211"};//
        ConsistentHash consistentHash = new ConsistentHash(VIRTUAL_NODE_COUNT,servers);
        Integer[] weights = null;//{5,5,5};
        consistentHash.test(consistentHash,servers,weights);
    }
} 