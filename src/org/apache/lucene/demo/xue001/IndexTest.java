package org.apache.lucene.demo.xue001;
import java.io.File;
import java.util.Date;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
public class IndexTest {
	public final static String INDEX_FILE_PATH = "d:/lucene";
	public final static String INDEX_STORE_PATH = "d:/data/xue001/index";
	public static int count ;
	private IndexWriter writer = null;
	public IndexTest(){
		try{
			writer = new IndexWriter(INDEX_STORE_PATH,new StandardAnalyzer(),true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 通过addDocument的函数，将文件file转化成document形式写入索引
	 * @return
	 * @throws Exception
	 */
	public int writeToIndex(File folder)throws Exception{
		int num = 0;
		writer.setMaxFieldLength(900000);
		if(folder.isDirectory()){
			File[] files = folder.listFiles();
			for(int i=0;i<files.length;i++){
				if(files[i].isDirectory()){
					num += writeToIndex(files[i]);
				}else if(files[i].getName().endsWith(".txt")){
					num ++;
					count++;
					System.out.println("正在建立索引："+files[i]+"");
					writer.addDocument(MyDocument.getDocument(files[i]));
				}
			}
			return num;
		}
		return num;
	}
	
	public void close()throws Exception{
		writer.close();
	}
	public static void main(String args[])throws Exception{
		IndexTest indexer = new IndexTest();
		Date start = new Date();
		File folder = new File(INDEX_FILE_PATH);
		int num = indexer.writeToIndex(folder);
		Date end = new Date();
		System.out.println("文档个数："+num);
		System.out.println("文档个数："+count);		
		System.out.println("建立索引用时："+(end.getTime()-start.getTime())+"ms");
		indexer.close();
	}
	
}
