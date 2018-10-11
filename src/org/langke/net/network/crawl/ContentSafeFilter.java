package org.langke.net.network.crawl;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.safety.Whitelist;

/**
 * 用Jsoup对用户输入内容的HTML做安全过滤
 * @author langke
 *
 */
public class ContentSafeFilter {
    private final static Whitelist userContentFilter = Whitelist.relaxed();
    static {
        //增加可信标签到白名单
        userContentFilter.addTags("embed","object","param","span","div");
        //增加可信属性
        userContentFilter.addAttributes(":all", "style", "class", "id", "name");
        userContentFilter.addAttributes("object", "width", "height","classid","codebase");
        userContentFilter.addAttributes("param", "name", "value");
        userContentFilter.addAttributes("embed", "src","quality","width","height","allowFullScreen","allowScriptAccess","flashvars","name","type","pluginspage");
    }
 
    /**
     * 对用户输入内容进行过滤
     * @param html
     * @return
     */
    public static String filter(String html) {
        if(StringUtil.isBlank(html)) return "";
        return Jsoup.clean(html, userContentFilter);
        //return filterScriptAndStyle(html);
    }
 
    /**
     * 比较宽松的过滤，但是会过滤掉object，script， span,div等标签，适用于富文本编辑器内容或其他html内容
     * @param html
     * @return
     */
    public static String relaxed(String html) {
        return Jsoup.clean(html, Whitelist.relaxed());
    }
 
    /**
     * 去掉所有标签，返回纯文字.适用于textarea，input
     * @param html
     * @return
     */
    public static String pureText(String html) {
        return Jsoup.clean(html, Whitelist.none());
    }
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        String unsafe = "<table><tr><td>1</td></tr></table>" +
                "<img src='' alt='' />" +
                "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a>" +
                "<object></object>" +
                "<script>alert(1);</script>" +
                "</p>";
        String safe = ContentSafeFilter.filter(unsafe);
        System.out.println("filter: " + safe);
        System.out.println("relaxed: " + ContentSafeFilter.relaxed(unsafe));
        System.out.println("pureText: " + ContentSafeFilter.pureText(unsafe));
    }
 
}