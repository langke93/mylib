package org.langke.util.security; 
import java.io.IOException;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/*
 * IDEA数据加密算法是由中国学者来学嘉博士和著名的密码专家 James L. Massey 于1990年联合提出的。
 * 它的明文和密文都是64比特，但密钥长为128比特。IDEA 是作为迭代的分组密码实现的，使用 128 位的密钥和 8 个循环。
 * 这比 DES 提供了更多的 安全性，但是在选择用于 IDEA 的密钥时，应该排除那些称为“弱密钥”的密钥。
 * DES 只有四个弱密钥和 12 个次弱密钥，而 IDEA 中的弱密钥数相当可观，有 2 的 51 次方个。
 * 但是，如果密钥的总数非常大，达到 2 的 128 次方个，那么仍有 2 的 77 次方个密钥可供选择。IDEA 被认为是极为安全的。
 * 使用 128 位的密钥，蛮力攻击中需要进行的测试次数与 DES 相比会明显增大，甚至允许对弱密钥测试。
 * 而且，它本身 也显示了它尤其能抵抗专业形式的分析性攻击。
 */
public class IDEA {
	
	 private byte[] Encrypt(byte[] bytekey, byte[] inputBytes, boolean flag) {
		 byte[] encryptCode = new byte[8];
	
		 // 分解子密钥
		 int[] key = get_subkey(flag, bytekey);
	
		 // 进行加密操作
		 encrypt(key, inputBytes, encryptCode);
	
		 // 返回加密数据
		 return encryptCode;
	 }

	 private int bytesToInt(byte[] inBytes, int startPos) {
		 return ((inBytes[startPos] << 8) & 0xff00) +
		 	(inBytes[startPos + 1] & 0xff);
	 }

	 private void intToBytes(int inputInt, byte[] outBytes, int startPos) {
		 outBytes[startPos] = (byte) (inputInt >>> 8);
		 outBytes[startPos + 1] = (byte) inputInt;
	 }

	 private int x_multiply_y(int x, int y) {
		 if (x == 0) {
			 x = 0x10001 - y;
		 } else if (y == 0) {
			 x = 0x10001 - x;
		 } else {
			 int tmp = x * y;
			 y = tmp & 0xffff;
			 x = tmp >>> 16;
			 x = (y - x) + ((y < x) ? 1 : 0);
		 }
	
		 return x & 0xffff;
	 }

	 private void encrypt(int[] key, byte[] inbytes, byte[] outbytes) {
		 int k = 0;
		 int a = bytesToInt(inbytes, 0);
		 int b = bytesToInt(inbytes, 2);
		 int c = bytesToInt(inbytes, 4);
		 int d = bytesToInt(inbytes, 6);
		 for (int i = 0; i < 8; i++) {
			 a = x_multiply_y(a, key[k++]);
			 b += key[k++];
			 b &= 0xffff;
			 c += key[k++];
			 c &= 0xffff;
			 d = x_multiply_y(d, key[k++]);
			 int tmp1 = b;
			 int tmp2 = c;
			 c ^= a;
			 b ^= d;
			 c = x_multiply_y(c, key[k++]);
			 b += c;
			 b &= 0xffff;
			 b = x_multiply_y(b, key[k++]);
			 c += b;
			 c &= 0xffff;
			 a ^= b;
			 d ^= c;
			 b ^= tmp2;
			 c ^= tmp1;
		 }
		 intToBytes(x_multiply_y(a, key[k++]), outbytes, 0);
		 intToBytes(c + key[k++], outbytes, 2);
		 intToBytes(b + key[k++], outbytes, 4);
		 intToBytes(x_multiply_y(d, key[k]), outbytes, 6);
	 }

	 private int[] encrypt_subkey(byte[] byteKey) {
		 int[] key = new int[52];
	
		 if (byteKey.length < 16) {
			 byte[] tmpkey = new byte[16];
			 System.arraycopy(byteKey, 0, tmpkey,
			 tmpkey.length - byteKey.length, byteKey.length);
			 byteKey = tmpkey;
		 }
		 for (int i = 0; i < 8; i++) {
			 key[i] = bytesToInt(byteKey, i * 2);
		 }
		 for (int j = 8; j < 52; j++) {
			 if ((j & 0x7) < 6) {
				 key[j] = (((key[j - 7] & 0x7f) << 9) | (key[j - 6] >> 7)) &
				 0xffff;
			 } else if ((j & 0x7) == 6) {
				 key[j] = (((key[j - 7] & 0x7f) << 9) | (key[j - 14] >> 7)) &
				 0xffff;
			 } else {
				 key[j] = (((key[j - 15] & 0x7f) << 9) | (key[j - 14] >> 7)) &
				 0xffff;
			 }
		 }
		 return key;
	 }

	 private int fun_a(int a) {
		 if (a < 2) {
			 return a;
		 }
		 int b = 1;
		 int c = 0x10001 / a;
		 for (int i = 0x10001 % a; i != 1;) {
			 int d = a / i;
			 a %= i;
			 b = (b + (c * d)) & 0xffff;
			 if (a == 1) {
				 return b;
			 }
			 d = i / a;
			 i %= a;
			 c = (c + (b * d)) & 0xffff;
		 }
		 return (1 - c) & 0xffff;
	 }

	 private int fun_b(int b) {
		 return (0 - b) & 0xffff;
	 }

