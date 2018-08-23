package org.langke.j2ee14.ch4;
import java.util.*;

/**
 *book是一个值对象
 */
public class Book implements java.io.Serializable {
  
  /**
   *属性
   */
  private String id;
  private String title;
  private String length;
  private List authors;
  /**
   *属性的getter和setter方法
   */
  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getLength() {
    return length;
  }

  public List getAuthors() {
  	return authors;
  }

  public void setId(String newid) {
  	id = newid;
  }

  public void setTitle(String newtitle) {
  	title = newtitle;
  }

  public void setLength(String newlength) {
  	length = newlength;
  }

  public void setAuthors(List newauthors) {
  	authors = newauthors;
  }

}