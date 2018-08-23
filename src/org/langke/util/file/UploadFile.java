package org.langke.util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import org.apache.struts.upload.FormFile;
 

public class UploadFile {
	private int count=0;
	private int fileSize;
	private String allow_ext;
 
	public UploadFile(){
		this.fileSize=150;
		this.allow_ext=".jpg,.gif,.png";
	}
	
	/**
	 * 文件参数设置
	 * @param fileSize 文件大小 单位K
	 * @param allow_ext 文件后缀名，多个以","隔开。如：.jpg,.gif,.png
	 */
	public UploadFile(int fileSize,String allow_ext){
		this.fileSize=fileSize;
		this.allow_ext=allow_ext;
	}
	
	/**
	 * 检查文件
	 * @param file
	 * @return
	 */
	public String  checkFile(FormFile file){
		//判断是否为空
		int size = file.getFileSize();
		if (size == 0) {
			return null;
		}
		String filename = file.getFileName();
		String postfix = filename.substring(filename.lastIndexOf("."));
		postfix = postfix.toLowerCase();

		// 判断文件类型是否正确
		if (allow_ext.indexOf(postfix) == -1) {
			return "文件的后缀名不正确";
		}

		// 判断文件大小
		if (size > fileSize * 1024) {
			return "文件大小超过"+fileSize+"k";
		}
		
		return null;
	}
	
	/**
	 * 保存文件
	 * @param file 上传的文件
	 * @param folderPath  文件保存路径，不包含文件名，最后以"/" 或"\" 结束
	 * @return 返回文件名
	 */
	public String saveFile(FormFile file, String folderPath) throws Exception {
		FileOutputStream fs = null;
		try {
			//判断是否为空
			int size = file.getFileSize();
			if (size == 0) {
				return null;
			}
			String filename = file.getFileName();
			String postfix = filename.substring(filename.lastIndexOf("."));
			postfix = postfix.toLowerCase();
			// 保存文件
			File f = new File(folderPath);
			if (!f.exists()) {
				f.mkdirs();
			}

			long time = Calendar.getInstance().getTime().getTime();
			String newFileName = time+""+(++count) + postfix;
			String filePath = folderPath + newFileName;
			fs = new FileOutputStream(filePath);
			fs.write(file.getFileData());
			return newFileName;
		}
		finally {
			if (fs != null) {
				try {
					fs.close();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 删除文件
	 * @param filePath
	 * @throws Exception
	 */
	public void delFile(String filePath){
		try{
			filePath = filePath.replace("/",File.separator);
			File f = new File(filePath);
			if (f.exists()) {
				f.delete();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
