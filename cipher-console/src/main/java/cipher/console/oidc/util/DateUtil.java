package cipher.console.oidc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date getSystemDate(){
        Date date = new Date();
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        String nowTime = sdf.format(date);
        try {
            Date time = sdf.parse( nowTime );
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSystemDateString(){
        Date date = getSystemDate();
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        return sdf.format(date);
    }

    public static Date getMoreYearsDate(int year){
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR,year);
        Date date = c.getTime();
        String timeString = sdf.format(date);
        try {
            Date time = sdf.parse( timeString );
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getCurrentFormatTimeString(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


}
