package org.langke.net.network.crawl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.net.www.protocol.http.HttpURLConnection;

public class Mofang {

    Log log = LogFactory.getLog(Mofang.class);
    
    public   String  getUrl(String url) throws UnsupportedEncodingException{
        StringBuilder sb = new StringBuilder();
        HttpURLConnection conn ;
        InputStream inputStream = null;
        int c;
        try {
            System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
            //urlObj = new URL(url);
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestProperty("Cookie", "miid=2065148463871201638; whl=-1%260%260%260; tk_pass=xT5P6FZG7b%2Fl20KHefRzqbPVRirIeR87; tk_trace=oTRxOWSBNwn9dPyscxqAz9fIOLXI0m9ok%2BU7pUO4VcAOOCOUhZiNRIW5XPEmZnmHHD7I%2BF6VerW2ZKw5o8wTp6t2azqLd1WPVqUqjrEoQVz39X1OgAVH05zLm6Rj1XDljin8uNTSo0mq1nNZOnQXhf6gBbai8eHEeDW4i6qtMOejBoqb%2FNKTS0TvYJaYCG5n8xAKXFP%2B5nSgl9eetGOpHcNP0aAOHTmFJMjF6yxM1Z3iCHkqPTm5xynH4COTQZrM3tsbfqaBxo3kGem%2FFRXA5ihkdnp55y%2FskcTnFeVb1uYur%2Blnjgm8qeg%3D; cna=LWfICavtnyACAWw7QtrtKVHS; mpp=t%3D1%26m%3D%26h%3D1367743178765%26l%3D1367743065805; lastgetwwmsg=MTM4MjY3OTczNA%3D%3D; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; tlut=UoLU7Rs5YW2YOg%3D%3D; mobileSendTime=-1; credibleMobileSendTime=-1; ctuMobileSendTime=-1; riskMobileBankSendTime=-1; riskMobileAccoutSendTime=-1; riskMobileCreditSendTime=-1; riskCredibleMobileSendTime=-1; overLimit=false; overLimitedProduct=; lzstat_uv=1928978193671705702|1733124@2738597@2728989@2728987@1836558@3349927@1544272@878758; lzstat_ss=3928114916_1_1370343376_1733124|886939670_1_1398268315_2738597|3799712501_0_1378890889_2728989|651430029_0_1378890892_2728987|3539132767_0_1380119121_1836558|510391704_0_1386786268_3349927|3908077985_0_1390988879_1544272|3074265579_0_1392810802_878758; _tb_token_=wUWKx4vKN6yz; l=langke93::1404833332414::11; ali_ab=218.106.151.194.1405314014991.3; sid_p=; v=0; uc1=lltime=1405385142&cookie14=UoW3uit9GiZOTQ%3D%3D&existShop=true&cookie16=W5iHLLyFPlMGbLDwA%2BdvAGZqLg%3D%3D&cookie21=W5iHLLyFfXVRCJf5lG0u7A%3D%3D&tag=1&cookie15=Vq8l%2BKCLz3%2F65A%3D%3D; uc3=nk2=pfl4NKayDajG8w%3D%3D&id2=VWsthffjmE2Z&vt3=F8dATHwJ9NsyIuTA48M%3D&lg2=VFC%2FuZ9ayeYq2g%3D%3D; existShop=MTQwNTM4NTE1Mg%3D%3D; sdi=6279dbdbcdcad81f6af845645de91264; lgc=%5Cu82F9%5Cu679C%5Cu9C7C1118; tracknick=%5Cu82F9%5Cu679C%5Cu9C7C1118; cookie2=6279dbdbcdcad81f6af845645de91264; sg=89d; mt=np=&ci=0_1&cyk=0_0; cookie1=AimaCRqQHJ4Z4%2B2AFQTs7KvrWNm81pYQEJdMwdNlbcQ%3D; unb=645571119; snk=%5Cu82F9%5Cu679C%5Cu9C7C1118; t=06dea508c3f0541420d3760d2812199c; _cc_=VFC%2FuZ9ajQ%3D%3D; tg=0; _l_g_=Ug%3D%3D; _nk_=%5Cu82F9%5Cu679C%5Cu9C7C1118; cookie17=VWsthffjmE2Z; sid_s=NDUxNTY5NTA1MjY4NzA3OSpeKjE0MDUzODUxNTIyMzk; version=s; CNZZDATA1000181376=1456902959-1405314061-http%253A%252F%252Fshuju.taobao.com%252F%7C1405385126; isg=15E8A48147FBCE4E5E929592053D7BF7");
            conn.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
            conn.setRequestProperty("Host", "mofang.taobao.com");
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36");
            conn.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
            conn.setRequestProperty("Referer", "http://mofang.taobao.com/s/app/basic");
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            conn.setRequestProperty("Connection", "keep-alive");
            inputStream = conn.getInputStream();
            while((c=inputStream.read())!=-1){
                //System.out.write(c);
                sb.append((char)c);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String s = new String(sb.toString().getBytes("iso-8859-1"),"UTF-8");
        log.info(s);
        return s;
    }
    
    public void getKeywordsData(){
        
        try {
            String keyword = URIUtil.encodeQuery("凉鞋 女 坡跟");
            String url =  "http://mofang.taobao.com/s/proxy/query/1202/r1:2014-07-13/r2:2014-07-13/f0:2014-07-13/tfix:200/tid:1/dfix:1/query:"+keyword+"?27400-599746-390374" ;
            String jsonstr = getUrl(url);
        } catch (Exception e) {
            log.error(e);
        }
    }
    public static void main(String[] args) throws IOException {
        
        final Mofang reg = new Mofang();
        reg.getKeywordsData();
    }
}

