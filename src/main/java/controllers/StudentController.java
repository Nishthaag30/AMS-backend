package controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import dao.AttendanceDao;
import dao.AttendanceListDto;
import dao.StudentDao;
import models.Attendance;
import models.Student;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;
import ninja.session.Session;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class StudentController {
    @Inject
    Provider<EntityManager> entityManagerProvider;
    @Inject
    CorsHeadersController cors;
    @Inject
    StudentDao studentDao;
    @Inject
    AttendanceDao attendanceDao;


    public Result addStudent(Student student) throws  Exception{
        try{
            System.out.println(student);
            Student std = studentDao.addStudent(student.getName(), student.getDOB(), student.getEmail(), student.getMobile(), student.getAddress());
            return cors.addHeaders(Results.json().render(student));
        }
        catch (Exception e){
            return cors.addHeaders(Results.json().render(e));
        }
    }

    public Result deleteStudent(@PathParam("rollNo") int rollNo) throws Exception{
        try{
            System.out.println(rollNo);
            Student std = studentDao.deleteStudent(rollNo);
            return cors.addHeaders(Results.json().render("deletion successful"));
        }
        catch (Exception e){
            System.out.println("Student not exist!!!!");
            return cors.addHeaders(Results.json().render(e));
        }
    }

    public Result markAttendance(Student student) throws Exception{
        try{
            int rollNo = student.getRollNo();
            System.out.println(rollNo);
            Student std = studentDao.markAttendance(rollNo);
            return cors.addHeaders(Results.json().render(std));
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public Result getAllStudents() throws Exception{
        try {
            List<Student> listOfStudents= studentDao.getAllStudent();
            System.out.println(listOfStudents);
            return cors.addHeaders(Results.json().render(listOfStudents));
        }
        catch (Exception e){
            return cors.addHeaders(Results.json().render(e));
        }
    }

    public Result updateStudentDetails(Student student) throws Exception{
        try{
            System.out.println("Updating Student- " + student.getRollNo());
            Student std = studentDao.updateStudent(student);
            return cors.addHeaders(Results.json().render(std));
        }
        catch (Exception e){
            return cors.addHeaders(Results.json().render(e));
        }
    }

    public Result getAllAttendances(@PathParam("rollNo") int rollNo) throws Exception{
        try{
            System.out.println(rollNo);
            List<Attendance> listOfAttendances = attendanceDao.getAllAttendance(rollNo);
            return cors.addHeaders(Results.json().render(listOfAttendances));
        }
        catch (Exception e){
            return cors.addHeaders(Results.json().render(e));
        }
    }

    public Result getStudent(Session session) throws Exception{
        try{
            String email = session.get("email");
            System.out.println(email);
            Student student = studentDao.getStudent(email);
            return cors.addHeaders(Results.json().render(student));
        }
        catch (Exception e){
            return cors.addHeaders(Results.json().render(e));
        }
    }

    public Result getStudentByRoll(@PathParam("rollNo") int rollNo) throws Exception{
        try {
            System.out.println(rollNo);
            Student std = studentDao.getStudentByRollNo(rollNo);
            System.out.println(std);
            return cors.addHeaders(Results.json().render(std));
        }
        catch (Exception e){
            return cors.addHeaders(Results.json().render(e));
        }
    }

    public Result updateAttendance(AttendanceListDto attendanceListDto) throws Exception{
        System.out.println("Hello working!!");
        try{
            System.out.println("\n\n\n\n\nList of Attendance : "+ attendanceListDto.getPresentDates());
            int rollNo= attendanceListDto.getRollNo();
            List<Attendance> newList = attendanceDao.updateAttendance(rollNo, attendanceListDto.getPresentDates() );
            System.out.println("Updated Attendance = "+ newList);
            return cors.addHeaders(Results.json().render("Attandance updated!!"));
        }
        catch (Exception e){
            return cors.addHeaders(Results.json().render(e));
        }
    }


}
