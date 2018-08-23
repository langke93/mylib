package org.langke.net.network.crawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SportReg {

    Log log = LogFactory.getLog(SportReg.class);
    
    static ExecutorService es = Executors.newFixedThreadPool(2);
    static Map<String,String> cookies = new HashMap<String,String>();
    static{
        cookies.put("ASP.NET_SessionId", "4zslql45xxy5pt45tp1n4n55");
        cookies.put("SBVerifyCode", "pmAgUMVTHuJoZfTYXUDY91xYJiI=");
        cookies.put("SpaceBuilder-UserCookie:5463", "ClubMemberSortBy=2");
        cookies.put("SBVerifyCode_CurrentLevel", "Normal");
        cookies.put(".SPBForms", "F324CFF90CA77F45524F238456566F916A4B2B60F70D48A45A20EA9E410154346C3B6BBADF488820574A2D737BADD6E7213E8D6E318964AFD372A2230B56BFB641834A27E6541B22");
    }
    
    public void login() throws IOException{
        Connection.Response res = Jsoup.connect("http://10.5.17.74/User/Login.aspx")
                //.data("userName", "", "password", "")
                .method(Method.POST)
                .header("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36")
                .execute();

            Document doc = res.parse();
            log.info(doc);
            //这儿的SESSIONID需要根据要登录的目标网站设置的session Cookie名字而定
            String sessionId = res.cookie("SESSIONID"); 
            log.info(sessionId);
    }

    public List<String> whatEvents() throws IOException{
        List<String> ids = new ArrayList<String>();
        TreeSet<Integer> idSet = new TreeSet<Integer>();
        Connection.Response res = Jsoup.connect("http://10.5.17.74/whatEvents.aspx")
                .method(Method.GET)
                .header("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36")
                .cookies(cookies)
                .timeout(10000)
                .execute();

            Document doc = res.parse();
            //log.info(doc);
            Elements ele = doc.getElementsByAttributeValueContaining("title","健身");//doc.getElementsByTag("a");
            for(Element e:ele){
                String href = e.attr("href");
                String id = href.substring(href.indexOf("T-")+2,href.length());
                if(id != null && !id.equals("442"))
                    idSet.add(Integer.parseInt(id));
            }
            log.info(idSet);
            if(!idSet.isEmpty()){
                ids.add(String.valueOf(idSet.pollLast()));
                ids.add(String.valueOf(idSet.pollLast()));
            }
            log.info(ids);
           return ids;
    }

    public boolean reg(String id) throws IOException{
        Connection.Response res = Jsoup.connect("http://10.5.17.74/whatEvents.aspx/RegisteredUserSignUp?eventID="+id)
                .data("email", "langke93@163.com", "body", "sf")
                .method(Method.POST)
                .header("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36")
                .cookies(cookies)
                .execute();

            Document doc = res.parse();
            log.info(doc);
            if(doc.toString().indexOf("恭喜") != -1 || doc.toString().indexOf("重复") != -1)
                return true;
            else
                return false;
    }
    
    public void startReg() {
        List<String> ids;
        boolean isReg = false;
        while(true){
            try {
                ids = whatEvents();
                if(ids == null || ids.isEmpty()){
                    Thread.sleep(9000);
                }else{
                    for(String id:ids){
                        boolean temp = reg(id);
                        isReg = (temp || isReg);
                        if(isReg){
                            log.info("reg success ! break.");
                            break;
                        }
                    }
                    if(isReg){
                        log.info("reg success ! break.");
                        break;
                    }else{
                        Thread.sleep(11000);
                    }
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(11000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                log.error(e);
            }
        }
    }
    
    public void preReg(){  
        String ids[] = {"446"};
        boolean isReg = false;
        while(true){
            try {
                for(String id:ids){
                    boolean temp = reg(id);
                    isReg = (temp || isReg);
                    if(isReg){
                        log.info("preReg success ! break.");
                        break;
                    }
                }
                if(isReg){
                    log.info("preReg success ! break.");
                    break;
                }else{
                    Thread.sleep(9000);
                }
            }catch (Exception e) {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                log.error(e);
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        final SportReg reg = new SportReg();
        Runnable t1 = new Runnable() {
            @Override
            public void run() {
                reg.startReg();
            }
        };
        Runnable t2 = new Runnable() {
            @Override
            public void run() {
                //reg.preReg();
            }
        };
        es.submit(t1);
        es.submit(t2);
        es.shutdown();
    }
}
