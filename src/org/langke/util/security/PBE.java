package org.langke.util.security;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.*;
import sun.misc.*;
/**
 * 在WINDOWS中，你需要把下载的bcprov-jdk14-119.jar文件拷贝到两个地方： 
一个在你安装的JDK目录中，比如说我的是C:\j2sdk1.4.0-rc\jre\lib\ext 
另一个在你的JDK运行环境中，我的是在 
C:\Program Files\Java\j2re1.4.0-rc\lib\ext； 
另外还要在对两个java.security进行修改： 
我的在 C:\j2sdk1.4.0-rc\jre\lib\security\java.security； 
C:\Program Files\Java\j2re1.4.0-rc\lib\security\java.security; 
在java.security中加入 security.provider.6=org.bouncycastle.jce.provider.BouncyCastleProvider 
也配置
 * @author lizz
 *
 */
public class PBE {
	private static int ITERATIONS = 1000;


	public static void main(String args[]) throws Exception {
		// Convert password to a char array.
		char[] password = "密钥".toCharArray();
		String text = "文本";
		String enchar = "",dechar="";
		enchar = encrypt(password, text);
		dechar = decrypt(password, text);
		System.out.println("encrypt"+enchar+"decrypt"+dechar);
	}

	private static String encrypt(char[] password, String plaintext)
			throws Exception {
		byte[] salt = new byte[8];
		Random random = new Random();
		random.nextBytes(salt);

		PBEKeySpec keySpec = new PBEKeySpec(password);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithSHAAndTwofish-CBC");
		SecretKey key = keyFactory.generateSecret(keySpec);
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATIONS);

		Cipher cipher = Cipher.getInstance("PBEWIthSHAAndTwofish-CBC");
		cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

		byte[] ciphertext = cipher.doFinal(plaintext.getBytes());

		BASE64Encoder encoder = new BASE64Encoder();

		String saltString = encoder.encode(salt);
		String ciphertextString = encoder.encode(ciphertext);

		return saltString + ciphertextString;
	}

	private static String decrypt(char[] password, String text)
			throws Exception {
		String salt = text.substring(0, 12);
		String ciphertext = text.substring(12, text.length());

		BASE64Decoder decoder = new BASE64Decoder();

		byte[] saltArray = decoder.decodeBuffer(salt);
		byte[] ciphertextArray = decoder.decodeBuffer(ciphertext);

		PBEKeySpec keySpec = new PBEKeySpec(password);
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance("PBEWithSHAAndTwofish-CBC");
		SecretKey key = keyFactory.generateSecret(keySpec);
		PBEParameterSpec paramSpec = new PBEParameterSpec(saltArray, ITERATIONS);

		Cipher cipher = Cipher.getInstance("PBEWithSHAAndTwofish-CBC");
		cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

		byte[] plaintextArray = cipher.doFinal(ciphertextArray);

		return new String(plaintextArray);
	}
}
