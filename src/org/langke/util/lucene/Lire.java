package org.langke.util.lucene;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
 
import javax.imageio.ImageIO;
 
import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.DocumentBuilderFactory;
import net.semanticmetadata.lire.ImageDuplicates;
import net.semanticmetadata.lire.ImageSearchHits;
import net.semanticmetadata.lire.ImageSearcher;
import net.semanticmetadata.lire.ImageSearcherFactory;
 
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
 
public class Lire {
 
    private static String INDEX_PATH = "E:/log/lire/index";// 索引文件存放路径
 
    //要索引的图片文件目录
    private static String INDEX_FILE_PATH = "D:/data/media/photo/融侨宜家";
 
    private static String SEARCH_FILE = "C:/Users/Administrator/Pictures/宜家2013.09.07/DSC04049.JPG";//用于搜索的图片
 
    @Test
    public void createIndex() throws Exception {
        //创建一个合适的文件生成器，Lire针对图像的多种属性有不同的生成器
        DocumentBuilder db = DocumentBuilderFactory.getCEDDDocumentBuilder();
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_33, new SimpleAnalyzer(Version.LUCENE_33));
        IndexWriter iw = new IndexWriter(FSDirectory.open(new File(INDEX_PATH)), iwc);
        File parent = new File(INDEX_FILE_PATH);
        try{
            for (File f : parent.listFiles()) {
                // 创建Lucene索引
                if(f.getName().toLowerCase().endsWith("jpg")){
                    System.out.println("add index "+f.getName());
                    Document doc = db.createDocument(new FileInputStream(f), f.getName());
                    // 将文件加入索引
                    iw.addDocument(doc);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            iw.close();
        }
        //iw.optimize();
    }
 
    @Test
    public void searchSimilar() throws Exception {
        IndexReader ir = IndexReader.open(FSDirectory.open(new File(INDEX_PATH)));//打开索引
        ImageSearcher is = ImageSearcherFactory.createDefaultSearcher();//创建一个图片搜索器
        FileInputStream fis = new FileInputStream(SEARCH_FILE);//搜索图片源
        BufferedImage bi = ImageIO.read(fis);
        ImageSearchHits ish = is.search(bi, ir);//根据上面提供的图片搜索相似的图片
        for (int i = 0; i < 10; i++) {//显示前10条记录（根据匹配度排序）
            System.out.println(ish.score(i) + ": " + ish.doc(i).getField(DocumentBuilder.FIELD_NAME_IDENTIFIER).stringValue());
        }
        Document d = ish.doc(0);//匹配度最高的记录
        ish = is.search(d, ir);// 从结果集中再搜索
        for (int i = 0; i < 4; i++) {
            System.out.println(ish.score(i) + ": " + ish.doc(i).getField(DocumentBuilder.FIELD_NAME_IDENTIFIER).stringValue());
        }
    }
 
    @Test//测试前先将包含重复图片的文件进行索引
    public void searchDuplicates() throws Exception {
        IndexReader ir = IndexReader.open(FSDirectory.open(new File(INDEX_PATH)));
        ImageSearcher is = ImageSearcherFactory.createDefaultSearcher();
        ImageDuplicates id = is.findDuplicates(ir);//查找重复的图片，如果没有，则返回null
        for (int i = 0; id != null && i < id.length(); i++) {
            System.out.println(id.getDuplicate(i).toString());
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }
}
