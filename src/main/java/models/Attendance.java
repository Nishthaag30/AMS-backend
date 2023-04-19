package models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Attendance")

public class Attendance {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private int rollNo;
    private String datePresent;

    public Attendance() {
    }

    public Attendance(int rollNo) {
        this.rollNo = rollNo;
    }

    public int getId() {
        return id;
    }

    public int getRollNo() {
        return rollNo;
    }
    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getDatePresent() {
        return datePresent;
    }

    public void setDatePresent(String date) {
        this.datePresent = date;
    }
}
