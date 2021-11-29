package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SuppressLint({"SimpleDateFormat"})
public class TimeUtils {
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_YMD_FORMAT = "yyyy-MM-dd";
    public static final String DATE_YMD_FORMAT2 = "yyyy-MM-dd";
    public static final String DATE_YMD_FORMAT3 = "MM-dd";
    public static final String DATE_YMD_FORMAT4 = "HH:mm";
    static PendingIntent sender;
    static PendingIntent sender2;
    static AlarmManager manager;
    static AlarmManager manager2;
    public static final String DATE_MD_FORMAT = "MM-dd";

    public TimeUtils() {
    }

    public static String ConverToString(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public static Date ConverToDate(String strDate) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.parse(strDate);
    }

    public static String ConverToString3(Date date) {
        DateFormat df = new SimpleDateFormat("MM-dd");
        return df.format(date);
    }

    public static Date ConverToDate3(String strDate) throws Exception {
        DateFormat df = new SimpleDateFormat("MM-dd");
        return df.parse(strDate);
    }

    public static String getAddDateTime(String time) {
        return ConverToString2(getDateTime(time));
    }

    public static String getAddDateTime3(String time) {
        return ConverToString3(getDateTime3(time));
    }

    public static String ConverToString2(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public static Date ConverToDate2(String strDate) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(strDate);
    }

    public static Date getDateTime(String time) {
        Date date = null;

        try {
            date = ConverToDate2(time);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, 1);
        Date date1 = new Date(calendar.getTimeInMillis());
        return date1;
    }

    public static Date getDateTime3(String time) {
        Date date = null;

        try {
            date = ConverToDate3(time);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, 1);
        Date date1 = new Date(calendar.getTimeInMillis());
        return date1;
    }

    public static String timeToString(String strDate) {
        String time = null;

        try {
            time = ConverToString(ConverToDate(strDate));
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return time;
    }

    public static String timeToString3(String strDate) {
        String time = null;

        try {
            time = ConverToString3(ConverToDate3(strDate));
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return time;
    }

    public static long getTimeChuo() {
        return System.currentTimeMillis();
    }

    public static int getLocalUTC() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        int zoneOffset = cal.get(15) / 1000;
        Calendar calendar = Calendar.getInstance();
        int localTime = (int)(calendar.getTimeInMillis() / 1000L + (long)zoneOffset);
        return localTime;
    }

    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static String getTimeYue() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        return sdf.format(new Date());
    }

