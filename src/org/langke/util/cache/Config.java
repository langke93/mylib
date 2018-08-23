package org.langke.util.cache;
 
public class Config { 
	
	/**
	 * Memcached service IP
	 * */
	public static String MEMCACHED_IP="192.168.202.103:11211";
	
	/**
	 * Memcached的数据，默认有效时间
	 * */
	public static long MEMCACHED_TIME=600000;
	
	   /**
     * Memcached的数据，只读长时间保存  s秒
     * */
    public static long MEMCACHED_LONG_TIME = 43200 * 1000;
	
	/**
	 * 是否使用Memcached
	 * */
	public static boolean ISUSED_MEMCACHED=true;
	
	/**
	 * 使用Memcached的方法
	 * */
	public static String USED_MEMCACHED_NAME="VideoInfo";
	
	   /**
     * 使用Memcached key 前缀
     * */
    public static String MEMCACHED_PRE="upv1_";
	
}
