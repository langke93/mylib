package org.langke.interview;

/**
 * JAVA不用类库，将字符串转换成数字
 * @author langke
 *
 */
 class StringToInteger {

	public static Integer parseInt(String str){
		if(str==null || str.trim().length()==0)
			return null;
		Integer res = 0;
		char c ;
		for(int i=0;i<str.length();i++){
			c = str.charAt(i) ;
			if(c>='0' && c<='9')
				res = 10 * res +(c-'0') ;
		}
		if(str.charAt(0) == '-')
			res = -res;
		return res;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(parseInt("-12345678"));
	}

}
