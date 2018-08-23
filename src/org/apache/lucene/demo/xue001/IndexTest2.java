package org.apache.lucene.demo.xue001;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;

public class IndexTest2 {
	public final static String INDEX_STORE_PATH = "d:/data/xue001/index_card";
	public final static String INDEX_SOURCE_PATH= "F:/";
	public static final int MAX_FIELD_LENGTH=900000;
	//合并索引前，内存中最大的文件数量
	public static final int MAX_DOCS_IN_RAM = Short.MAX_VALUE;
	//两个索引器
	private IndexWriter ramWriter = null;
	private IndexWriter fsWriter = null;
	//内存中已经有的文档数量
	private int docs_in_ram;
	//内存中最大的文档数量
	private int ramMaxFiles = MAX_DOCS_IN_RAM;
	//内存是否已经被刷新过
	private boolean freshed = true;
	//索引存储的文件系统目录
	FSDirectory fsDir= null;
	//索引存储的内存目录;
	RAMDirectory ramDir = null;
	
	private IndexTest2()throws IOException{		
	}
	
	/**
	 * 构造索引器
	 */
	public void initWriter(){
		try{
			fsDir = FSDirectory.getDirectory(INDEX_STORE_PATH);
			ramDir = new RAMDirectory();
			Analyzer analyzer = new StandardAnalyzer();
			
			ramWriter = new IndexWriter(ramDir,analyzer,true);
			fsWriter = new IndexWriter(fsDir,analyzer,true);
			ramWriter.setMaxFieldLength(MAX_FIELD_LENGTH);
			fsWriter.setMaxFieldLength(MAX_FIELD_LENGTH);
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	public void writeToIndex()throws IOException{
		File file = new File(INDEX_SOURCE_PATH);
		if(fsWriter ==null || ramWriter==null){
			System.out.println("请先初始化索引器！");
			return;
		}
		Date start = new Date();
		int num = indexFiles(file);
		if(!freshed){
			freshRam();
		}
		Date end = new Date();
		System.out.println("文档个数："+num);	
		System.out.println("建立索引用时："+(end.getTime()-start.getTime())+"ms");
	}
	/**
	 * 关闭索引
	 */
	public void close()throws IOException{
		ramWriter.close();
		fsWriter.close();
	}
	/**
	 * 刷新内存
	 */
	private void freshRam()throws IOException{
		System.out.println("内存刷新中....");
		ramWriter.close();//在合并内存索引器的索引到硬盘索引器前，务必先关闭内存索引器。
		fsWriter.addIndexesNoOptimize(new Directory[]{ramDir});

		ramDir.close();
		ramDir = new RAMDirectory();
		ramWriter = new IndexWriter(ramDir,new StandardAnalyzer(),true);
		ramWriter.setMaxFieldLength(MAX_FIELD_LENGTH);
		
		docs_in_ram = 0;
		freshed = true;
	}
	/**
	 * 向索引加入文档
	 */
	private void addDocument(File file )throws IOException{
		if(docs_in_ram >= ramMaxFiles){
			freshRam();
		}
		ramWriter.addDocument(MyDocument.getDocument(file));
		docs_in_ram++;
		freshed = false;
	}
	/**
	 * 递归文件目录来建立索引
	 */
	private int indexFiles(File file)throws IOException{
		if(file.isDirectory()){
			File[] files = file.listFiles();
			int num=0;
			for(int i=0;i<files.length;i++){
				num+=indexFiles(files[i]);
			}
			return num;
		}else{
			if(file.getPath().endsWith(".txt")){
				System.out.println("正在建军索引："+file);
				addDocument(file);
				return 1;
			}else{
				return 0;
			}
		}
	}
	
	public static void main(String args[])throws IOException{
		IndexTest2 indexer = new IndexTest2();
		indexer.initWriter();
		indexer.writeToIndex();
		indexer.close();
	}
}
