package controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import dao.EmployeeDao;
import models.Employee;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;
import ninja.session.Session;

import javax.persistence.EntityManager;

public class EmployeeController {
    @Inject
    EmployeeDao employeeDao;
    @Inject
    Provider<EntityManager> entityManagerProvider;

    @Inject
    CorsHeadersController cors;

//    Signup as Admin
    public Result signUpAsAdmin(Employee employee) throws Exception{
        try{
            System.out.println(employee.getPassword()+" "+ employee.getEmail());
            Employee emp = employeeDao.signupAsAdmin(employee.getEmail(), employee.getPassword());
            return  cors.addHeaders(Results.json().render(emp));
        }
        catch(Exception e){
            return cors.addHeaders(Results.json().render("Admin with this email already exist"));
        }
    }

//    Signup as USer
    public Result signup(Employee employee) throws Exception{
        try{
            System.out.println(employee.getPassword()+" "+ employee.getEmail());
            Employee emp = employeeDao.signup(employee);
//            return cors.addHeaders(Results.json().render(emp));
            return Results.json().render(emp);
        }
        catch (Exception e){
//            return cors.addHeaders(Results.json().render(e));
            return Results.json().render(e);
        }
    }

//    Login as Admin
    public Result loginAdmin(Employee employee, Session session) throws Exception{
        try{
            System.out.println(employee.getEmail() + " " + employee.getPassword());
            boolean isvalid = employeeDao.validateAdmin(employee.getEmail(), employee.getPassword());
            if(isvalid){
                session.put("email", employee.getEmail());
                System.out.println(session.get("email"));
                return cors.addHeaders(Results.json().render("Admin Logged In"));
            }
            else{
                return cors.addHeaders(Results.json().render("Error, invalid details"));
            }
        }
        catch (Exception e){
            return cors.addHeaders(Results.json().render("Error thrown " + e));
        }
    }

//    Login as User
    public Result login(Employee employee, Session session){
        try{
            System.out.println(employee.getEmail() + " " + employee.getPassword());
            boolean isvalid = employeeDao.validateUser(employee.getEmail(), employee.getPassword());
            System.out.println(isvalid);
            if(isvalid){
                session.put("email", employee.getEmail());
                System.out.println(session.get("email"));
//                return cors.addHeaders(Results.json().render("User Logged In"));
                return Results.json().render("User Logged In");
            }
            else{
//                return cors.addHeaders(Results.json().render("Error, invalid details"));
                return Results.json().render("Error, invalid details");
            }
        }
        catch (Exception e){
//            return cors.addHeaders(Results.json().render("Error thrown " + e));
            return Results.json().render("Error thrown " + e);
        }
    }

//    Logout Either Admin or user
    public Result logout(Context context) {
        // remove any user dependent information
        context.getSession().clear();
        System.out.println("Logout!!!");
        return cors.addHeaders(Results.json().render("Logged Out"));
    }

//    if User is Loggged in or not
    public Result isUserLoggedIn(Session session) throws Exception{
        try {
            System.out.println(session.get("email"));
            if (session.get("email") != null) {
                boolean isUser = employeeDao.isUser(session.get("email"));
                if(isUser) {
                    System.out.println("USer is Logged In");
                    return cors.addHeaders(Results.json().render(true));
                }
                else{
                    System.out.println("USer Not logged In");
                    return cors.addHeaders(Results.json().render(false));
                }
            } else {
                System.out.println("USer Not logged In");
                return cors.addHeaders(Results.json().render(false));
            }
        }
        catch (Exception e){
            return cors.addHeaders(Results.json().render("Error Occurred" + e));
        }
    }


    public Result isAdminLoggedIn(Session session) throws Exception{
        try {
            System.out.println(session.get("email"));
            if (session.get("email") != null) {
                boolean isAdmin = employeeDao.isAdmin(session.get("email"));
                if(isAdmin) {
                    System.out.println("USer is Logged In");
                    return cors.addHeaders(Results.json().render(true));
                }
                else{
                    System.out.println("USer Not logged In");
                    return cors.addHeaders(Results.json().render(false));
                }
            } else {
                System.out.println("USer Not logged In");
                return cors.addHeaders(Results.json().render(false));
            }
        }
        catch (Exception e){
            return cors.addHeaders(Results.json().render("Error Occurred" + e));
        }
    }

    @Transactional
    public Result resetPassword(Employee employee, Session session) throws Exception{
        try{
            System.out.println(session.get("email"));
            if(session.get("email")!=null){
                String oldpassword = employee.getEmail();
                String newpassword = employee.getPassword();
                System.out.println("Old Password : "+ oldpassword + " new Password" + newpassword);
                Employee emp = employeeDao.resetPass(oldpassword, newpassword, session.get("email"));
                return cors.addHeaders(Results.json().render(emp));
            }
            return cors.addHeaders(Results.json().render("Session not login"));
        }
        catch (Exception e){
            return cors.addHeaders(Results.json().render("Error Occured : "+e));
        }
    }



}
