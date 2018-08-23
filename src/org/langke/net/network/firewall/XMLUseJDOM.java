package org.langke.net.network.firewall;


import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import java.util.*;
import java.io.*;

import javax.servlet.http.HttpServlet;


/**
 * @Company lk org
 * @author 李庄照
 * @version 1.1
 */
public class XMLUseJDOM extends HttpServlet{
	public boolean error;
    Element e_root; // Root of document
    Document doc; // JDOM document 
    Element e_ip;//ip元素
    List l_ip; // ip列表    
    String file_name;//XML文件名
    
 /**
  *构造方法，创建document
  */	
 public XMLUseJDOM(String r_name)
 {
	this.file_name=r_name;
 	this.buildDocument();
 }
 public void buildDocument() {
      try {
        SAXBuilder builder = new SAXBuilder();
        try {
			buildFromDoc(builder.build(file_name));
		} catch (IOException e) {
			e.printStackTrace();
		}
      } catch (JDOMException e) {
          e.printStackTrace();
      }
    }

    public void buildFromDoc(Document doc) {
      this.doc = doc;
      e_root = doc.getRootElement();
      e_ip = e_root.getChild("ips");
      l_ip = e_ip.getChildren();
    }  

    public int getNumberOfUsers() {
      return l_ip.size();
    }
    
    public List getLusers() {
      return l_ip;
    }
    
    
   
    
    //查询是否存在用户名 true存在
    public String getIp(){
    	String result = "";
    	Iterator it=l_ip.iterator();
    	while(it.hasNext()){
    		Element ele=(Element)it.next();
    		result = ele.getChildText("client_ip");
    	}
    	return result;
    }


    public void deleteByid(int id){
    	 l_ip.remove(id);
    }

   
    public void updateIp(String ip) {
	    Iterator it = l_ip.iterator();
	    while(it.hasNext()){
		      Element ele = (Element)it.next();
		      if(ele.getAttributeValue("id").equals("ip")){
		    	  ele.removeChild("client_ip");
			      ele.addContent(new Element("client_ip").setText(ip));
		      }
		      this.save();//保存文件
	    }
    }

    
    //保存文件
    public void save() {
      XMLOutputter outputter = new XMLOutputter("  ",true);
      outputter.setEncoding("GBK");
      try {
        outputter.setTextNormalize(true);
        FileWriter f = new FileWriter(new File(file_name));
        outputter.output(doc, f);
        f.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
    }    
}