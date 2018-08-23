package org.apache.lucene.demo.xue001;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.index.IndexWriter;

public class OptimizeIndex {
	private IndexWriter writer = null;
	public void optimizeIndex(String indexPath)throws Exception{
		writer = new IndexWriter(indexPath,new MMAnalyzer(), false);
		writer.optimize();
		writer.close();
	}
	public static void main(String args[])throws Exception{
		if(args.length<1){
			System.out.println("Usage:OptimizeIndex indexpath");
			return;
		}
		String indexPath = args[0];
		new OptimizeIndex().optimizeIndex(indexPath);
	}
}
