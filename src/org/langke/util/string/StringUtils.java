package org.langke.util.string;

import java.util.Date;

import javax.servlet.http.*;
import java.text.*;

public final class StringUtils {

	private StringUtils() {
	}

	public static String valueOf(char c) {
		char ac[] = new char[1];
		ac[0] = c;
		return new String(ac);
	}

	public static String valueOf(double d) {
		return Double.toString(d);
	}

	public static String valueOf(float f) {
		return Float.toString(f);
	}

	public static String valueOf(int i) {
		char ac[] = new char[11];
		int j = ac.length;
		boolean flag = i < 0;
		if (!flag)
			i = -i;
		for (; i <= -10; i /= 10)
			ac[--j] = Character.forDigit(-(i % 10), 10);

		ac[--j] = Character.forDigit(-i, 10);
		if (flag)
			ac[--j] = '-';
		return new String(ac, j, ac.length - j);
	}

	public static String valueOf(long l) {
		return Long.toString(l, 10);
	}

	public static String valueOf(Object obj) {
		return obj != null ? obj.toString() : "";
	}

	public static String valueOf(boolean flag) {
		return flag ? "true" : "false";
	}

	public static String valueOf(char ac[]) {
		return new String(ac);
	}

	public static String replaceAll(HttpServletRequest request, String str) {
		String path = request.getContextPath();
		str = stringReplace(str, "./BaseFunc", path + "/BaseFunc");
		str = stringReplace(str, "BBSIndex?", path + "/BBSIndex?");
		str = stringReplace(str, "./images/", "images/");
		str = stringReplace(str, "images/", path + "/images/");
		str = stringReplace(str, path + "/" + path, path);
		return str;
	}

	public static String ArrayToString(String[] arr, String sign) {
		String str = "";
		if (sign == null || sign.length() == 0)
			sign = ",";
		if (arr != null && arr.length != 0) {
			for (int i = 0; i < arr.length; i++)
				if (arr[i] != null && arr[i].length() != 0)
					str += sign + arr[i];
			if (str.length() != 0)
				str = str.substring(1);
		}
		return str;
	}
	/**
	 * 将数组转成数据库查询用的字符串
	 * @param arr
	 * @return
	 */
	public static String ArrayToSQLString(String[] arr) {
		String str = "";
		if (arr != null && arr.length != 0) {
			for (int i = 0; i < arr.length; i++)
				if (arr[i] != null && arr[i].length() != 0)
					str += ",'" + arr[i]+"'";
			if (str.length() != 0)
				str = str.substring(1);
		}
		return str;
	}
	/**
	 * 
	 * @param d
	 * @return
	 */
	public static String formatDateYyyymmdd(Date d){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(d == null)
			return "";
		else
			return 
				formatter.format(d);
	}
	
	public static String formatDate(Date d,String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		if(d == null)
			return "";
		else
			return 
				formatter.format(d);
	}
	
	public static String formatDateToMmdd(Date d){
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
		if(d == null)
			return "";
		else
			return 
				formatter.format(d);
	}
	/**
	 * 
	 * @param d
	 * @return
	 */
	public static String formatDate(Date d) {
		if (d == null)
			return "";
		else
			return DateFormat.getDateInstance().format(d);
		// return d.toLocaleString().substring(0,10);
	}
	
	public static Date formatStringDate(String d) {
		if (d == null)
			return null;
		else{
			Date date = null;
			try {
				date = DateFormat.getDateInstance().parse(d);
			} catch (Exception e) {
			}
			return date;
		}
		// return d.toLocaleString().substring(0,10);
	}
	/**
	 * 将Integer类型日期转化为YYYY-MM-DD格式
	 * @param i
	 * @return
	 */
	public static String integerDateFormat(Integer i){
		String date="";
		if(i==null || i.equals("") || i.toString().length()<8){
			return "";
		}else{
			String temp=i.toString();
			date=temp.substring(0, 4)+"-"+temp.substring(4,6)+"-"+temp.substring(6,8);
		}
		return date;
	}
	/**
	 * 将YYYY-MM-DD格式的字符串转为数字(YYYYMMDD)
	 * 
	 * @param d
	 * @return
	 */
	public static Integer formatDateToInteger(Date d){
		return formatDate(formatDateYyyymmdd(d));
	}
	public static Integer formatDate(String d) {
		Integer iDate = null;
		try {
			if (d != null && d.trim().length()>0) {
				String str[] = d.split("-");
				if (str.length == 3) {
					if(str[1].length()==1)
						str[1] = "0"+str[1];
					String newd = str[0] + str[1] + str[2];
					iDate = Integer.valueOf(newd);
				}
			}
		} catch (Exception e) {
		}
		return iDate;
	}

