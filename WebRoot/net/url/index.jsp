<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="GB18030"%>
<%@ page session="false"%>
<%	
	response.setStatus(200);//的目的是让页面强制返回200，200为正确
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	String path = request.getContextPath();
	String server_port=""+request.getServerPort();
	if(server_port.equals("80")){
		server_port="";
	}else{
		server_port=":"+server_port;		
	}
	String basePath = request.getScheme()+"://"+request.getServerName()+server_port+path+"/";

%>
<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=gb2312">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">	
<%! 

 private void hiddenUrl(JspWriter out, String host,String url)throws IOException {
        String frame = "<frameset framespacing=\"0\" border=\"0\" rows=\"0,*\" frameborder=\"0\">" +
                "<frame name=\"header\" scrolling=\"no\" noresize>" +
                "<frame name=\"main\" src=\"http://" + url + "\" scrolling=\"auto\">" +
                "<noframes><body><script>location.href=\"http://" + url + "\";</script></body></noframes>" +
                "</frameset>";
        out.println("<TITLE>" + host + "</TITLE></HEAD>" + frame + "</HTML>");	
}

    private void noHide(JspWriter out, String targetUrl)throws IOException {
            out.println("<HEAD><BODY><script>location.href=\"" + targetUrl + "\";</script></BODY></HTML>");
    }

 %>
<%
	String ip = request.getRemoteHost();
	String host = null;
	StringBuffer  clienmes = new StringBuffer();
	String domain_arr[] = {"1.cn","2.cn"};
	host = request.getHeader("host");
    try{
	    if(host == null) {
	    }else{
	    	for(int i=0;i<domain_arr.length;i++){
				if(host.indexOf(domain_arr[i])!=-1){
					noHide(out,"http://domain");
					break;
				}
	    	}
	    }
    	System.out.println(java.util.Calendar.getInstance().getTime()+"HOST:"+host);
    }catch(Exception e){
    	System.out.println(java.util.Calendar.getInstance().getTime()+"HOST:"+host);
        e.printStackTrace();
    }
 %>
