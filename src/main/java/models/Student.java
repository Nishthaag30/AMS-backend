package models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(schema = "public")
@NamedQueries({
        @NamedQuery(name = "Student.getStudentByRollNo", query = "select s from Student s where s.rollNo =:rollNo"),
        @NamedQuery(name = "Student.getAllStudents", query = "SELECT s FROM Student s order by s.rollNo asc "),
})
public class Student {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int rollNo;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String DOB;
    private String email;
    private String address;
    private int tAttendance;
    private long mobile;

    public Student() {
    }
    public Student(String name, String DOB, String email, long mobile, String address) {
        this.name = name;
        this.DOB = DOB;
        this.email = email;
        this.address = address;
        this.tAttendance = 0;
        this.mobile=mobile;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDOB() {
        return DOB+"";
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public int gettAttendance() {
        return tAttendance;
    }

    public void settAttendance(int tAttendance) {
        this.tAttendance = tAttendance;
    }
    public void updateAttendance(int newAttendance){
        this.tAttendance = newAttendance;
    }
    public void markAttendance(){
        tAttendance++;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }
}
