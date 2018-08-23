package org.langke.util.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author langke
 * 调用本地命令行
 *@date 2014/5/27
 */
public class RuntimeUtil {

    private static Logger log =LoggerFactory.getLogger(RuntimeUtil.class);
    /**
     * 执行命令并返回结果 ,命令可以带参数
     * @param cmd
     * @return
     */
    public static String getRuntimeResult(String cmd){
        String encoding = System.getProperty("file.encoding");
        int c;
        String str="";
        String estr="";
        String arr_cmd[] = null;
        try{
            Process child = null;
            arr_cmd  = new String[]{"/bin/sh","-c",cmd};//这种形式参数，可以解决ERROR: Unsupported SysV option.问题；  
            child = Runtime.getRuntime().exec(arr_cmd);
            //取得命令执行结果
            java.io.InputStream in = child.getInputStream();
            while ((c = in.read()) != -1) {
                str+=((char)c);
            }
            str = new String(str.getBytes("iso-8859-1"),encoding); 
            //取得异常信息
            java.io.InputStream err = child.getErrorStream();
            while((c = err.read())!=-1){
                estr+=((char)c);
            }
            estr = new String(estr.getBytes("iso-8859-1"),encoding); 
            in.close();
            try {
                child.waitFor();
            } catch (InterruptedException e) {
                log.error("child.waitFor() exception ",e);
            }
        }catch(Exception e){
            log.error("getRuntimeResult() "+cmd,e);
        }
        if(str != null && str.length()>0 && estr!=null && estr.length()>0){
            return str + estr;
        }else if(str!=null && str.length()>0){
            return str;
        }else return estr;
    }
}
