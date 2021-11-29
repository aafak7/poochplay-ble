package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint({"SimpleDateFormat"})
public class Util {
    static Typeface typeface = null;
    static Resources rescources = null;
    static int isRingPhone = 0;
    static MediaPlayer mp;

    public Util() {
    }

    public static byte[] decodeHex(char[] data) {
        int len = data.length;
        if ((len & 1) != 0) {
            throw new RuntimeException("Odd number of characters.");
        } else {
            byte[] out = new byte[len >> 1];
            int i = 0;

            for(int j = 0; j < len; ++i) {
                int f = toDigit(data[j], j) << 4;
                ++j;
                f |= toDigit(data[j], j);
                ++j;
                out[i] = (byte)(f & 255);
            }

            return out;
        }
    }

    private static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        } else {
            return digit;
        }
    }

    public static void setFontStyle(TextView view, Context context) {
        if (typeface != null) {
            view.setTypeface(typeface);
        } else {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/chinaesefont.ttf");
            view.setTypeface(typeface);
        }

    }

    public static void setFontStyle(Button view, Context context) {
        if (typeface != null) {
            view.setTypeface(typeface);
        } else {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/chinaesefont.ttf");
            view.setTypeface(typeface);
        }

    }

    public static void setFontStyle(EditText view, Context context) {
        if (typeface != null) {
            view.setTypeface(typeface);
        } else {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/chinaesefont.ttf");
            view.setTypeface(typeface);
        }

    }

    public static boolean isEmail(String email) {
        String str = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isMobile(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[^4,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();
    }

    public static String inttostr(int num) {
        String add = "";
        if (num < 10) {
            add = "0" + num;
        } else {
            add = String.valueOf(num);
        }

        return add;
    }

    public static Message bundMessage(String msg) {
        Bundle bundle = new Bundle();
        bundle.putString("message", msg);
        Message message = new Message();
        message.setData(bundle);
        return message;
    }

    public static String getHttpParams(HashMap<String, Object> params) {
        String url = null;
        StringBuilder builder = null;
        if (params != null) {
            builder = new StringBuilder();
            builder.append("?");

            Object key;
            Object value;
            for(Iterator mapite = params.entrySet().iterator(); mapite.hasNext(); builder.append(key + "=" + value + "&")) {
                Entry testDemo = (Entry)mapite.next();
                key = testDemo.getKey();
                value = testDemo.getValue();
                if (value != null && !TextUtils.isEmpty(value.toString())) {
                    value = chinaToUnicode(value.toString());
                }
            }

            int end = builder.length() - 1;
            if (builder != null) {
                url = builder.substring(0, end);
            }
        }

        return url;
    }

    public static String chinaToUnicode(String date) {
        try {
            if (date != null && isNull(date)) {
                return URLEncoder.encode(date.toString(), "UTF-8");
            }
        } catch (UnsupportedEncodingException var2) {
        }

        return null;
    }

    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static int getAge(String time) {
        Time t = new Time();
        t.setToNow();
        int year = t.year;
        int month = t.month;
        int age = month < Integer.parseInt(time.substring(time.indexOf("-") + 2, time.length())) ? year - Integer.parseInt(time.substring(0, time.indexOf("-") - 1)) : year - Integer.parseInt(time.substring(0, time.indexOf("-") - 1)) + 1;
        return age;
    }



    public static String getYear() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(1));
    }

    public static String getMonth() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(2) + 1);
    }

    public static String getDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(5) < 10 ? "0" + calendar.get(5) : "" + calendar.get(5);
    }

    public static String getWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(3) < 10 ? "0" + calendar.get(3) : "" + calendar.get(3);
    }

    public static int calDayByYearAndMonth(String dyear, String dmouth) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM");
        Calendar rightNow = Calendar.getInstance();

        try {
            rightNow.setTime(simpleDate.parse(dyear + "/" + dmouth));
        } catch (ParseException var5) {
            var5.printStackTrace();
        }

        return rightNow.getActualMaximum(5);
    }

    public static boolean isNull(Object str) {
        boolean result = true;
        if (str == null || "".equals(str.toString().trim()) || str.toString().trim().equals("null")) {
            result = false;
        }

        return result;
    }

    public static byte[] hexStringToByte(String hex) {
        int len = hex.length() / 2;
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();

        for(int i = 0; i < len; ++i) {
            int pos = i * 2;
            result[i] = (byte)(toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }

        return result;
    }

    public static byte toByte(char c) {
        byte b = (byte)"0123456789ABCDEF".indexOf(c);
        return b;
    }




    public static String getliangwei(float ss) {
        BigDecimal db = new BigDecimal((double)ss);
        String ii = db.toPlainString();
        return ii.substring(0, 4);
    }

    public static String getliuwei(double haiba) {
        BigDecimal b = new BigDecimal(haiba);
        float f1 = b.setScale(6, 4).floatValue();
        BigDecimal db = new BigDecimal((double)f1);
        return db.toPlainString();
    }

    public static double getliangweixiaoshu(Double ss) {
        BigDecimal db = new BigDecimal(ss);
        double f1 = db.setScale(2, 4).doubleValue();
        return f1;
    }

    public static double floattodouble(float ss) {
        double d = Double.parseDouble(String.valueOf(ss));
        BigDecimal db = new BigDecimal(d);
        double f1 = db.setScale(2, 4).doubleValue();
        return f1;
    }

    public static String getnowtime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        return time;
    }

    public static String gettodaytime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        return time;
    }





    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = String.valueOf(i);
        }

        return retStr;
    }

    public static String stradd0(String str) {
        String retStr = null;
        int i = Integer.valueOf(str);
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = String.valueOf(i);
        }

        return retStr;
    }

    public static int get_checksum(int init, byte[] arr, int start_pos, int len) {
        int tmpSum = init;

        for(int i = 0; i < len; ++i) {
            int tmp = arr[i + start_pos];
            if (tmp < 0) {
                tmp += 256;
            }

            tmpSum += tmp;
        }

        return tmpSum & 255;
    }

    public static int get_checksum(int init, int[] arr, int start_pos, int len) {
        int tmpSum = init;

        for(int i = 0; i < len; ++i) {
            tmpSum += arr[i + start_pos];
        }

        return tmpSum & 255;
    }

    public static int CIRCULAR_INC_RET(int a, int b, int max) {
        int ret = (a + b) % max;
        return ret & 255;
    }

    public static int getWeek(String pTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException var4) {
            var4.printStackTrace();
        }

        int Week = c.get(7);
        return Week;
    }

    public static void RingPhone(int ispaly, Context context) {
        if ((isRingPhone ^ ispaly) != 0) {
            isRingPhone = ispaly;
            if (ispaly == 1) {
                mp = new MediaPlayer();
            }

            try {
                if (ispaly == 1) {
                    mp.setDataSource(context, RingtoneManager.getDefaultUri(1));
                    mp.prepare();
                    Log.e("moofit", "RingPhone: 开" + ispaly);
                    mp.start();
                } else if (ispaly == 0) {
                    Log.e("moofit", "RingPhone: 关" + ispaly);
                    mp.stop();
                    mp = null;
                }
            } catch (Exception var3) {
                var3.printStackTrace();
                Log.e("moofit", "RingPhone: Exception");
            }

        }
    }
}
