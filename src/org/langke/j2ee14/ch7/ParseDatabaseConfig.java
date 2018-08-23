package org.langke.j2ee14.ch7;

import java.util.Properties;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.net.URL;

/**
 *其它程序通过ParseDatabaseConfig来获得数据库的配置信息，
 *这样使得类之间的耦合松散
 */
public class ParseDatabaseConfig
{
	private Properties props;
	public Properties getProps()
	{
		return this.props;
	}
	/**
	 *解析XML配置文件，把属性保存起来
	 */
	public void parse(String filename)throws Exception
	{
		ConfigParser handler=new ConfigParser();
		SAXParserFactory factory=SAXParserFactory.newInstance();
		factory.setNamespaceAware(false);
		factory.setValidating(false);
		SAXParser parser=factory.newSAXParser();
		URL confURL=ConfigParser.class.getClassLoader().getResource(filename);
		try
		{
			parser.parse(confURL.toString(),handler);
			props=handler.getProps();
		}
		finally
		{
			factory=null;
			parser=null;
			handler=null;
		}
	}

}
