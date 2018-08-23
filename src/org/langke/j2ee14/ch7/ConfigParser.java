package org.langke.j2ee14.ch7;
import  org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;

import java.util.Properties;

/**
 *ConfigParser扩展了DefaultHandler，它用于获得数据库的连接属性
 */
public class ConfigParser extends DefaultHandler
{
	private String currentSet;
	private Properties props;
	private String currentName;
	private StringBuffer currentValue =new StringBuffer();
	public ConfigParser()
	{
		this.props=new Properties();
	}
	public Properties getProps()
	{
		return this.props;
	}
	
	public void startElement(String uri,String localName,String qName,Attributes attributes)throws SAXException
	{
		currentValue.delete(0,currentValue.length());
		this.currentName=qName;
	}
	public void characters(char[] ch, int start, int length) throws SAXException { 

	currentValue.append(ch, start, length);

	}

    /**
     *把XML配置文件的中相关的属性保存到Properties对象中
     */
	public void endElement(String uri,String localName,String qName)throws SAXException
	{
		props.put(qName.toLowerCase(),currentValue.toString().trim());
	}
}

	
	