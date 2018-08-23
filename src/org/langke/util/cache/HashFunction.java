package org.langke.util.cache;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;


/** 
 *  hash函数 实现 NATIVE_HASH	NEW_COMPAT_HASH 	CONSISTENT_HASH
 * @author langke
 *
 */
public class HashFunction{
	MessageDigest md5;
	public Long hash(String value){
		return md5HashingAlg(value);
		//return newCompatHashingAlg(value);
		//return (long)value.hashCode() & 0xffffffffL;/* NATIVE_HASH Truncate to 32-bits */
	}
	private long newCompatHashingAlg(String key){
		CRC32 checksum = new CRC32();
		checksum.update(key.getBytes());
		long crc = checksum.getValue();
        return (crc >> 16) & 0x7fff;
	}
	private ThreadLocal<MessageDigest> MD5 = new ThreadLocal<MessageDigest>() {
		@Override
		protected final MessageDigest initialValue() {
			try {
				return MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalStateException("++++ no md5 algorythm found");
			}
		}
	};
	//CONSISTENT_HASH
	private  long md5HashingAlg(String key) {
		MessageDigest md5 = MD5.get();
		md5.reset();
		md5.update(key.getBytes());
		byte[] bKey = md5.digest();
		long res = ((long) (bKey[3] & 0xFF) << 24) | ((long) (bKey[2] & 0xFF) << 16) | ((long) (bKey[1] & 0xFF) << 8)
				| (long) (bKey[0] & 0xFF);
		return res;
	}
}
