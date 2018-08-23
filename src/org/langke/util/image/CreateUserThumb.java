package org.langke.util.image;

import java.io.File;


public class CreateUserThumb {
	static String basePath = "/home/tomcat/house/ROOT/upload/images/community/";
	static String targetPath = "/home/tomcat/house/ROOT/upload/images/community/small/";
/*	static String basePath = "H:/down/";
	static String targetPath = "H:/down/small/";*/
	public static void main(String args[]){
	
				String fileName = "";
/*				if(args.length>1){
					System.out.println("Usage: java BulkCreate basePath  ");
					return;
				}else if(args.length==1){
					basePath = args[0];
				}*/
				try {
				File dir1 = new File(basePath);
				File file2 ;
				File file3;
				System.out.println("start");
				if(!dir1.exists()){// 如果目录不存在
					System.out.println("exit");
				}else{
					String filelist[] =dir1.list();//列出目录下所有文件
					for(int i=0;i<filelist.length;i++){
						file3 = new File(basePath +filelist[i]);
						if(file3.isDirectory()) continue;//无视目录
						file2 = new File( targetPath+filelist[i]);
						if(file2.exists());
						else{
								Thumbnails.saveImageAsJpg(basePath+file2.getName(), targetPath+file2.getName(),200, 150);
								
								System.out.println(file2.getName());
								
							}
						}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}		
	}
}
