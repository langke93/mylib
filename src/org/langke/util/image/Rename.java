package org.langke.util.image;

import java.io.File;

public class Rename {
	static String basePath = "I:/DCIM/101MSDCF/";
/*	static String basePath = "H:/down/";
	static String targetPath = "H:/down/small/";*/
	public static void main(String args[]){
	
				String fileName = "DSC";
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
						if(i<10) fileName = fileName+"0000"+i;
						else
							if(i<100) fileName = fileName+"000"+i;
							else
								if(i<1000) fileName = fileName+"00"+i;
								else
									if(i<10000) fileName = fileName+"0"+i;
									else
										if(i<100000) fileName = fileName+i;
						fileName +=".JPG";
						System.out.println(fileName);
						file3 = new File(basePath +filelist[i]);
						if(file3.isDirectory()) continue;//无视目录
						if(file3.getName().toUpperCase().endsWith(".JPG"))
							file3.renameTo(new File(basePath+fileName));
						fileName="DSC";
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}		
	}
}
