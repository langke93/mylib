package org.langke.net.network.crawl;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * 用Jsoup对用户输入内容的HTML做安全过滤
 * @author langke
 *
 */
public class HtmlClean {

    private void simpleClean() {
      //HTML clean
        String unsafe = "<table><tr><td>1</td></tr></table>" +
                "<img src='' alt='' />" +
                "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a>" +
                "<object></object>" +
                "<script>alert(1);</script>" +
                "</p>";
        String safe = Jsoup.clean(unsafe, Whitelist.relaxed());
        System.out.println("safe: " + safe);
    }
    
    public static void main(String[] args) {
        HtmlClean htmlClean = new HtmlClean();
        htmlClean.simpleClean();
    }

}
