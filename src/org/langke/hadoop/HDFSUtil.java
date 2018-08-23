package org.langke.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.langke.util.cache.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HDFSUtil {
    private static Logger log = LoggerFactory.getLogger(HDFSUtil.class);
    static Configuration config = new Configuration();
    static {
        config.set("fs.defaultFS", "hdfs://hadoop73:9000");
        config.set("dfs.datanode.socket.write.timeout", "0");
        config.set("hadoop.job.ugi", "hadoop");
        
    }

    public static void main(String[] args) throws Exception {
        // createNewHDFSFile("/tmp/create2.c", "hello");

        // System.out.println(readHDFSFile("/tmp/copy.c").toString());
        // mkdir("/tmp/testdir");
        // deleteDir("/tmp/testdir");

/*        final AtomicInteger i = new AtomicInteger();
        final HDFSUtil util = new HDFSUtil();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                StringBuffer sb = new StringBuffer();
                for(int x=0;x<10000;x++){
                    sb.append("test"+i.incrementAndGet()+"\n");
                }
                util.appendFile2HDFS("/soa/soa_logs_20140624.log", sb.toString());
            }
        };
        ExecutorService es = Executors.newFixedThreadPool(100);
        CostTime cost = new CostTime();
        cost.start();
        Future<?> f = null ;
        for(int j=0;j<10;j++){
              es.submit(task);
        }
       es.shutdown();
            log.info("cost:"+cost.cost());*/
        
        deleteHDFSFile("/soa/soa_logs_20140624");
        listAll("/soa");
        //byte[] b = readHDFSFile("/soa/soa_logs_20140624.log");
        //log.info(new String(b));
    }

    public synchronized void appendFile2HDFS(String path,String content){
        FileSystem hdfs = null;
        FSDataOutputStream os = null;
        try {
            hdfs = DistributedFileSystem.get(config);
            Path f = new Path(path);
            if(!hdfs.exists(f.getParent())){
                hdfs.mkdirs(f.getParent());
            }
            if(!hdfs.exists(f)){
                os = hdfs.create(f);
                os.write(content.getBytes("UTF-8"));
            }else{
                os = hdfs.append(f);
                os.write(content.getBytes("UTF-8"));
            }
        } catch (IOException e) {
           log.error("",e);
        }finally{
            if(os != null)
                try {
                    os.close();
                } catch (IOException e) {
                    log.error("",e);
                }
            if(hdfs!=null)
                try {
                    hdfs.close();
                } catch (IOException e) {
                    log.error("",e);
                }
        }
    }
    
    /*
     * upload the local file to the hds notice that the path is full like
     * /tmp/test.c
     */
    public static void uploadLocalFile2HDFS(String s, String d)
            throws IOException {
        FileSystem hdfs = FileSystem.get(config);

        Path src = new Path(s);
        Path dst = new Path(d);

        hdfs.copyFromLocalFile(src, dst);

        hdfs.close();
    }

    /*
     * create a new file in the hdfs. notice that the toCreateFilePath is the
     * full path and write the content to the hdfs file.
     */
    public static void createNewHDFSFile(String toCreateFilePath, String content)
            throws IOException {
        FileSystem hdfs = FileSystem.get(config);
        FSDataOutputStream os = hdfs.create(new Path(toCreateFilePath));

        os.write(content.getBytes("UTF-8"));

        os.close();

        hdfs.close();
    }

    /*
     * delete the hdfs file notice that the dst is the full path name
     */
    public static boolean deleteHDFSFile(String dst) throws IOException {
          FileSystem    hdfs = FileSystem.get(config);  
        
        Path path = new Path(dst);
        boolean isDeleted = hdfs.delete(path);

        hdfs.close();

        return isDeleted;
    }

    /*
     * read the hdfs file content notice that the dst is the full path name
     */
    public static byte[] readHDFSFile(String dst) throws Exception {
        FileSystem fs = FileSystem.get(config);

        // check if the file exists
        Path path = new Path(dst);
        if (fs.exists(path)) {
            FSDataInputStream is = fs.open(path);
            // get the file info to create the buffer
            FileStatus stat = fs.getFileStatus(path);

            // create the buffer
            byte[] buffer = new byte[Integer.parseInt(String.valueOf(stat
                    .getLen()))];
            is.readFully(0, buffer);

            is.close();
            fs.close();

            return buffer;
        } else {
            throw new Exception("the file is not found .");
        }
    }

    /*
     * make a new dir in the hdfs
     * 
     * the dir may like '/tmp/testdir'
     */
    public static void mkdir(String dir) throws IOException {
        FileSystem fs = FileSystem.get(config);

        fs.mkdirs(new Path(dir));

        fs.close();
    }

    /*
     * delete a dir in the hdfs
     * 
     * dir may like '/tmp/testdir'
     */
    public static void deleteDir(String dir) throws IOException {
        FileSystem fs = FileSystem.get(config);

        fs.delete(new Path(dir));

        fs.close();
    }

    public static void listAll(String dir) throws IOException {
        FileSystem fs = FileSystem.get(config);

        FileStatus[] stats = fs.listStatus(new Path(dir));

        for (int i = 0; i < stats.length; ++i) {
            if (stats[i].isFile()) {
                // regular file
                System.out.println(stats[i].getPath().toString());
            } else if (stats[i].isDirectory()) {
                // dir
                System.out.println(stats[i].getPath().toString());
            } else if (stats[i].isSymlink()) {
                // is s symlink in linux
                System.out.println(stats[i].getPath().toString());
            }

        }
        fs.close();
    }

}
