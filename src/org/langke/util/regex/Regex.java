package org.langke.util.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringEscapeUtils;

public class Regex {

	public static final String digi1 = "^/d+$";//非负整数（正整数+0）
	public static final String digi2 = "^[0-9]*[1-9][0-9]*$";//正整数
    public static final String digi3 = "^((-/d+)|(0+))$";//非正整数（负整数+0）
    public static final String digi4 = "^-[0-9]*[1-9][0-9]*$";//负整数
    public static final String digi5 = "^-?/d+$";//整数
    public static final String float1 = "^/d+(/./d+)?$";//非负浮点数（正浮点数+0）
    public static final String float2 = "^(([0-9]+/.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*/.[0-9]+)|([0-9]*[1-9][0-9]*))$";//正浮点数
    public static final String float3 = "^((-/d+(/./d+)?)|(0+(/.0+)?))$";//非正浮点数（负浮点数+0）
    public static final String float4 = "^(-?/d+)(/./d+)?$";//浮点数
    public static final String en = "^[A-Za-z]+$";//由26格英文字母组成的字符串
    public static final String enUpper = "^[A-Z]+$";//由26格大写字母组成的字符串
    public static final String enDigi = "^[A-Za-z0-9]+$";//由数字和26个英文字母组成的字符串
    public static final String enDigi_ = "^/w+$";//由数字、26格英文字母或者下滑线组成的字符串
    public static final String email = "^[/w-]+(/.[/w-]+)*@[/w-]+(/.[/w-]+)+$";//Email地址的验证格式
    public static final String url = "^[a-zA-z]+://(/w+(-/w+)*)(/.(/w+(-/w+)*))*(/?/S*)?$";//URL地址的格式
    public static final String yymmdd = "^(d{2}|d{4})-((0([1-9]{1}))|(1[1|2]))-(([0-2]([1-9]{1}))|(3[0|1]))$";//年-月-日
    public static final String mmddyy = "^((0([1-9]{1}))|(1[1|2]))/(([0-2]([1-9]{1}))|(3[0|1]))/(d{2}|d{4})$";//月/日/年
    public static final String mobile = "(d+-)?(d{4}-?d{7}|d{3}-?d{8}|^d{7,8})(-d+)?";//手机号码
    public static final String ip = "^(25[0-5]|2[0-4]/d|[0-1]?/d?/d)(/.(25[0-5]|2[0-4]/d|[0-1]?/d?/d)){3}$";//IP地址
    public static final String cn = "[/u4e00-/u9fa5]";//中文字符
    public static final String doubleByte = "[^/x00-/xff] ";//双字节
    public static final String qq = "^[1-9]*[1-9][0-9]*$ ";//QQ号
    public static final String id = "^d{15}|d{}18$";//国内身份证格式【没有验证生日】
    public static final String tel = "(/(/d{3,4}/)|/d{3,4}-|/s)?/d{8}";//国内的电话号码
    
    public static boolean validate(String str,String regex){
    	regex = regex.replace("/", "\\");
    	return str.matches(regex);
    }

    /**
     * 相邻是否有重复单词
     * @param phrase
     * @return
     */
	public static boolean hasDuplicate(String phrase){
		boolean result = false;
		String duplicatePattern = "\\b(\\w+) \\1\\b";
		Pattern pattern = null;
		try{
			pattern = Pattern.compile(duplicatePattern);
		}catch(PatternSyntaxException pex){
			pex.printStackTrace();
			return result;
		}
		int matches = 0;
		Matcher matcher = pattern.matcher(phrase);
		String valString = null;
		
		while(matcher.find()){
			result = true;
			valString = "$" + matcher.group() + "$";
			//System.out.println(valString);
			matches++;
		}
		return result;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    String str = "字段1\u0001字段2";
	    str = StringEscapeUtils.escapeJava(str);
	    System.out.println(str);
		boolean res = Regex.validate("192.168.1.3", Regex.ip);
		System.out.println(res);
		new Regex().matchNginxLog();
	}

	/**
	 * nginx日志正则匹配
	 */
	private void matchNginxLog(){
	    String inputRegex;
	    Pattern inputPattern;
	    String rowText ;
	    rowText = "1438845314.648 \"43.224.213.196\" 1365 \"GET\" \"files.zyw.com\" \"/forum/fz_tele-02/images/2013/04/28/164211ctss5hfnsfn3cncs.png\" \"files.zyw.com\" \"58.22.102.24\" 80 \"/forum/fz_tele-02/images/2013/04/28/164211ctss5hfnsfn3cncs.png\" \"-\" \"HTTP/1.1\" 20153 19829 200 0.073 \"http://bbs.zyw.com/\" \"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36\" \"1413033090124768\" \"-\" \"3a1666182be0d5c455c309825756bb94\" \"0\" \"10.59.107.16:80\" \"-\" [06/Aug/2015:15:15:14 +0800]";
	    inputRegex =  "([^ ]*) \"([^ ]*)\" (-|[0-9]*) \"([^ ]*)\" \"([^ ]*)\" \"([^ ]*)\" \"([^ ]*)\" \"([^ ]*)\" (-|[0-9]*) \"([^ ]*)\" \"([^ ]*)\" \"([^ ]*)\" (-|[0-9]*) (-|[0-9]*) (-|[0-9]*) ([^ ]*) \"([^ ]*)\" \"(.+?|-)\" \"([^ ]*)\" \"([^ ]*)\" \"([^ ]*)\" \"([^ ]*)\" (-|\\[[^\\]]*\\])";
	    inputPattern = Pattern.compile(inputRegex, Pattern.DOTALL + (true ? Pattern.CASE_INSENSITIVE : 0));
	    Matcher m = inputPattern.matcher(rowText.toString());
	    if(m.matches())
	    for (int i=0;i<m.groupCount();i++){
	        String t = m.group(i+1);
	        System.out.println(t);
	    }
	}
}
