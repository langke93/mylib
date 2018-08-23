package org.langke.util.file;

import java.io.*;

public class DownloadFile {

	public   void   downloadFile(String   aFileName,String bFileName,javax.servlet.http.HttpServletResponse   response)   throws   Exception   {   
        java.io.BufferedInputStream   iin;
        try   {
              File   ff=new   File(aFileName);   
              if(!ff.exists()){   
                  throw   new   Exception("对不起!   您下载的文件不存在");   
              }else{   
                  byte[]   buffer;   
                  int   length=(new   Long(ff.length())).intValue();   
                  buffer=new   byte[length];   
                  try{   
                          iin=new   BufferedInputStream(new   java.io.FileInputStream(ff));   
    
                        response.setContentType(   "application/octet-stream"   );   //   MIME   type   for   pdf   doc   
                        int   pos=aFileName.lastIndexOf("/");   
                        aFileName=aFileName.substring(pos+1);   
                        response.setHeader("Content-disposition",   "attachment;   filename=\""+bFileName+"\"");   
                        //传送数据   
                        BufferedOutputStream  dout   =   new   BufferedOutputStream(   response.getOutputStream());   
                        int   once   =   0;   
                        int   total   =   0;   
                        while   ((total<length)   &&   (once>=0))   {   
                              once   =   iin.read(buffer,total,length);   
                              total   +=   once;   
                              dout.write(buffer,0,length);   
                        }   
                        if(iin!=null){       iin.close();}   
                        if(dout!=null){     dout.close();}   
                    }catch(Exception   ex){   
                          throw   new   Exception("文件下载过程中出现错误!   ");   
                    }   
                }   
            }catch(Exception   ex)   {   
                  throw   new   Exception("文件下载过程中出现错误!   ");   
            }   
      }   

}
