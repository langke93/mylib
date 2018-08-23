package org.langke.util.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 可以支持LINUX的拼音工具
 * @author lee
 *
 */
public class PinYin4jUtil {
	public static String getPinYin(String inputString) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);

		char[] input = inputString.trim().toCharArray();
		String output = "";

		try {
			for (int i = 0; i < input.length; i++) {
				if (java.lang.Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
					output += temp[0];
				} else
					output += java.lang.Character.toString(input[i]);
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return output;
	}
	
	   public static String getPinYinHeadChar(String str) {  

		    String convert = "";  
		    for (int j = 0; j < str.length(); j++) {  
		          char word = str.charAt(j);  
		          String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);  
		          if (pinyinArray != null) {  
		          convert += pinyinArray[0].charAt(0);  
		          }else {  
		          convert += word;  
		          }  
		    }  
		    return convert;  
		    }  
		    //将字符串转移为ASCII码  
		    public static String getCnASCII(String cnStr)  
		    {  
		    StringBuffer   strBuf   =   new   StringBuffer();  
		    byte[]   bGBK   =   cnStr.getBytes();  
		            for(int   i=0;i <bGBK.length;i++){  
//		                System.out.println(Integer.toHexString(bGBK[i]&0xff));  
		                    strBuf.append(Integer.toHexString(bGBK[i]&0xff));  
		            }  
		            return strBuf.toString();  
		    }  
	
	public static void main(String args[]){
		System.out.println(getPinYin("李庄照"));
		System.out.println(getPinYinHeadChar("李庄照"));
		System.out.println(getCnASCII("李庄照"));
	}
}
