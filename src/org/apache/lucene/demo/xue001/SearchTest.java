package org.apache.lucene.demo.xue001;
import java.io.*;
import java.util.Date;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.index.*;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.*;
import org.apache.lucene.search.*;
public final class SearchTest {
	private IndexSearcher searcher = null;
	private Analyzer analyzer = null;
	private String directory = "d:/data/xue001/index_card";
	public SearchTest()throws IOException{
		analyzer = new StandardAnalyzer();
		searcher = new IndexSearcher(IndexReader.open(directory));
	}
	/**
	 * 
	 * @param queryString ：搜索的关键字
	 * @param field：搜索的字段
	 * @return ：搜索的结果词条
	 */
	public final Hits search(String queryString,String[] fields){
		if(searcher !=null){
			try{
				//QueryParser parser = new QueryParser(field,analyzer);

				MultiFieldQueryParser parser = new MultiFieldQueryParser(fields,analyzer);
				//parser.setDefaultOperator(QueryParser.OR_OPERATOR);
				parser.setDefaultOperator(QueryParser.AND_OPERATOR);
				Query query = parser.parse(queryString);
				return searcher.search(query);
			}catch(ParseException e){
				e.printStackTrace();
				return null;
			}catch(IOException e){
				e.printStackTrace();
				return null;
			}finally{
				try {
					searcher.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	/**
	 * 
	 * @param hits :返回的搜索结果
	 * @param keywords：搜索的关键字
	 * @throws Excption
	 */
	public static void printresult(Hits hits,String keywords)throws Exception{
		if(hits.length()>0){
			System.out.println("搜索关键字："+keywords+",存在以下文件中：");
			for(int i=0;i<hits.length();i++){
				Document doc = hits.doc(i);
				String path = doc.get("path");
				if(path!=null){
					System.out.println(path);
				}
			}
		}else{
			System.out.println("No Found!");
		}
	}
	
	public static void main(String args[])throws IOException{
		SearchTest searcher = new SearchTest();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请输入您要搜索的关键字：");
		String keywords = in.readLine();
		while(keywords!=null){
			System.out.println("keywords:"+keywords);
			Date start = new Date();
			String[] fields = new String[]{"path","contents","title","modified"};
			Hits  h = searcher.search(keywords, fields);
			Date end = new Date();
			try {
				printresult(h,keywords);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("总共耗时："+(end.getTime()-start.getTime())+"ms");
			System.out.println("总共找到"+h.length()+"个文件");	
			System.out.println("请输入您要搜索的关键字：");
			keywords =  in.readLine();
		}
	}
}
