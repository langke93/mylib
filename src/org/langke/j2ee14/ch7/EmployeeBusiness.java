package org.langke.j2ee14.ch7;
import java.util.*;
/**
 *雇员管理的业务逻辑接口
 */
public interface EmployeeBusiness
{
   /**
    * @return Collection
    */
   public Collection getAllEmployees()throws EmployeeException;
   
   /**
    * @param name
    * @return Employee
    */
   public Employee getTheEmployeeDetail(int id)throws EmployeeException;
   
   /**
    * @return Collection
    */
   public Collection getEmployeeBySex(int sex)throws EmployeeException;
   
   public void addEmployee(Employee emp)throws EmployeeException;
   
   public void deleteEmployee(int id)throws EmployeeException;
}