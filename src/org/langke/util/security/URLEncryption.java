package org.langke.util.security;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;


public class URLEncryption {
	private static final int INCRE_VALUE = 10;
	private static final int NUM_RAND = 3;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String strSrc = "";
		String strDesc = "";
		String strDecode = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		try {
			while (true) {
				System.out.print("待加密串为：");
				String line;
				line = in.readLine();
				if (line.length() == -1)
					break;
				strSrc = line;

				System.out.println("开始进行加密..........");
				strDesc = encodeURL(strSrc,true);
				System.out.println("加密后串变为：" + strDesc);
				System.out.println("开始进行解密..........");
				strDecode = decodeURL(strDesc,true);
				System.out.println("还原加密串：" + strDecode);
				System.out.println("----------------------------");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static String encodeURL(String strIn, boolean flg) {
		String strReturn = "";
		String strOut = "";
		strOut = encodeURL(strIn);
		for (int i = 0; i < strOut.length(); i = i + 4) {
			strReturn += strOut.substring(i + 2, (i + 2) + 2);
		}
//		System.out.println(strReturn);
		return strReturn;
	}

	public static String decodeURL(String strIn, boolean flg) {
		String strReturn = "";
		for (int i = 0; i < strIn.length(); i = i + 2) {
			strReturn += "00" + strIn.substring(i, i + 2);
		}
		strReturn = decodeURL(strReturn);
//		System.out.println(strReturn);
		return strReturn;
	}


	public static String encodeURL(String strIn) {
		if (strIn == null)
			return null;
		String strOut = "";
		byte[] bIn = null;
		Random rnd = new Random();
		for (int i = 0; i < NUM_RAND; i++) {
			strIn = String.valueOf(rnd.nextInt(9)) + strIn
					+ String.valueOf(rnd.nextInt(9));
		}
		bIn = stringToByte(strIn);
		for (int i = 0; i < bIn.length; i++) {
			if (i % 2 == 0)
				continue;
			int b = bIn[i];
			b += i * INCRE_VALUE;
			if (b > 255)
				b = b - 256;
			bIn[i] = (byte) b;
		}

		strOut = byteToHexString(bIn);
		return strOut;
	}


	public static String decodeURL(String strIn) {
		if (strIn == null)
			return null;
		String strOut = "";
		byte[] bIn = null;
		// bIn = stringToByte(strIn);
		bIn = hexStringToByte(strIn);
		// 对字节数组进行解密处理
		for (int i = 0; i < bIn.length; i++) {
			if (i % 2 == 0)
				continue;
			int b = bIn[i];
			b -= i * INCRE_VALUE;
			if (b < 0)
				b = b + 256;
			bIn[i] = (byte) b;
		}
		// System.out.println(byteToHexString(bIn));
		strOut = byteToString(bIn);
		strOut = strOut.substring(NUM_RAND, strOut.length() - NUM_RAND);
		return strOut;
	}


	private static byte[] hexStringToByte(String strIn) {
		byte[] bIn = null;
		int len = strIn.length();
		bIn = new byte[len / 2];
		char c1, c2;
		int i1 = 0, i2 = 0;
		// 计算出每个字节的值，两个16进制值构成一个字节
		for (int i = 0, j = 0; i < len - 1; i += 2, j++) {
			// 字节的前四位
			c1 = strIn.charAt(i);
			if (c1 >= 'A' && c1 <= 'F') {
				i1 = (10 + 5 - ('F' - c1)) * 16;
			} else if (c1 >= '0' && c1 <= '9') {
				i1 = Integer.parseInt(String.valueOf(c1)) * 16;
			}
			// 字节后四位
			c2 = strIn.charAt(i + 1);
			if (c2 >= 'A' && c2 <= 'F') {
				i2 = (10 + 5 - ('F' - c2));
			} else if (c2 >= '0' && c2 <= '9') {
				i2 = Integer.parseInt(String.valueOf(c2));
			}
			bIn[j] = (byte) (i1 + i2);
		}
		return bIn;
	}


	private static String byteToHexString(byte b) {
		String s[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F" };
		return String.valueOf(s[(b & 0xf0) >> 4]) + String.valueOf(s[b & 0xf]);
	}


	private static String byteToHexString(byte b[]) {
		String s = "";
		for (int i = 0; i < b.length; i++)
			s = String.valueOf(s) + String.valueOf(byteToHexString(b[i]));

		return s;
	}


	private static byte[] charToByte(char c) {
		byte b[] = new byte[2];
		b[0] = (byte) (((short) c & 0xff00) >> 8);
		b[1] = (byte) ((short) c & 0xff);
		return b;
	}

	private static String charToHexString(char c) {
		return byteToHexString(charToByte(c));
	}


	private static byte[] stringToByte(String s) {
		byte b[] = new byte[2 * s.length()];
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			b[2 * i + 0] = (byte) (((short) ch & 0xff00) >> 8);
			b[2 * i + 1] = (byte) ((short) ch & 0xff);
		}
		return b;
	}

	private static char byteToChar(byte b[]) {
		return (char) ((short) b[0] << 8 | (short) b[1] & 0xff);
	}


	private static String byteToString(byte b[]) {
		byte t[] = new byte[2];
		char c[] = new char[b.length / 2];
		for (int i = 0; i < b.length / 2; i++) {
			t[0] = b[2 * i + 0];
			t[1] = b[2 * i + 1];
			c[i] = byteToChar(t);
		}

		return new String(c);
	}

}
