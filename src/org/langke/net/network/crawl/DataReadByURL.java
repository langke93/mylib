package org.langke.net.network.crawl;
import java.io.InputStream;
import java.net.URL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class DataReadByURL {
	static {
		URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
	}

	public static void catHDFSWithAPI(){
		Configuration conf = new Configuration();
		FSDataInputStream in = null;
		try{
			conf.set( "fs.default.name",  "hdfs://hadoop8:9000");
			FileSystem fs = FileSystem.get(conf);
			in = fs.open(new Path("/user/demo/user_top_community/community.2010-12-28-19-20-09/part-r-00000"));
			IOUtils.copyBytes(in, System.out, 4096,false);
			in.seek(2);
			IOUtils.copyBytes(in, System.out, 4096,false);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			IOUtils.closeStream(in);
		}
	}
	public static void main(String[] args) throws Exception {
		catHDFSWithAPI();
		InputStream in = null;
		try {
			in = new URL("hdfs://hadoop8:9000/user/demo/user_top_community/community.2010-12-28-19-20-09/part-r-00000").openStream();
			//IOUtils.copyBytes(in, System.out, 2048, false);
		} finally {
			IOUtils.closeStream(in);
		}
	}
}
