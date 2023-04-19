package dao;


import java.util.List;

public class AttendanceListDto {
    private int rollNo;
    private String datePresent;

    List<String> presentDates;

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

    public List<String> getPresentDates() {
        return presentDates;
    }

    public void setPresentDates(List<String> presentDates) {
        this.presentDates = presentDates;
    }
}
