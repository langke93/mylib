package org.langke.hadoop;


import java.io.IOException;
//import java.security.PrivilegedExceptionAction;
import java.security.PrivilegedExceptionAction;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
//import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.security.UserGroupInformation;

public class NewWordCount {

  public static class TokenizerMapper 
       extends Mapper<Object, Text, Text, IntWritable>{
		static enum Counters { INPUT_WORDS };

	  
	protected void setup(Context context
      ) throws IOException, InterruptedException {
		System.out.println( 123 );
	}
    
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
      
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      StringTokenizer itr = new StringTokenizer(value.toString());
      while (itr.hasMoreTokens()) {
        word.set(itr.nextToken());
        context.write(word, one);
      }
    }
  }
  
  public static class IntSumReducer 
       extends Reducer<Text,IntWritable,Text,IntWritable> {
    private IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values, 
                       Context context
                       ) throws IOException, InterruptedException {
      int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
      }
      result.set(sum);
      context.write(key, result);
    }
  }
  /**
   * @usage 
   * 		jar -cvf mylib.jar -C classes/ .
   * 		java -classpath .:* org.langke.hadoop.NewWordCount /user/hadoop/input /user/hadoop/output
   * 		java -classpath .:* org.langke.hadoop.NewWordCount  -Dwordcount.case.sensitive=true /user/hadoop/input/docs /user/hadoop/output5 -skip /user/hadoop/patterns.txt
   * @param args
   * @throws Exception
   */
  public static void main(final String[] args) throws Exception {
	  //System.out.println( org.codehaus.jackson.map.JsonMappingException.class );
	  //org/codehaus/jackson/JsonProcessingException
	  //System.out.println( System.getProperty( "java.class.path") );
		UserGroupInformation ugi = UserGroupInformation.createRemoteUser("hadoop");

		ugi.doAs(new PrivilegedExceptionAction<Object>(){

			public Object run() throws Exception {
			    Configuration conf = new Configuration();
			    conf.set( "fs.default.name" , "hdfs://hadoop8:9000" );
			    conf.set("mapred.job.tracker", "hadoop1:9001" );
			    String outputPath = "/data/rundata/test/wordcount/output";
			    FileSystem.get( conf ).delete( new Path( outputPath ), true );
			    String[] otherArgs = new String[]{ "/data/rundata/test/wordcount/input",outputPath};
			    if(args.length>1){
			    	otherArgs = args;
			    }
			    Job job = new Job(conf, "word count");
			    job.setJarByClass(NewWordCount.class);
			    job.setMapperClass(TokenizerMapper.class);
			    job.setCombinerClass(IntSumReducer.class);
			    job.setReducerClass(IntSumReducer.class);
			    job.setOutputKeyClass(Text.class);
			    job.setOutputValueClass(IntWritable.class);
			    //job.setInputFormatClass(cls);
			    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
			    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
			    System.out.println( job.getJar() );
			    System.exit(job.waitForCompletion(true) ? 0 : 1);
				return null;
			}
		});
  }
}
