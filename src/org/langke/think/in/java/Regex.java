package org.langke.think.in.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class Regex {
	/**
	 * 数字与字母的正则表达式
	 * @param arge
	 */
	public static void main(String arge[]){
        Regex regex = new Regex();
        
	    String str1 = "${DAY},${HH},${MM},${yyyyMMdd},${mm}";
	    str1 = str1.replace("${DAY}", "1");
        System.out.println(str1);
        str1 = str1.replace("${HH}", "2");
        System.out.println(str1);
        str1 = str1.replace("${MM}", "3");
	    System.out.println(str1);
        str1 = str1.replace("${yyyyMMdd}", "4");
        System.out.println(str1);
        str1 = str1.replace("${mm}", "5");
        System.out.println(str1);
        

        str1 = "${DAY},${HH},${MM},${yyyyMMdd},${mm}";
        str1 = regex.replaceAll(str1,"${DAY}", "1");
        System.out.println(str1);
        str1 = regex.replaceAll(str1,"${HH}", "2");
        System.out.println(str1);
        str1 = regex.replaceAll(str1,"${MM}", "3");
        System.out.println(str1);
        str1 = regex.replaceAll(str1,"${yyyyMMdd}", "4");
        System.out.println(str1);
        str1 = regex.replaceAll(str1,"${mm}", "5");
        System.out.println(str1);
        
        ////////////////////////////////
	    String s = " babc \\n a \n Dfdf";
	    System.out.println(s.replace('\n', ' '));
	    regex.split();
		String str = "mpraido3.cn";
		str = str.substring(0,str.indexOf("."));
		if(str.matches("(([0-9]+[a-zA-Z]+[0-9]*)|([a-zA-Z]+[0-9]+[a-zA-Z]*))")){
			
		}else{
			System.out.println(str);
		}
		String curLine = "23dnsdc.com";
		if(curLine.indexOf(".")<5 || curLine.toLowerCase().indexOf("idc")!=-1 || curLine.toLowerCase().indexOf("isp")!=-1 || curLine.toLowerCase().indexOf("dns")!=-1 || curLine.toLowerCase().indexOf("fz")!=-1 || curLine.toLowerCase().indexOf("fj")!=-1){
			System.out.println(curLine);
		}
		System.out.println(java.util.Calendar.getInstance().getTimeInMillis()/1000);
		System.out.println((int)(java.util.Calendar.getInstance().getTimeInMillis()/1000));
		String key = "SEQINT";
		String tag = "\\$\\{"+key+"\\."+1+"}";
		str = "{abc${SEQINT.1}hello";
		String res = str.replaceFirst(tag, "1"); 
		System.out.println(res);
		
	}

    public String replaceAll(String str, String str1, String str2){
        int index = -1;
        while((index = str.indexOf(str1)) != -1){
            str = str.substring(0, index) + str2 + str.substring(index+str1.length());
        }
        return str;
    }
    
	public void split(){
	    long start = System.currentTimeMillis();
	    String log = "123@456@789";
        log= "27ea18d64a1840b7980d35017500994f1409647982102@10.5.121.144@1409647982102@90480.1.1search\\Ncom.cyou.fz.services.search.api.IndexServicebuild1.0.1com.cyou.fz.services.search.api.SMServicegetAllowCommitByIndex1.0.210.5.121.14810.5.121.148:2089101409647982157600\\N\\N\\N";

	    String regex ="([^\u0001]*)\u0001([^\u0001]*)\u0001([^\u0001]*)\u0001([^\u0001]*)\u0001([^\u0001]*)\u0001([^\u0001]*)\u0001([^\u0001]*)\u0001([^\u0001]*)\u0001([^\u0001]*)\u0001([^\u0001]*)\u0001([^\u0001]*)\u0001([^\u0001]*)\u0001([^\u0001]*)\u0001([^\u0001]*)\u0001([^\u0001]*)\u0001([^\u0001]*)\u0001([^\u0001]*)\u0001([^\u0001]*)\u0001([^\u0001]*)\u0001([^\u0001]*)";// "([\\w@\\.]*)\u0001([\\d\\.]*)\u0001([\\w\\-\\\\N]*)\u0001([\\w\\-\\\\N]*)\u0001([\\w\\-\\.\\\\N]*)\u0001([\\w\\-\\.\\\\N]*)\u0001([\\w\\-\\.\\\\N]*)\u0001([\\w\\-\\.\\\\N]*)\u0001([\\w\\-\\.\\\\N]*)\u0001([\\w\\-\\.\\\\N]*)\u0001([\\w\\-\\.\\:\\\\N]*)\u0001([\\w\\-\\.\\:\\\\N]*)\u0001([\\d]*)\u0001([\\d]*)\u0001([\\d]*)\u0001([\\d]*)\u0001([\\d\\\\N]*)\u0001([^\u0001]*)\u0001([\\d\\\\N]*)\u0001([\\d\\\\N]*)";//"([^@].*)@([^@].*)@([^@].*)"
	    Matcher matcher = Pattern.compile(regex).matcher(log);

        
        //System.out.println(str.matches("(\\w.*)\u0001(\\w.*)\\s"));
        
        if (matcher.find()) {
            System.out.println("1:::" + matcher.group(1));
            System.out.println("2:::" + matcher.group(2));
            System.out.println("3:::" + matcher.group(3));
          System.out.println("4:::" + matcher.group(4));
          System.out.println("5:::" + matcher.group(5));
          System.out.println("6:::" + matcher.group(6));
          System.out.println("7:::" + matcher.group(7));
          System.out.println("8:::" + matcher.group(8));
          System.out.println("9:::" + matcher.group(9));
          System.out.println("10:::" + matcher.group(10));
          System.out.println("11:::" + matcher.group(11));
          System.out.println("12:::" + matcher.group(12));
          System.out.println("13:::" + matcher.group(13));
          System.out.println("14:::" + matcher.group(14));
        }else
            System.out.println("not find");

        System.out.println(System.currentTimeMillis()-start + " ms");
	}
}
