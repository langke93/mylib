package org.langke.j2ee14.ch7;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
/**
 *雇员管理GUI程序
 */
public class EmployeeGUI
{
	
	JFrame topLevel;//顶层窗口容器
	JPanel jPanel,down;//一个面板，它放入topLevel中
	Container contentPane;//容器
	JLabel jLabel;//标签
	JTextField jTextField;//单行文本
	JTable table;//表格

    int amount;
    Collection employees;
    EmployeeBusiness business;
	
	JButton search,findAll,findBySex,delete,add;
	
	public EmployeeGUI()throws EmployeeException
	{
		business=new EmployeeBusinessImpl();
		employees=business.getAllEmployees();
		amount=employees.size();
		System.out.println(amount);
	}
	public static void main(String[] args)	{
		
		try
		{
			new EmployeeGUI().go();
		}
		catch(EmployeeException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 *初始化GUI程序
	 */
	public void go()
	{
		
		topLevel=new JFrame("Swing GUI");
		jPanel=new JPanel();
		down=new JPanel();
		jTextField=new JTextField(20);

		
		search=new JButton("按ID查找");
		findAll=new JButton("查看所有");
		findBySex=new JButton("按性别查找");
		add=new JButton("增加雇员");
		delete=new JButton("删除");
		
		search.addMouseListener(new _MouseListener());
		findAll.addMouseListener(new _MouseListener());
		findBySex.addMouseListener(new _MouseListener());
		add.addMouseListener(new _MouseListener());
		delete.addMouseListener(new _MouseListener());
		
		table=new JTable(amount+5, 7) ;
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(30);

		table.setValueAt("ID", 0, 0) ;
		table.setValueAt("姓名", 0, 1) ;
		table.setValueAt("年龄", 0, 2) ;
		table.setValueAt("月薪", 0, 3) ;
		table.setValueAt("性别", 0, 4) ;
		table.setValueAt("备注", 0, 5) ;
		table.setValueAt("其它", 0, 6) ;
		
		showResult();
				
		contentPane=topLevel.getContentPane();//获得JFrame的ContentPane
		contentPane.setLayout(new BorderLayout());//设置布局管理器
	
		
		jPanel.setLayout(new FlowLayout());
		jPanel.add(table);
		
		down.add(jTextField);
		down.add(search);
		down.add(findAll);
		down.add(findBySex);
		down.add(add);
		down.add(delete);
		contentPane.add(jPanel,BorderLayout.NORTH);//把JPanel加入到ContentPane里
		contentPane.add(down,BorderLayout.SOUTH);//把JPanel加入到ContentPane里	
		
		topLevel.pack();
		topLevel.setVisible(true);
	}
	/**
	 *在table中显示结果
	 */
	public void showResult()
	{
			clearTable();
			Iterator employee_i=employees.iterator();
			int i=1;
			while(employee_i.hasNext())
			{
				Employee employeevo=(Employee)employee_i.next();
				
				table.setValueAt( Integer.toString(employeevo.id), i, 0) ;
				table.setValueAt(trans(employeevo.name), i, 1) ;
				table.setValueAt( Integer.toString(employeevo.age), i, 2) ;
				table.setValueAt( Float.toString(employeevo.salary), i, 3) ;				
				table.setValueAt(new String((employeevo.sex==0)?"男":"女"), i, 4) ;
				table.setValueAt(trans(employeevo.description), i, 5) ;
				i++;
	        }
	        table.doLayout();
	    
    }
    /**
     *清除table中的数据
     */
    public void clearTable()
    {
    	for(int i=1;i<table.getRowCount();i++)
    	{
    		for(int y=0;y<7;y++)
    		table.setValueAt("",i,y);
    	}
    }
    		
	/**
	 *事件监听类
	 */	
	class _MouseListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if(((JButton)e.getSource()).getLabel().equals("按ID查找"))
			{
							
				Employee employee=null;
				try
				{
					employee=business.getTheEmployeeDetail(Integer.parseInt((String)jTextField.getText()));
				}
				catch(EmployeeException e1)
				{
					e1.printStackTrace();
				}
				employees.clear();
				employees.add(employee);
				showResult();
			}
			if(((JButton)e.getSource()).getLabel().equals("按性别查找"))
			{
				try
				{
					 employees=business.getEmployeeBySex((((String)jTextField.getText()).equals("男"))?0:1);
				}
				catch(EmployeeException e2)
				{
					e2.printStackTrace();
				}
				 showResult();
			}
			if( ((JButton)e.getSource()).getLabel().equals("查看所有"))
			{
				try
				{
					employees=business.getAllEmployees();
				}
				catch(EmployeeException e3)
				{
					e3.printStackTrace();
				}
				showResult();				
			}
			if( ((JButton)e.getSource()).getLabel().equals("增加雇员"))
			{
				try
				{
					Employee temp=new Employee();
					
					temp.id=Integer.parseInt(table.getValueAt(employees.size()+1,0).toString());					
					temp.name=table.getValueAt(employees.size()+1,1).toString();
					temp.age=Integer.parseInt(table.getValueAt(employees.size()+1,2).toString());;
					temp.salary=Float.parseFloat(table.getValueAt(employees.size()+1,3).toString());
					temp.sex=((table.getValueAt(employees.size()+1,4).toString()).equals("男"))?0:1;				
					temp.description=table.getValueAt(employees.size()+1,5).toString();	
						
					business.addEmployee(temp);
				}				
				catch(EmployeeException e4)
				{
					e4.printStackTrace();
				}						
			}
			if( ((JButton)e.getSource()).getLabel().equals("删除"))
			{
				try
				{
				   business.deleteEmployee(Integer.parseInt((String)jTextField.getText()));
				   employees=business.getAllEmployees();
				}
				catch(EmployeeException e5)
				{
					e5.printStackTrace();
				}
				showResult();					
			}
			
		}
	}
	/**
	 *帮助方法，用于进行编码转换
	 */
	String trans(String chi)
    {
        String result = null;
           byte temp [];
             try
              {
                       temp=chi.getBytes("iso-8859-1");
                      result = new String(temp);
                }
                catch(java.io.UnsupportedEncodingException e)
                {
                        System.out.println (e.toString());
                }
			return result;
	}
}