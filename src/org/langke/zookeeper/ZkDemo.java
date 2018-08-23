package org.langke.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZkDemo {
     // 会话超时时间，设置为与系统默认时间一致
     private static final int SESSION_TIMEOUT=30000;
    
     // 创建 ZooKeeper 实例
     ZooKeeper zk;
    
     // 创建 Watcher 实例
     Watcher wh=new Watcher(){
            public void process(org.apache.zookeeper.WatchedEvent event)
            {
                    System.out.println("watcher:"+event.toString());
            }
     };
    
     // 初始化 ZooKeeper 实例
     private void createZKInstance()  
     {             
            try {
                zk=new ZooKeeper("dubbo2:2181,dubbo1:2181,dubbo3:2181",ZkDemo.SESSION_TIMEOUT,this.wh);
            } catch (IOException e) {
                e.printStackTrace();
            }
           
     }
    
     private void ZKOperations()  
     {
         System.out.println(java.security.Security.getProperty("networkaddress.cache.ttl"));
         System.out.println(java.security.Security.getProperty("networkaddress.cache.negative.ttl"));
         System.out.println(java.security.Security.getProperty("sun.net.inetaddr.ttl"));
         System.out.println(java.security.Security.getProperty("sun.net.inetaddr.negative.ttl"));
            //System.out.println("\n1. 创建 ZooKeeper 节点 (znode ： zoo2, 数据： myData2 ，权限： OPEN_ACL_UNSAFE ，节点类型： Persistent");
            //zk.create("/zoo2","myData2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
           
            //System.out.println("\n2. 查看是否创建成功： ");
            //System.out.println(new String(zk.getData("/zoo2",false,null)));
                           
            //System.out.println("\n3. 修改节点数据 ");
            //zk.setData("/zoo2", "shenlan211314".getBytes(), -1);
           
            System.out.println("\n4. 查看是否修改成功： ");
            try {
                System.out.println(new String(zk.getData("/zoo2", false, null)));
            } catch (KeeperException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
                           
       /*     System.out.println("\n5. 删除节点 ");
            zk.delete("/zoo2", -1);*/
           
            //System.out.println("\n6. 查看节点是否被删除： ");
            //System.out.println(" 节点状态： ["+zk.exists("/zoo2", false)+"]");
     }
     
     private void ZKClose()  
     {
            try {
                zk.close();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
     }
    
     public static void main(String[] args)  {
            ZkDemo dm=new ZkDemo();
            while(true){
                dm.createZKInstance( );
                dm.ZKOperations();
                dm.ZKClose();
                 try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
     }
}