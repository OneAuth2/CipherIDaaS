package cipher.console.oidc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 95744 on 2018/9/26.
 */
public class TimeUtils {

    /**
     * Date 类型转化为 String 类型
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss 格式的时间
     */
    public static String dateToString(Date date) {
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(date);
    }

    /**
     * Date 类型转化为 String 类型
     *
     * @param date
     * @return yyyy-MM-dd 格式的时间
     */
    public String dateToString1(Date date) {
        String strDateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(date);
    }

    /**
     * String 类型转化为 Date 类型
     *
     * @param strTime    String 类型时间
     * @param formatType 时间格式
     * @return Date 类型的时间
     * @throws ParseException
     */
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    /**
     * String 类型数据转化为 Date 类型数据
     *
     * @param strTime String 类型时间
     * @return Date 类型时间，转换后忽略时分秒
     * @throws ParseException
     */
    public static Date stringToDate(String strTime)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    /**
     * String 类型转化为 Date 类型
     *
     * @param strTime String 类型时间
     * @return Date 类型时间，转化后保存时分秒
     * @throws ParseException
     */
    public static Date stringToDate1(String strTime)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    /**
     * 获取时分秒
     *
     * @param date
     * @return HH:mm:ss 格式的时分秒
     */
    public static String getTimeShort(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * Date 类型转化为 Long 类型
     *
     * @param date
     * @return
     */
    public static Long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * Long 类型转化为 String 类型
     *
     * @param time
     * @return
     */
    public static Date longToDate(Long time) {
        return new Date(time);
    }


    /**
     * @Description: String类型毫秒数转换成日期
     * [@param](http://my.oschina.net/param) lo 毫秒数
     * @return String yyyy-MM-dd HH:mm:ss
     */
    public static String stringToDatenew(String lo){
        long time = Long.parseLong(lo);
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.format(date);
    }

    public static void main(String[] args) throws ParseException {
        TimeUtils util = new TimeUtils();
        Date date = new Date();
        //System.out.println(util.getTimeShort(date));
     /*   Long timeLong = date.getTime();
        System.out.println(timeLong);
        String t = "2017-01-12 12:11:01";
        String f = "yyyy-MM-dd";*/
        //System.out.println(stringToDate(t,f)); 1507772534344
        //System.out.println(stringToDate1(t));
        Long l = 1507772738542L;
        String ll="1507772738542";
        util.stringToDatenew(ll);
        System.out.println(util.stringToDatenew(ll));
    }
}
