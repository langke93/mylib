package org.langke.util.xml;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Set;
import java.io.File;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class DOM4jParser {

  public DOM4jParser() {
  }
  public static HashMap parse(String fileName) throws Exception {
    HashMap result = new HashMap();
    SAXReader reader = new SAXReader();
    File file = new File(fileName);
    Document doc = reader.read(file);
    Element root = doc.getRootElement();
    Iterator it = root.elementIterator();
    while(it.hasNext()) {
      Element element = (Element)it.next();
      Iterator ita = element.elementIterator();
      while(ita.hasNext()) {
        Element elementa = (Element)ita.next();
        result.put(
            root.getName()+"."+element.getName()+"."+elementa.getName(), elementa.getText());
      }
     }
     return result;
   }   
   
   public static void main(String args[]) throws Exception {
     HashMap hm = DOM4jParser.parse("datan.xml");
     Set set = hm.keySet();
     for(Iterator it = set.iterator();it.hasNext();){
       String key = (String)it.next();
       String value = (String)hm.get(key);
       System.out.println(key+"="+value);
     }
   }
}
