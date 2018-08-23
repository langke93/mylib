package org.langke.data.imp;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpClientTest {
	final static String target_url = "http://192.168.1.5/sqcn/member.do";
	 public static void main(String[] args) throws Exception {
	        HttpClient client = new HttpClient();
	        PostMethod method = new PostMethod(target_url);
	        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
	        int result = client.executeMethod(method);
	      if (result == HttpStatus.SC_OK) {
	            method = new PostMethod(target_url);
	            method.getParams().setContentCharset("GBK");
	            method.setRequestBody(new NameValuePair[] {
	                    new NameValuePair("method", "memberRegiest"),
	                    new NameValuePair("u_nme", "用户名"),
	                    new NameValuePair("u_pss", "ooxx")
	            });
	            if (client.executeMethod(method) == HttpStatus.SC_OK) {
	                System.out.println(method.getResponseBodyAsString());
	            }
	        }
	        method.releaseConnection();
            if (client != null) {
                try {
                    ((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown();
                } catch (Exception e) {
                	e.printStackTrace();
                }
                client = null;
            }	        
	        //System.out.println("\u4e2a\u4f53\u7ecf\u8425~1|" + "  \u79c1\u8425\u72ec\u8d44\u4f01\u4e1a~2|" + "  \u79c1\u8425\u5408\u4f19\u4f01\u4e1a~3|" + "  \u79c1\u8425\u6709\u9650\u8d23\u4efb\u516c\u53f8~4|" + "  \u79c1\u8425\u80a1\u4efd\u6709\u9650\u516c\u53f8~5|" + "  \u56fd\u6709\u4f01\u4e1a~6|" + "  \u96c6\u4f53\u4f01\u4e1a~7|" + "  \u80a1\u4efd\u5408\u4f5c\u4f01\u4e1a~8|" + "  \u8054\u8425\u4f01\u4e1a~9|" + "  \u6709\u9650\u8d23\u4efb\u516c\u53f8~10|" + "  \u6709\u9650\u8d23\u4efb\u516c\u53f8(\u56fd\u6709\u72ec\u8d44)~11|" + "  \u4e00\u4eba\u6709\u9650\u8d23\u4efb\u516c\u53f8~12|" + "  \u5176\u4ed6\u6709\u9650\u8d23\u4efb\u516c\u53f8~13|" + "  \u80a1\u4efd\u6709\u9650\u516c\u53f8~14|" + "\u5176\u4ed6\u5185\u8d44\u4f01\u4e1a~15|" + "  \u4e09\u6765\u4e00\u8865~16|" + "\u6cd5\u4eba\u5206\u652f\u673a\u6784~17|" + "  \u5408\u8d44\u7ecf\u8425\u4f01\u4e1a(\u6e2f\u6216\u6fb3\u3001\u53f0\u8d44)~18|" + "  \u5408\u4f5c\u7ecf\u8425\u4f01\u4e1a(\u6e2f\u6216\u6fb3\u3001\u53f0\u8d44)~19|" + "  \u6e2f\u3001\u6fb3\u3001\u53f0\u5546\u72ec\u8d44\u7ecf\u8425\u4f01\u4e1a~20|" + "  \u6e2f\u3001\u6fb3\u3001\u53f0\u5546\u6295\u8d44\u80a1\u4efd\u6709\u9650\u516c\u53f8~21|" + "  \u4e2d\u5916\u5408\u8d44\u7ecf\u8425\u4f01\u4e1a~22|" + "  \u4e2d\u5916\u5408\u4f5c\u7ecf\u8425\u4f01\u4e1a~23|" + "  \u5916\u8d44\u4f01\u4e1a~24|" + "\u5176\u4ed6~25");

	    }
}
