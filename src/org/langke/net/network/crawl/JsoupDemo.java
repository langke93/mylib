package org.langke.net.network.crawl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class JsoupDemo {

    private static Log log = LogFactory.getLog(JsoupDemo.class);
    private Set<String> getUrl(String url) {
        Set<String> urlSet = new TreeSet<String>();
        try{
            Map<String,String> cookieMap = new HashMap<String,String>();
            cookieMap.put("oj_wol_dia", "9442df98c559e1c9f9900f7bcad007deef05748b4e9c4878d14488c152520959b9186d07d08898784cb21badf15c587b8419a77d756616149319d6aa2d16d38855c2dbd6amlhbnBpbmdrYW5nQGN5b3UtaW5jLmNvbQ==!userid_type:b64unicode");
            cookieMap.put("ojwolf", "92720fc5a6eb3f61254094d7e8b03357dd110aca24e5ece35fdf40d18c437f220c56dde4");
            cookieMap.put("ojwolf_p", "a4bd7fd4ceb94a77bc334f9b310a7906");
            cookieMap.put("ojwolf_u", "457d34e5a8c375f20a4d2abde75b33e2af0ee2707444938b21e8c111836ff31b");
            Connection.Response res = Jsoup.connect(url)
                    //.data("userName", "", "password", "")
                    .cookies(cookieMap)
                    .header("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36")
                    .execute();

                Document doc = res.parse();
                Elements  eles = doc.getElementsByTag("a");
                for(int i=0;i<eles.size();i++){
                    Element ele = eles.get(i);
                    String uri = ele.attr("href");
                    if(uri.startsWith("/")){
                        uri = "http://mall.jia.com"+uri;
                        log.info(uri);
                        urlSet.add(uri);
                    }
                }
               
        }catch(Exception e){
            log.error(e);
        }
        return urlSet;
    }
    
    private Set<String> readFile(){
        Set<String> urlSet = new TreeSet<String>();
        String fileName="doc/menulist.txt";
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            String line ;
            while((line = br.readLine())!=null){
                urlSet.add(line);
            }
        } catch (Exception e) {
            log.error(e);
        }finally{
                try {
                    if(br!=null)
                        br.close();
                    if(fr!=null)
                        fr.close();
                } catch (IOException e) {
                    log.error(e);
                }
        }
        return urlSet;
    }
    
    private void writeFile(Set<String> urlSet){
        String fileName = "doc/urllist.txt";
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(fileName);
            bw = new BufferedWriter(fw);
            for(String url:urlSet)
                bw.write(url+"\n");
        }catch(Exception e){
            log.error(e);
        }finally{
            try{
                if(bw!=null)
                    bw.close();
                if(fw!=null)
                    fw.close();
            }catch(Exception e){
                log.error(e);
            }
        }
    }
    
    public static void main(String[] args) {
        JsoupDemo demo = new  JsoupDemo();
        Set<String> menuSet = demo.readFile();
        Set<String> urlSet = new TreeSet<String>();
        log.info(menuSet);
        for(String url :menuSet){
            urlSet.addAll(demo.getUrl(url));
        }
        demo.writeFile(urlSet);
    }

}
