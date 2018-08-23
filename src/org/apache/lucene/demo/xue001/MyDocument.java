package org.apache.lucene.demo.xue001;
import java.io.*;
import java.util.StringTokenizer;
import org.apache.lucene.document.*;
public class MyDocument {
	/**
	 * 将文本文档转成lucene的document格式
	 * @param file : 所要建立文件的索引
	 * @return document:已将文件转化成的document
	 * @throws Exception
	 */
	public static Document getDocument(File file)throws IOException{
		Document doc = new Document();
		//为文件路径构建一个字段
		doc.add(new Field("path", file.getPath(), Field.Store.YES, Field.Index.ANALYZED));
		//文件名
		doc.add(new Field("title",getFileName(file),Field.Store.YES,Field.Index.NOT_ANALYZED));
		//文件内容
		doc.add(new Field("contents",new FileReader(file)));
		//文件修改时间
		doc.add(new Field("modified",DateField.timeToString(file.lastModified()),Field.Store.YES, Field.Index.ANALYZED));
		return doc;
	}
	/**
	 * 
	 * @param file 取文件名
	 * @return
	 */
	
	private static String getFileName(File file){
		String path = file.getPath();
		StringTokenizer st = new StringTokenizer(path,File.separator);
		String token = "";
		while(st.hasMoreTokens()){
			token= st.nextToken();
		}
		if(token != null && token.endsWith(".txt")){
			token = token.substring(0,token.indexOf(".txt"));
		}
		return token;
	}
	
}
