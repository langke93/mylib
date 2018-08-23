package org.langke.util.http;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpClientUtil {

	private HttpClient httpClient;

	public HttpClientUtil(){
		httpClient = new HttpClient();
	}

	/**
	 * 提交URL地址，返回结果
	 * @param URL
	 * @param body
	 * @return
	 */
	public String postUrl(String URL,String body){
		String result = null;
		PostMethod postMethod = new PostMethod(URL);
		try {
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			postMethod.getParams().setContentCharset("UTF-8");
			if (body != null) {
				postMethod.setRequestBody(body);
			}
			httpClient.executeMethod(postMethod);
			result = postMethod.getResponseBodyAsString();			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			postMethod.releaseConnection();
		}
		return result;
	}

	public String getUrl(String URL)  {
		String result = null;
		GetMethod getMethod = new GetMethod(URL);
		try{
			getMethod.getParams().setContentCharset("UTF-8");
			httpClient.executeMethod(getMethod);
			result = getMethod.getResponseBodyAsString();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			getMethod.releaseConnection();
		}
		return result;
	}
}
