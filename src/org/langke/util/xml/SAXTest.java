package org.langke.util.xml;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXTest extends DefaultHandler{

  private static Map<String, String> map = new TreeMap<String, String>();
  private String curKey = "";
  private String key = "";
  private String value = "";

  public static void main(String[] args) throws ParserConfigurationException,
      SAXException, IOException {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser parser = factory.newSAXParser();
    parser.parse(new File("datan.xml"), new SAXTest());
    
    // 输出 map
    for(Map.Entry<String, String> entry : map.entrySet()) {
      System.out.println(entry.getKey() + " --> " + entry.getValue());
    }
  }

  public void startElement(String namespaceURI, String localName,
      String qName, Attributes attributes) throws SAXException {
    if (key.length() == 0) {
      key = qName;
    } else {
      key = key + "." + qName;
    }
    curKey = qName;
  }

  public void endElement(String uri, String localName, String qName)
      throws SAXException {
    if (key.contains(".")) {
      if (curKey.equals(qName)) {
        map.put(key, value);
      }
      key = key.substring(0, key.lastIndexOf(qName) - 1);
    }
  }

  public void characters(char[] ch, int start, int length) {
    value = new String(ch, start, length).trim();
  }
}
