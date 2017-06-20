package com.goyo.grocery_goyo.TimeValidate;
import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
/**
 * Created by Admin on 6/17/2017.
 */
public class TimeValidate
{
    Context cxt;
    Date currentLocalTime;
    DateFormat date;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public TimeValidate(Context cxt)
    {
        this.cxt=cxt;
        currentTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
    }
    private Calendar fromTime;
    private Calendar toTime;
    private Calendar currentTime;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean checkTime(String time) {
        try {
            String[] times = time.split("-");
            String[] from = times[0].split(":");
            String[] until = times[1].split(":");

            fromTime = Calendar.getInstance();
            fromTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(from[0]));
            fromTime.set(Calendar.MINUTE, Integer.valueOf(from[1]));
           // Toast.makeText(cxt,String.valueOf(fromTime.get(Calendar.HOUR_OF_DAY)+":"+fromTime.get(Calendar.MINUTE)),Toast.LENGTH_LONG).show();


            toTime = Calendar.getInstance();
            toTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(until[0]));
            toTime.set(Calendar.MINUTE, Integer.valueOf(until[1]));
            currentLocalTime = currentTime.getTime();
            date = new SimpleDateFormat("hh:mm");
            date.setTimeZone(android.icu.util.TimeZone.getTimeZone("GMT+5:30"));
            String localTime = date.format(currentLocalTime);
            //Toast.makeText(cxt,localTime,Toast.LENGTH_LONG).show();
            currentTime.setTime(date.parse(localTime));
            //Toast.makeText(cxt,String.valueOf(currentTime.getTime().toString()),Toast.LENGTH_LONG).show();
          /*  Toast.makeText(cxt,String.valueOf(fromTime.getTime().toString()),Toast.LENGTH_LONG).show();
            Toast.makeText(cxt,String.valueOf(toTime.getTime().toString()),Toast.LENGTH_LONG).show();
          */  //this approach had been tried by me but the between timings is not happening
           /* if(currentTime.after(fromTime) && currentTime.before(toTime))
            {
                return true;
            }*/

           //I have tried another approach according to my logic its going perfectly
            int a=Integer.valueOf(currentTime.get(Calendar.HOUR));
            int b=Integer.valueOf(fromTime.get(Calendar.HOUR));
            int c=Integer.valueOf(toTime.get(Calendar.HOUR));
            if(a==b)
            {
                if(Integer.valueOf(currentTime.get(Calendar.MINUTE))>=Integer.valueOf(fromTime.get(Calendar.MINUTE)))
                {
                   return true;
                }
            }
            else if(a==c)
            {
                if(Integer.valueOf(currentTime.get(Calendar.MINUTE))<=Integer.valueOf(toTime.get(Calendar.MINUTE)))
                {
                    return true;
                }
            }
            else {
                if (a >= b && a <= c) {
                    return true;
                }
            }
        }
        catch (Exception e)
        {
            return false;
        }
        return false;
    }
    public String GetAmPm()
    {
        int Am_Pm=currentTime.get(Calendar.AM_PM);
        String am_pm=null;
        if(Am_Pm==1)
        {
            am_pm="PM";
        }
        if(Am_Pm==0)
        {
            am_pm="AM";
        }
        return  am_pm;
    }
}
