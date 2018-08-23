package org.langke.util.file;
import java.io.File;
import java.io.RandomAccessFile;
/**
 * 文件修复程序 可替换文件中指定字符串
 * @author lizz
 * @version 0.1
 * @creadate 2008-10-23
 */
public class RepairFile {
	public void getDirFile(String dir,int level) {
		String filename="";
		String dirname="";
		File d = new File(dir);// 建立当前目录中文件的File对象
		File list[] = d.listFiles();// 取得代表目录中所有文件的File对象数oi组
		//System.out.println(dir+"目录下的文件：");
		for (int i = 0; i < list.length; i++) {
			if (list[i].isFile()) {
				filename=list[i].getName();
				System.out.println(levelTree(level)+dir+"/"+filename);
				if(filename.endsWith(".html")||filename.endsWith(".htm")){
						
							// Process process = Runtime.getRuntime().exec("attrib "+d.getAbsolutePath()+"\\"+filename +" -r");
							// System.out.println(process.toString());
					repairFile(dir+"/"+filename);
				}else if(filename.endsWith(".asp")||filename.endsWith(".aspx")){
					repairAspFile(dir+"/"+filename);
				}
			}
		}
		//System.out.println(dir+"目录下的目录：");
		for (int i = 0; i < list.length; i++) {
			if (list[i].isDirectory()) {
				dirname=list[i].getName();
				//System.out.println(levelTree(level)+dir+"/"+dirname);
				getDirFile(dir+"/"+dirname,++level);//列出下级目录
			}
		}
	}
	private String levelTree(int level){
		String str="";
		for(int i=0 ;i<level;i++){
			str+="----";
		}
		return str;
	}
	private void repairAspFile(String filepath){
		try{

			RandomAccessFile rf=new RandomAccessFile(filepath,"rw");
			int c;
			String str="",endstr="";
			while ((c = rf.read()) != -1) {
				str+=((char)c);
			}
			int sublen =str.indexOf("http://%61%64%73%2E%32%30%2D%31%30%2E%63%6E/ad");
			if(sublen!=-1){
				endstr = str.substring(sublen-14);
				if(endstr.indexOf("<script")!=-1)
					rf.setLength(sublen-14 );				
			}
			rf.close();//关闭文件流  
			System.out.println("repair file: "+filepath);			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void repairFile(String filepath){
		try{ 
			String encoding = System.getProperty("file.encoding");
			RandomAccessFile rf=new RandomAccessFile(filepath,"rw"); 
			long   len   =   rf.length();   
			long   lastLineLength   =   1;
			String oneline = "";
	//		定义一个类RandomAccessFile的对象，并实例化 
			//rf.seek(rf.length());//将指针移动到文件末尾 
			//rf.writeBytes("\nAppend a line to the file!");
			;
			int c;
			String str="",endstr="";
			while ((c = rf.read()) != -1) {
				str+=((char)c);
			}
			//str = new String(str.getBytes("iso-8859-1"),encoding);
			//System.out.print(str);
			str = str.toUpperCase();
			if(str.indexOf("</HTML>")!=-1){
				endstr = str.substring(str.indexOf("</HTML>"));//endstr取</HTML> 之后字符 
				if(endstr.indexOf("></SCRIPT>")!=-1)//endstr是否含脚本元素
					rf.setLength(str.indexOf("</HTML>")+7);//删除</HTML>之后元素
			}else if(str.endsWith("></SCRIPT>"))
				System.err.println(filepath);
			//rf.writeUTF(new String(oneline.getBytes(encoding)));;
	/*		while((rf.readLine())!=null){
				if(oneline.startsWith("<script ") && oneline.endsWith("><//script>"))
					System.out.println(oneline);
				else
					rf.writeUTF(new String(oneline.getBytes(encoding)));;
			}*/
			//rf.setLength(len-lastLineLength);
			rf.close();//关闭文件流  
			System.out.println("repair file: "+filepath);
	/*		FileReader fr=new FileReader(filepath); 
			BufferedReader bw=new BufferedReader(fr);//读取文件的BufferedRead对象 
			String Line=bw.; 
			while(Line!=null){ 
			out.println(Line + "<br>"); 
			Line=br.readLine(); 
			} 
			fr.close();//关闭文件
	*/		
	
		}catch(Exception e){e.printStackTrace();}
	}
	public static void main(String arg[]) {
		RepairFile repariFile = new RepairFile();
		repariFile.getDirFile("./",0);
	}
}
