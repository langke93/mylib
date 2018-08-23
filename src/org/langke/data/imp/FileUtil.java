package org.langke.data.imp;

import java.io.File;
import java.io.FileWriter;

public class FileUtil {
	public static void FileLog(String fileName,String content){
		try{
			FileWriter fileWriter=new FileWriter(fileName,true);//true参数表追加文件
			fileWriter.write(content);
			fileWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
