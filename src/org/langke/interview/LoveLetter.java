package org.langke.interview;

public class LoveLetter {

    public static void main(String[] args) {
        LoveLetter loveLetter = new LoveLetter();
        System.out.println(loveLetter.decode("747063"));
        System.out.println(loveLetter.decode("756870676B79"));
    }

    /**
     * 解密函数
     * @param code
     * @return
     */
    private String decode(String code){
        String str = "";
        char array[] = {'q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m'};//数组下标+97与10进制ASCII码字母a-z对应
        for(int i=0;i<code.length();i+=2){
            String tmpStr = code.substring(i,i+2);//每两个字符一组
            Integer decimalism = Integer.valueOf(tmpStr, 16);//16进制转10进制
            str+=array[decimalism-97];//10进制97对齐数组下标0
        }
        return str;
    }
}
