package dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import models.Attendance;
import models.Employee;
import models.Student;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Date;


public class StudentDao {
    @Inject
    Provider<EntityManager> entityManagerProvider;

    @Inject
    AttendanceDao attendanceDao;

    @Transactional
    public Student addStudent(String name, String DOB, String email, long mobile, String address ) throws  Exception{
        try{
            EntityManager em = entityManagerProvider.get();
            Student std = new Student(name, DOB, email, mobile, address);
            em.persist(std);
            return std;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    @Transactional
    public Student deleteStudent(int rollno) throws Exception{
        try{
            System.out.println(rollno);
            EntityManager em = entityManagerProvider.get();
            Query namedQuery = em.createNamedQuery("Student.getStudentByRollNo").setParameter("rollNo", rollno);
            List<Student> std = namedQuery.getResultList();
            if(std.size()!=0){
//                deleting all attendances of student we want to delete
                TypedQuery<Attendance> query = em.createQuery("SELECT a FROM Attendance a where a.rollNo = :rollno", Attendance.class);
                List<Attendance> list = query.setParameter("rollno", rollno).getResultList();
                for(Attendance atd: list){
                    em.remove(atd);
                }

//                deleting the student from student
                em.remove(std.get(0));

//                deleting the student from Employee as well
                String email = std.get(0).getEmail();
                Query qq = em.createNamedQuery("Employee.getUserByEmail").setParameter("email", email);
                Employee emp = (Employee) qq.getSingleResult();
                em.remove(emp);

                return std.get(0);
            }
            else {
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }


    public List<Student> getAllStudent() throws Exception{
        try{
            EntityManager em = entityManagerProvider.get();
            Query query = em.createNamedQuery("Student.getAllStudents");
            List<Student> student = query.getResultList();
            return student;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    public Student updateStudent(Student student) throws Exception{
        try {
            EntityManager em = entityManagerProvider.get();
            Query query = em.createNamedQuery("Student.getStudentByRollNo").setParameter("rollNo", student.getRollNo());
            List<Student> listStudent = query.getResultList();
            if(listStudent.size()!=0){
                Student std = listStudent.get(0);
                std.setName(student.getName());
                std.setDOB(student.getDOB());
                std.setMobile(student.getMobile());
                std.setAddress(student.getAddress());
                em.persist(std);
                return std;
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    public Student markAttendance(int rollNo) throws Exception{
        try{
            System.out.println(rollNo);
            EntityManager em = entityManagerProvider.get();
            Query namedQuery = em.createNamedQuery("Student.getStudentByRollNo").setParameter("rollNo", rollNo);
            Student std = (Student) namedQuery.getSingleResult();
            System.out.println(std.getName());
//            check if student has marked himself present or not
            boolean isPresent = isPresent(rollNo);
//            if not marked present then mark him present
            if(!isPresent) {
                System.out.println("Student not Present");
//                increasing the total attendance
                std.markAttendance();
//                marking him present by adding row in attendance with current date
                Attendance attendance = new Attendance(rollNo);
                attendance.setDatePresent("" + LocalDate.now());
                attendanceDao.deleteAttendances(rollNo);
                em.persist(attendance);
                em.persist(std);
            }
//            student has marked himself present then throw error
            else{
                System.out.println("Student already marked Present");
            }
            return std;

        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public boolean isPresent(int rollNo){
        String todayDate = LocalDate.now()+"";
        EntityManager em = entityManagerProvider.get();
        TypedQuery<Attendance> query = em.createQuery("SELECT a FROM Attendance a where a.rollNo = :rollNo", Attendance.class);
        List<Attendance> attendances = query.setParameter("rollNo", rollNo).getResultList();
        System.out.println(attendances);
        for(Attendance atd : attendances){
            if(atd.getDatePresent().equals(todayDate))
                return true;
        }
        return false;
    }

    public Student getStudent(String email) throws Exception{
        try{
            EntityManager em = entityManagerProvider.get();
            TypedQuery<Student> query = em.createQuery("select x from Student x where x.email=:email", Student.class);
            Student std = query.setParameter("email", email).getSingleResult();
            return std;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public Student getStudentByRollNo(int rollNo) throws Exception{
        try {
            EntityManager em = entityManagerProvider.get();
            Query query = em.createNamedQuery("Student.getStudentByRollNo").setParameter("rollNo", rollNo);
            Student std = (Student) query.getSingleResult();
            return std;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }


//new Date(System.currentTimeMillis())
}