    public static String getTimeDays() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return sdf.format(new Date());
    }

    public static String getTime(String timeType) {
        SimpleDateFormat sdf = new SimpleDateFormat(timeType);
        return sdf.format(new Date());
    }

    public static void clockClose(Context mContext) {
        try {
            manager.cancel(sender);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public static void clockClose2(Context mContext) {
        try {
            manager2.cancel(sender2);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public static boolean getIsTime(int startTime, int endTime) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(11);
        return hour >= startTime && hour <= endTime;
    }

    public static long[] parseDateWeeks(String value) {
        long[] weeks = null;

        try {
            String[] items = value.split(",");
            weeks = new long[items.length];
            int i = 0;
            String[] var7 = items;
            int var6 = items.length;

            for(int var5 = 0; var5 < var6; ++var5) {
                String s = var7[var5];
                weeks[i++] = Long.valueOf(s);
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return weeks;
    }

    public static long[][] parseDateMonthsAndDays(String value) {
        long[][] values = new long[2][];

        try {
            String[] items = value.split("\\|");
            String[] monthStrs = items[0].split(",");
            String[] dayStrs = items[1].split(",");
            values[0] = new long[monthStrs.length];
            values[1] = new long[dayStrs.length];
            int i = 0;
            String[] var9 = monthStrs;
            int var8 = monthStrs.length;

            String s;
            int var7;
            for(var7 = 0; var7 < var8; ++var7) {
                s = var9[var7];
                values[0][i++] = Long.valueOf(s);
            }

            i = 0;
            var9 = dayStrs;
            var8 = dayStrs.length;

            for(var7 = 0; var7 < var8; ++var7) {
                s = var9[var7];
                values[1][i++] = Long.valueOf(s);
            }
        } catch (Exception var10) {
            var10.printStackTrace();
        }

        return values;
    }

    public static String getZhouTime(int type, String time) {
        Calendar curr = Calendar.getInstance();
        if (type == 0) {
            curr.set(5, curr.get(2) + 7);
        } else {
            curr.set(5, curr.get(2) - 7);
        }

        Date date = curr.getTime();
        return ConverToString2(date);
    }

    public static String getMonthTime(int type, String time) {
        Calendar curr = Calendar.getInstance();
        if (type == 0) {
            curr.set(2, curr.get(2) + 1);
        } else {
            curr.set(2, curr.get(2) - 1);
        }

        Date date = curr.getTime();
        return ConverToString2(date);
    }

    public static String getYearTime(int type, String time) {
        Calendar curr = Calendar.getInstance();
        if (type == 0) {
            curr.set(1, curr.get(1) + 1);
        } else {
            curr.set(1, curr.get(1) - 1);
        }

        Date date = curr.getTime();
        return ConverToString2(date);
    }

    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getNowTime(String format) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static long getDays(String date1, String date2) {
        if (date1 != null && !date1.equals("")) {
            if (date2 != null && !date2.equals("")) {
                SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                Date mydate = null;

                try {
                    date = myFormatter.parse(date1);
                    mydate = myFormatter.parse(date2);
                } catch (Exception var7) {
                }

                long day = (date.getTime() - mydate.getTime()) / 86400000L;
                return Math.abs(day);
            } else {
                return 0L;
            }
        } else {
            return 0L;
        }
    }

    public static int getAge(String agetime) {
        long day = getDays(agetime, getStringDate());
        long year = day / 365L;
        long month = (day - 365L * year) / 30L + 1L;
        long week = day / 7L + 1L;
        System.out.println(year + "岁" + month + "月" + week + "周");
        return (int)year;
    }

    public static int DateCompare(String source, String traget, String type) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat(type);
        Date sourcedate = format.parse(source);
        Date tragetdate = format.parse(traget);
        int ret = sourcedate.compareTo(tragetdate);
        return ret;
    }

    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(1, year);
        a.set(2, month - 1);
        a.set(5, 1);
        a.roll(5, -1);
        int maxDate = a.get(5);
        return maxDate;
    }

    public static int getYear() {
        Calendar c = Calendar.getInstance();
        return c.get(1);
    }

    public static int getMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(2) + 1;
    }

    public static int getDay() {
        Calendar c = Calendar.getInstance();
        return c.get(5);
    }

    public static int getWeek() {
        Calendar c = Calendar.getInstance();
        return c.get(7);
    }

    public static int getHour() {
        Calendar c = Calendar.getInstance();
        return c.get(11);
    }

    public static int getMinute() {
        Calendar c = Calendar.getInstance();
        return c.get(12);
    }

    public static int getSecond() {
        Calendar c = Calendar.getInstance();
        return c.get(13);
    }

    public static int getTimeMode(Context context) {
        boolean is24 = android.text.format.DateFormat.is24HourFormat(context);
        return is24 ? 0 : 1;
    }

    public static int getTimeZone() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        int zoneOffset = cal.get(15) / 1000;
        int zone = zoneOffset / 1800;
        if (zone < 0) {
            zone = Math.abs(zone) + 128;
        }

        return zone;
    }

    public static long dateToStamp(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        return ts;
    }

    public static String getPreviousDate(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException var8) {
            var8.printStackTrace();
        }

        long ts = date.getTime();
        long last = ts - 86400000L;
        Date lastDate = new Date(last);
        return simpleDateFormat.format(lastDate);
    }

    public static String getNextDate(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException var8) {
            var8.printStackTrace();
        }

        long ts = date.getTime();
        long last = ts + 86400000L;
        Date lastDate = new Date(last);
        return simpleDateFormat.format(lastDate);
    }

    public static String stampToDate(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        String res = simpleDateFormat.format(date);
        return res;
    }

    public static int getTimeZoneForSecond() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        int zoneOffset = cal.get(15) / 1000;
        return zoneOffset;
    }

    public static long subValueForTwoDate(String last, String old) throws ParseException {
        long lastTime = dateToStamp(last);
        long oldTime = dateToStamp(old);
        return (lastTime - oldTime) / 1000L;
    }

    public static String[] getDateOfWeek(long weekMil) {
        String formatWeek = getFormatWeek(weekMil);
        String[] split = formatWeek.replace(" ", "").split("-");
        int year = Integer.valueOf(split[0]);
        int week = Integer.valueOf(split[1]);
        String[] weeks = new String[7];
        String zhoutime1 = getZhou4(year, week);

        for(int i = 0; i < 7; ++i) {
            zhoutime1 = getAddDateTime3(zhoutime1);
            weeks[i] = zhoutime1;
        }

        return weeks;
    }

    private static String getZhou4(int yyyy, int num) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(1, yyyy);
        cal.set(3, num);
        cal.set(7, 1);
        return getSpecifiedDayBefore(ConverToString3(cal.getTime()));
    }

    public static String getSpecifiedDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;

        try {
            date = (new SimpleDateFormat("MM-dd")).parse(specifiedDay);
        } catch (ParseException var5) {
            var5.printStackTrace();
        }

        c.setTime(date);
        int day = c.get(5);
        c.set(5, day - 1);
        String dayBefore = (new SimpleDateFormat("MM-dd")).format(c.getTime());
        return dayBefore;
    }

    public static String getCurrentMonth() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        return df.format(date);
    }

    public static String getPreviusMonth(String mCurrentMonth) {
        String[] split = mCurrentMonth.replace(" ", "").split("-");
        int year = Integer.valueOf(split[0]);
        int month = Integer.valueOf(split[1]) - 1;
        if (month <= 0) {
            --year;
            month = 12;
        }

        return year + "-" + getFormatNumber(month);
    }

    private static String getFormatNumber(int number) {
        return number < 10 ? "0" + number : String.valueOf(number);
    }

    public static long getPreviousWeekTime(long mCurrentWeekMil) {
        long newTime = mCurrentWeekMil - 604800000L;
        return newTime;
    }

    public static String getNextMonth(String mCurrentMonth) {
        String[] split = mCurrentMonth.replace(" ", "").split("-");
        int year = Integer.valueOf(split[0]);
        int month = Integer.valueOf(split[1]) + 1;
        if (month > 12) {
            ++year;
            month = 1;
        }

        return year + "-" + getFormatNumber(month);
    }

    public static String getFormatWeek(long mCurrentWeekMil) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-w");
        Date date = new Date(mCurrentWeekMil);
        return df.format(date);
    }

    public static String getCurrentWeek() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-w");
        Date date = new Date();
        return df.format(date);
    }

    public static long getNextWeekTime(long mCurrentWeekMil) {
        long newTime = mCurrentWeekMil + 604800000L;
        return newTime;
    }

    public static String getDateString(long mCurrentWeekMil, String format) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(mCurrentWeekMil);
        return df.format(date);
    }

    public static long getDurationTwoTime(String newTime, String oldTime, String format) {
        long duration = 0L;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        try {
            Date newDate = dateFormat.parse(newTime);
            Date oldDate = dateFormat.parse(oldTime);
            duration = newDate.getTime() - oldDate.getTime();
        } catch (ParseException var8) {
            var8.printStackTrace();
        }

        return duration;
    }

    public static String getFormatTime(long millionTime, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date date = new Date(millionTime);
        return df.format(date);
    }

    public static int getZoneOffset() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        int zoneOffset = cal.get(15) / 1000;
        return zoneOffset;
    }
}
