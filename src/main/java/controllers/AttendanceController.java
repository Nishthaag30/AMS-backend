package controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import models.Attendance;
import ninja.Result;
import ninja.Results;

import javax.persistence.EntityManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class AttendanceController {

    public void func(String str){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDate DOB = LocalDate.parse(str);
        Date date = new Date();
        String todate = dateFormat.format(date);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Date todate1 = cal.getTime();
        String fromdate = dateFormat.format(todate1);
    }
    @Inject
    Provider<EntityManager> entityManagerProvider;
    @Transactional
    public Result addAttendance(Attendance attendance) throws  Exception{
        try{
            System.out.println(attendance);
            EntityManager em = entityManagerProvider.get();
            em.persist(attendance);
            return Results.json().render(attendance);
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
