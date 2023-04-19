package dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import models.Employee;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class EmployeeDao {
    @Inject
    Provider<EntityManager> entityManagerProvider;

    @Transactional
    public Employee signupAsAdmin(String email, String password){
        try{
            EntityManager em = entityManagerProvider.get();
            Employee employee = new Employee(email, password, true);
            em.persist(employee);
            return employee;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    @Transactional
    public Employee signup(Employee employee){
        try{
            EntityManager em = entityManagerProvider.get();
            Employee employee1 = new Employee();
            employee1.setEmail(employee.getEmail());
            employee1.setPassword(employee.getPassword());
            employee1.setAdmin(false);
            em.persist(employee1);
            return employee;
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public boolean validateAdmin(String email, String password) throws InvalidAdminException, InvalidPasswordException{
        if(email!=null && password!=null){
            EntityManager em = entityManagerProvider.get();
            Query query = em.createNamedQuery("Employee.getUserByEmail").setParameter("email",email);
            Employee emp = (Employee) query.getSingleResult();
            if(emp!=null){
                if(emp.getPassword().equals(password)){
                    if(emp.isAdmin()){
                        return true;
                    }else{
                        throw new InvalidAdminException("You are not an ADMIN");
                    }
                }else{
                    throw new InvalidPasswordException("Password is invalid");
                }
            }
            return false;
        }
        return false;
    }

    public boolean validateUser(String email, String password) throws InvalidAdminException, InvalidPasswordException{
        if (email != null && password != null) {
            EntityManager em = entityManagerProvider.get();
            Query query = em.createNamedQuery("Employee.getUserByEmail").setParameter("email", email);
            Employee emp = (Employee) query.getSingleResult();
            if (emp != null) {
                if(emp.getPassword().equals(password)){
                    if(!emp.isAdmin()){
                        return true;
                    }else{
                        throw new InvalidAdminException("Student Not Exist");
                    }
                }else{
                    throw new InvalidPasswordException("Password is invalid");
                }
            }
            return false;
        }
        return false;
    }

    public boolean isUser(String email) throws Exception{
        try{
            EntityManager em = entityManagerProvider.get();
            Query query = em.createNamedQuery("Employee.getUserByEmail").setParameter("email", email);
            Employee emp = (Employee) query.getSingleResult();
            if(emp!=null && !emp.isAdmin()){
                return true;
            }
            return false;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }


    public boolean isAdmin(String email) throws Exception{
        try{
            EntityManager em = entityManagerProvider.get();
            Query query = em.createNamedQuery("Employee.getUserByEmail").setParameter("email", email);
            Employee emp = (Employee) query.getSingleResult();
            if(emp!=null && emp.isAdmin()){
                return true;
            }
            return false;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    public Employee resetPass(String oldpassword, String newpassword,  String email) throws Exception{
        try {
            EntityManager em = entityManagerProvider.get();
            Query query = em.createNamedQuery("Employee.getUserByEmail").setParameter("email", email);
            Employee emp = (Employee) query.getSingleResult();
            if(emp!=null) {
                if(emp.getPassword().equals(oldpassword)){
                    emp.setPassword(newpassword);
                    em.persist(emp);
                }
                else{
                    throw new Exception("Invalid Password");
                }
            }
            return emp;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }


}
