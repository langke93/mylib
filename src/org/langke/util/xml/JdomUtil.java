package org.langke.util.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class JdomUtil {
	
	/**
	 * String转JDOM Element
	 * @param xmlString
	 * @return
	 */
	public static Element getJdomElement(String xmlString){
		xmlString = xmlString.replaceAll("UTF-8", "gb2312");//UTF-8无法识别中文
		InputStream inputStream;
		SAXBuilder builder = new SAXBuilder();
		Document document;
		Element element = null;
		try {
			inputStream = new ByteArrayInputStream(xmlString.getBytes());
			document = builder.build(inputStream);		      
		    element = document.getRootElement(); // 获得根节点
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return element;
}

}