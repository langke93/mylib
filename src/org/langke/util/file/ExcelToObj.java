package org.langke.util.file;

import java.io.*;
import jxl.*;
import java.util.*;

public class ExcelToObj {
	  /**
	   *  将excel解析为某对象的列表
	   * @param is  excel文件流
	   * @param getval  excel列所对应的实体对象方法字符串,顺序必须与excel列先后顺序一致（格式为"setId,setName,setRemark" setId对应excel第一列,setName对应excel第二列,以此类推)
	   * @param objectname  实体对应的路径+名称（完整路径）
	   * @return 实体对象的列表
	   * @throws Exception
	   */
	  public List change(InputStream is,String excelsetname,String objectname) throws Exception {
		  String getval[]=excelsetname.split(",");//将实体方法解析为数组
		  List list=new ArrayList();
		    try {
		      //构建Workbook对象, 只读Workbook对象
		      //从输入流创建Workbook
		      jxl.Workbook rwb = Workbook.getWorkbook(is);
		      //获取第一张Sheet表
		      Sheet rs = rwb.getSheet(0);
		      //取得数记录数和列数
		      int iRows = rs.getRows();
		      int iColumns = rs.getColumns();
		      Class c=	Class.forName(objectname); 
		      java.lang.reflect.Method m[] = c.getMethods();
	 	      //取得Excel工作薄中的每一行和每一列的值
		      for (int i = 0; i < iRows; i++) {
     	    	List nextval=new ArrayList();  
     	    	Object obj = c.newInstance();//动态获得一个new对象
		        for (int j = 0; j < iColumns; j++) {
     	          //取得Excel一行中的每一个列
		         Cell cell = rs.getCell(j, i);
		        	 for(int k=0;k<m.length;k++){
		        		 if(getval[j].equals(m[k].getName())){//判断该方法与传进来的方法是否一致	 
		        			 Class type[] = m[k].getParameterTypes();//获得该方法参数类型
		        			 if(type.length>0){
		        			     String typeval=type[0].toString();
		        				 if(typeval.equals("Integer")||typeval.equals("INTEGER")||typeval.equals("class java.lang.Integer")||typeval.equals("java.lang.Integer")){
		        					 Integer intval=Integer.valueOf(cell.getContents());
                                     Object para[] = new Object[1];
                                     para[0]=intval;
                                     m[k].invoke(obj,para);
		        				 }
		        				 if(typeval.equals("String")||typeval.equals("STRING")||typeval.equals("class java.lang.String")||typeval.equals("java.lang.String")){
		        					 String strval=cell.getContents();
		        					 Object para[] = new Object[1];
                                     para[0]=strval;
                                     m[k].invoke(obj,para);
		        				 }
		        				 if(typeval.equals("DATE")||typeval.equals("date")||typeval.equals("class java.lang.Date")){
		        					 System.out.println("DATE类型");
		        				 }
		           			 }
		        		 }
		        	 }
		        }
		       nextval.add(obj);
		       list.add(nextval);
		      }
		      //操作完成时，关闭对象，释放占用的内存空间
		      rwb.close();
		      return list;
	    }
	    catch (jxl.read.biff.BiffException ex) {
	        ex.printStackTrace();
	        return null;
	    }
	  }
	  /**
	   * 将excel解析为嵌套列表
	   * @param is  excel文件流
	   * @return
	   * @throws Exception
	   */
	  public List getList(InputStream is) throws Exception {
		  //System.out.println("执行第二个方法!");
		  List list=new ArrayList();
		    try {
		      //构建Workbook对象, 只读Workbook对象
		      //从输入流创建Workbook
		      jxl.Workbook rwb = Workbook.getWorkbook(is);
		      //获取第一张Sheet表
		      Sheet rs = rwb.getSheet(0);
		      //取得数记录数和列数
		      int iRows = rs.getRows();
		      int iColumns = rs.getColumns();
	          //取得Excel工作薄中的每一行和每一列的值
		      for (int i = 0; i < iRows; i++) {
		    	  List nextval=new ArrayList();  
		        for (int j = 0; j < iColumns; j++) {
		          //取得Excel一行中的每一个列
	             Cell cell = rs.getCell(j, i);
		         nextval.add(cell.getContents());
		        }
		       list.add(nextval);
		      }
		     // System.out.println("列数==="+iColumns);
	         // System.out.println("行数===="+list.size());
		      //操作完成时，关闭对象，释放占用的内存空间
		      rwb.close();
		      return list;
	    }
	    catch (jxl.read.biff.BiffException ex) {
	        ex.printStackTrace();
	        return null;
	    }
	  }
	  

}
