package dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import models.Attendance;
import models.Student;
import org.checkerframework.checker.units.qual.A;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDao {
    @Inject
    Provider<EntityManager> entityManagerProvider;

    @Inject
    StudentDao studentDao;

    public List<Attendance> getAllAttendance(int rollNo) throws Exception{
        try{
            EntityManager em = entityManagerProvider.get();
            TypedQuery<Attendance> query = em.createQuery("select a from Attendance a where a.rollNo=:rollNo", Attendance.class);
            List<Attendance> list = query.setParameter("rollNo", rollNo).getResultList();
            return list;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    public void deleteAttendances(int rollNo){
        LocalDate datebefore  = LocalDate.now().minusDays(7);
        EntityManager em = entityManagerProvider.get();
        TypedQuery<Attendance> query = em.createQuery("SELECT a FROM Attendance a where a.rollNo = :rollNo", Attendance.class);
        List<Attendance> attendances = query.setParameter("rollNo", rollNo).getResultList();
        System.out.println(attendances);
        for(Attendance atd : attendances){
            LocalDate thisDate = LocalDate.parse(atd.getDatePresent());
            if(thisDate.isBefore(datebefore)) {
                em.remove(atd);
            }
        }
    }

    @Transactional
    public List<LocalDate> getSevenDaysDates(){
        LocalDate startDate  = LocalDate.now().minusDays(6);
        List<LocalDate> dateList = new ArrayList<>();
        for (LocalDate date = startDate; date.isBefore(LocalDate.now()); date = date.plusDays(1)){
            dateList.add(date);
        }
        dateList.add(LocalDate.now());
        return dateList;
    }

    @Transactional
    public List<Attendance> updateAttendance(int rollNo, List<String> present)throws Exception{
        try {
            EntityManager em = entityManagerProvider.get();
            List<Attendance> currentList = getAllAttendance(rollNo);
            Student std = studentDao.getStudentByRollNo(rollNo);
            System.out.println("Present DAtes"+ present);
            System.out.println("All Attendances : "+ currentList);
            for(String atd : present){
                int find=0;
                for(Attendance curr_atd : currentList){
                    if(atd.equals(curr_atd.getDatePresent())){
                        find = 1;
                        break;
                    }
                }
                if(find == 0){
                    Attendance attendance = new Attendance(rollNo);
                    attendance.setDatePresent(""+atd);
                    std.markAttendance();
                    em.persist(attendance);
                }
            }
            List<LocalDate> sevenDaysDates = getSevenDaysDates();
            List<LocalDate> absent = new ArrayList<>();
            for(LocalDate date : sevenDaysDates){
                int find = 0;
                for(String atd: present){
                    if(atd.equals(date+"")){
                        find = 1;
                        break;
                    }
                }
                if(find==0){
                    absent.add(date);
                }
            }
            System.out.println("Seven Days DAtes"+ sevenDaysDates);
            System.out.println("absents : "+ absent);
            for(LocalDate date: absent){
                for(Attendance atd: currentList){
                    if(atd.getDatePresent().equals(date+"")){
                        em.remove(atd);
                        std.settAttendance(std.gettAttendance()-1);
                    }
                }
            }
            currentList = getAllAttendance(rollNo);
            return currentList;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