	public static String valueOf(String value) {
		if (value != null && !(value.equals("")) && !(value.equals("No data")))
			return value;
		else
			return "";
	}
	public static String intValueOf(String value) {
		if (value != null && !(value.equals("")))
			return value;
		else
			return "0";
	}
	public static Integer intValueOf(Integer value) {
		if (value != null && !(value.equals("")))
			return value;
		else
			return new Integer("0");
	}
	public static Integer stringToIntValueOf(String value) {
		if (value !=null && !value.equals("")){
			return Integer.valueOf(value);
		} else {
			return new Integer("0");
		}
	}
	public static float toMinFloat(Float value){
		if (value != null && !value.equals("")){
			return value.floatValue();
		} else {
			return 0;
		}
	}
	public static int toMinInteger(Integer value){
		if (value != null && !value.equals("")){
			return value.intValue();
		} else {
			return 0;
		}
	}
	public static int floatToInt(Float value){
		if (value != null && !value.equals("")){
			return value.intValue();
		} else {
			return 0;
		}
	}
	public static String getAppointStr(String value){
		String result = "";
		if (value!=null && !value.equals("")){
			int firstindex = value.indexOf("(");
			if(firstindex>-1){
				result = value.substring(firstindex+1,value.length()-1);
			}else{
				result = value;
			}
		}
		return result;
	}
	 /**
     * 返回四舍五入后的浮点数
     * @param arg
     * @return 字符型浮点数
     */
    public static String floatToStr(float arg){
    	String result = new String();
    	result = String.valueOf(arg);
    	switch (result.substring(result.indexOf("."),result.length()).length()-1){//小数点后有几位
    	case 1:
    		if (result.substring(result.length()-1,result.length()).equals("0"))
        		result = result.substring(0,result.indexOf("."));
    		break;
    	case 2:
    		break;
    	default: 
    		int i = Integer.parseInt(result.substring(result.indexOf(".")+2,result.indexOf(".")+3));//小数点后第二位数据
    		int j = Integer.parseInt(result.substring(result.indexOf(".")+3,result.indexOf(".")+4));//小数点后第三位数据
    	    if (j > 5)
    	    	result = result.substring(0,result.indexOf(".")+2)+String.valueOf(i+1);
    	    else
    	    	result = result.substring(0,result.indexOf(".")+3);
    	}
    	if(result.equals("0")){
    		result = "";
    	}
    	return (result);
    }
	/**
	 * 替换字符串 参数: str 中的 str1 替换成为 str2 生成串 返回
	 */
	public static java.lang.String stringReplace(String str, String str1,
			String str2) {
		StringBuffer strbuff = new StringBuffer();
		try {
			if (str != null && str.length() != 0) {
				int intL = str.indexOf(str1);
				while (intL != -1) {
					strbuff.append(str.substring(0, intL) + str2);
					str = str.substring(intL + str1.length(), str.length());
					intL = str.indexOf(str1);
				}
				if (str != null && str.length() != 0) {
					strbuff.append(str.substring(0, str.length()));
				}
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
		}
		return strbuff.toString();
	}
	/**
	 * 十六进制转十进制
	 * @param hex
	 * @return
	 */
	public static Integer hexToNumber(String hex){
		Integer num =0;
		char tmp;
		hex.replace("0x","0x").toUpperCase();
		for(int i=0;i<hex.length();i++){
			tmp = hex.charAt(hex.length()-1-i);
			if(tmp>='A')
				tmp = (char) (tmp - 7); 
			System.out.println(Integer.valueOf(hex.charAt(hex.length()-1-i))+"-->"+tmp);
			num += (tmp - 48) * div(16,i);
			System.out.println("num:"+ (tmp - 48) *  div(16,i));
		}
		return num;
	}
	public static int div(int base_num,int count){
		int res=1;
		for(int i=0;i<count ;i++){
			res*=base_num;
		}
		return res;
	}
	public static void main(String[] args) {
		System.out.println(div(16,3));
		System.out.println(hexToNumber("EFA3A"));
//		for (int i = 0; i < 10; i++) {
//			DateFormat df = DateFormat.getDateInstance(i);
//			Date d = new Date();
//			System.out.println(df.format(d));
//		}
//		System.out.println(StringUtils.getAppointStr("not in(12345)"));
		System.out.println(99.5%100+"---------"+99.0/100);
	}
	
}
