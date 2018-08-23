package org.langke.think.in.java;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.CRC32;

import org.apache.commons.lang.time.DateUtils;
import org.langke.util.security.MD5;

public class CRC32Util {

    public static void main(String args[]) throws ParseException{
        String str = "soa-log-test1com.cyou.fz.soa.service.LogTestService1getLogTest11.0.0";
        long t1 = System.currentTimeMillis();
        String value1 = null;
        Set<String> keySet = new HashSet<String>();
        for(int i=0;i<10000000;i++){
            CRC32 crc32 = new CRC32();
            crc32.update((str+i).getBytes());
            value1 = Long.toHexString(crc32.getValue());
            /* if(keySet.contains(value1)){
                System.out.println("mamamaadfsfsad!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                break;
            }
            keySet.add(value1);*/
        }
        System.out.println(value1+" time: "+(System.currentTimeMillis()-t1));

        long t2 = System.currentTimeMillis();
        MD5 md5 = new MD5(); 
        String value2 = null;
        for(int i=0;i<10000000;i++){
            value2 = md5.getmd5str((str+i));
        }
        System.out.println(value2+" time: "+(System.currentTimeMillis()-t2));
        
        CRC32 crc32a = new CRC32();
        str = "`";
        crc32a.update(str.getBytes());
        System.out.println(System.currentTimeMillis()+Long.toHexString(crc32a.getValue()));
        
        System.out.println( DateUtils.parseDate("2099-01-01", new String[]{"yyyy-MM-dd"}));
        System.out.println( DateUtils.parseDate("2013-11-22", new String[]{"yyyy-MM-dd"}));
    }
}