	 private int[] uncrypt_subkey(int[] key) {
		 int dec = 52;
		 int asc = 0;
		 int[] unkey = new int[52];
		 int aa = fun_a(key[asc++]);
		 int bb = fun_b(key[asc++]);
		 int cc = fun_b(key[asc++]);
		 int dd = fun_a(key[asc++]);
		 unkey[--dec] = dd;
		 unkey[--dec] = cc;
		 unkey[--dec] = bb;
		 unkey[--dec] = aa;
	
		 for (int k1 = 1; k1 < 8; k1++) {
			 aa = key[asc++];
			 bb = key[asc++];
			 unkey[--dec] = bb;
			 unkey[--dec] = aa;
			 aa = fun_a(key[asc++]);
			 bb = fun_b(key[asc++]);
			 cc = fun_b(key[asc++]);
			 dd = fun_a(key[asc++]);
			 unkey[--dec] = dd;
			 unkey[--dec] = bb;
			 unkey[--dec] = cc;
			 unkey[--dec] = aa;
		 }
		 aa = key[asc++];
		 bb = key[asc++];
		 unkey[--dec] = bb;
		 unkey[--dec] = aa;
		 aa = fun_a(key[asc++]);
		 bb = fun_b(key[asc++]);
		 cc = fun_b(key[asc++]);
		 dd = fun_a(key[asc]);
		 unkey[--dec] = dd;
		 unkey[--dec] = cc;
		 unkey[--dec] = bb;
		 unkey[--dec] = aa;
		 return unkey;
	 }

	 private int[] get_subkey(boolean flag, byte[] bytekey) {
		 if (flag) {
			 return encrypt_subkey(bytekey);
		 } else {
			 return uncrypt_subkey(encrypt_subkey(bytekey));
		 }
	 }

	 private byte[] ByteDataFormat(byte[] data, int unit) {
		 int len = data.length;
		 int padlen = unit - (len % unit);
		 int newlen = len + padlen;
		 byte[] newdata = new byte[newlen];
		 System.arraycopy(data, 0, newdata, 0, len);
		 for (int i = len; i < newlen; i++)
			 newdata[i] = (byte) padlen;
		 return newdata;
	 }

	 public byte[] IdeaEncrypt(byte[] idea_key, byte[] idea_data, boolean flag) {
		 byte[] format_key = ByteDataFormat(idea_key, 16);
		 byte[] format_data = ByteDataFormat(idea_data, 8);
		 int datalen = format_data.length;
		 int unitcount = datalen / 8;
		 byte[] result_data = new byte[datalen];
		 for (int i = 0; i < unitcount; i++) {
			 byte[] tmpkey = new byte[16];
			 byte[] tmpdata = new byte[8];
			 System.arraycopy(format_key, 0, tmpkey, 0, 16);
			 System.arraycopy(format_data, i * 8, tmpdata, 0, 8);
			 byte[] tmpresult = Encrypt(tmpkey, tmpdata, flag);
			 System.arraycopy(tmpresult, 0, result_data, i * 8, 8);
		 }
		 return result_data;
	 }

	 /**
	  * 将字节数组转换成字符串
	  * @param str
	  * @return
	  */
	 public String byteToString(byte[] str){
		  BASE64Encoder enc=new BASE64Encoder();
		  return  enc.encode(str);
	 }
	 
	 /**
	  * 将字符串转换成字节数组
	  * @param str
	  * @return
	  */
	 public byte[] stringTobyte(String str){
		 BASE64Decoder dec=new BASE64Decoder(); 
		  try {
			  return  dec.decodeBuffer(str);
		  } catch (IOException e1) {
		    e1.printStackTrace();
		  }
		  return null;
	 }
	 /**
	  * 加密方法
	  * @param key 密钥
	  * @param data加密数据
	  * @return 加密后的字节数组
	  */
	 public byte[] encrypt(String key,String data){
		 byte[] bytekey = key.getBytes();
		 byte[] bytedata = data.getBytes();
		 byte[] b = new byte[bytedata.length];
		 IDEA idea = new IDEA();
		 byte[] encryptdata = idea.IdeaEncrypt(bytekey, bytedata, true);
		 return encryptdata;
	 }
	 
	 /**
	  * 解密方法
	  * @param key 密钥
	  * @param encryptdata加密后的数据
	  * @return
	  */
	 public byte[] decrypt(String key ,byte[] encryptdata ){
		 IDEA idea = new IDEA();
		 byte[] bytekey = key.getBytes();
		 byte[] decryptdata = idea.IdeaEncrypt(bytekey, encryptdata, false);
		 return decryptdata;
	 }
	 
	 public static void main(String[] args){
		 
		 IDEA idea = new IDEA();
		 byte[] encryptdata = idea.encrypt("123456","zxbsky1981");
		 for(int i=0;i<encryptdata.length;i++){
			 System.out.print(" "+encryptdata[i]);
			 
		 }
		 String encryptstr = idea.byteToString(encryptdata);
		 System.out.println("---"+encryptstr);
		 byte[] decrypt = idea.stringTobyte(encryptstr);
		 for(int i=0;i<decrypt.length;i++){
			 System.out.print(" "+decrypt[i]);
			 
		 }
		 byte[] decrypt1 = idea.decrypt("123456",decrypt);
		 String decryptstr = idea.byteToString(decrypt1);
		 System.out.println("---"+new String(decrypt1));
		 
		
	 }
	
} 
	 

	 
	 

