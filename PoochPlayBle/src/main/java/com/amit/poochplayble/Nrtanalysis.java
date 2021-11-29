package com.amit.poochplayble;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.amit.poochplayble.Array.RtCtrlData;
import com.amit.poochplayble.Array.UserBodyInfo;
import com.amit.poochplayble.Array.UserSleepInfo;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Nrtanalysis {
    private static JSONArray jsonArray1;
    private static JSONArray jsonArray2;
    private static JSONArray jsonArray;
    public static SleepData sleepData;
    public static StepData stepData;
    static int isexitsleep = 0;
    static int walkCounts = 0;
    static int calorie = 0;
    static int maxlength;
    static int index;
    static int goalWalk = 0;
    static int tota = 0;
    static int shengshui = 0;
    static int qianshui = 0;
    static int qingxing = 0;
    static int sleepmax;
    static int sleepindex;
    public static String todaytime;
    static String data_time1;
    static HashMap<String, StepData> map;
    private static StringBuffer mTempHeartRateData = new StringBuffer();
    private static List<String> mHeartRateDataCollection = new ArrayList();

    public Nrtanalysis() {
    }

    public static void setdata(String value, BleService mBluetoothLeService, Handler handler) {
        String tt = value.toString().replace(" ", "").toUpperCase();
        byte[] data = Util.hexStringToByte(tt);
        int one = Utils.getLowBit4(data[0]);
        if (one == 8) {
            collectHistoryData(data, value.substring(6, value.length() - 2), mBluetoothLeService);
        }

        int bb;
        byte shuju;
        int gg;
        int j;
        int ii;
        int checksum;
        int first;

        if (data[0] == 1) {
            byte[] newdata = new byte[12];
            bb = 0;

            newdata[0] = -31;
            newdata[1] = 0;
            gg = UserBodyInfo.weight;
            newdata[2] = (byte)(gg >> 8);
            newdata[3] = (byte)gg;
            j = UserBodyInfo.age;
            newdata[4] = (byte)j;
            ii = UserBodyInfo.height;
            newdata[5] = (byte)ii;
            newdata[6] = (byte)(ii / 7);
            checksum = UserBodyInfo.sex;
            if (checksum == 1) {
                newdata[7] = 1;
            } else {
                newdata[7] = 0;
            }

            goalWalk = UserBodyInfo.target;
            newdata[8] = (byte)(goalWalk >> 16);
            newdata[9] = (byte)(goalWalk >> 8);
            newdata[10] = (byte)goalWalk;

            for(first = 0; first <= newdata.length - 2; ++first) {
                shuju = newdata[first];
                if (shuju < 0) {
                    shuju += 256;
                }

                bb += shuju;
            }

            newdata[11] = (byte)(bb & 255);
            boolean var40 = mBluetoothLeService.write(newdata);
        }

        int wendrtmin;
        if ((data[0] & 15) == 2) {
            checksum = 0;

            Calendar calendar = Calendar.getInstance();
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            long localTime = calendar.getTimeInMillis() / 1000L + (long)(cal.get(15) / 1000);
            long time4 = localTime / 16777216L;
            long time3 = localTime % 16777216L / 65536L;
            long time2 = localTime % 65536L / 256L;
            long time1 = localTime % 256L;
            byte[] newdata = new byte[]{-30, 1, (byte)((int)time4), (byte)((int)time3), (byte)((int)time2), (byte)((int)time1), 0};

            for(wendrtmin = 0; wendrtmin <= newdata.length - 2; ++wendrtmin) {
                bb = newdata[wendrtmin];
                if (bb < 0) {
                    bb += 256;
                }

                checksum += bb;
            }

            newdata[6] = (byte)(checksum & 255);
            boolean var48 = mBluetoothLeService.write(newdata);
        }

        if ((data[0] & 15) == 3) {
            checksum = data[5] & 255;
            bb = data[6] & 255;
            shuju = (byte) (data[7] & 255);
            Array.totaldata = shuju + (bb << 8) + (checksum << 16);
            gg = data[11] & 255;
            j = data[12] & 255;
            ii = data[13] & 255;
            Array.totalcalorie = (ii + (j << 8) + (gg << 16)) / 10;
            String xx = "E0 02 00 E2";
            String ss = xx.toString().replace(" ", "");
            byte[] data1 = Util.hexStringToByte(ss);
            mBluetoothLeService.write(data1);
        }

        if ((data[0] & 15) == 4 && (data[1] & 15) == 1) {
            Array.liststring.add(value);
        }

        if ((data[0] & 15) == 4 && (data[1] & 15) == 2) {
            Array.liststring.add(value);
        }

        byte[] newdata1;
        boolean var34;
        if ((data[0] & 15) == 4 && (data[1] & 15) == 3) {
            checksum = 0;
            bb = (data[0] & 240) >> 4;

            newdata1 = new byte[]{-32, (byte)bb, 0, 0};

            for(j = 0; j <= newdata1.length - 2; ++j) {
                shuju = newdata1[j];
                if (shuju < 0) {
                    shuju += 256;
                }

                checksum += shuju;
            }

            newdata1[3] = (byte)(checksum & 255);
            var34 = mBluetoothLeService.write(newdata1);
        }

        if ((data[0] & 15) == 5 && (data[1] & 15) == 1) {
            Array.listsleepstring.add(value);
        }

        if ((data[0] & 15) == 5 && (data[1] & 15) == 3) {
            checksum = 0;
            bb = (data[0] & 240) >> 4;

            newdata1 = new byte[]{-32, (byte)bb, 0, 0};

            for(j = 0; j <= newdata1.length - 2; ++j) {
                shuju = newdata1[j];
                if (shuju < 0) {
                    shuju += 256;
                }

                checksum += shuju;
            }

            newdata1[3] = (byte)(checksum & 255);
            var34 = mBluetoothLeService.write(newdata1);
        }

        if ((data[0] & 15) == 7) {
            int aa = data[2];
            int ff = data[3];
            bb = data[4];
            int cc = data[5];
            int dd = data[6];
            int ee = data[7];
            Array.starthour = String.valueOf(aa);
            Array.startmin = String.valueOf(ff);
            Array.peaceendhour = String.valueOf(bb);
            Array.peaceendmin = String.valueOf(cc);
            Array.weekendhour = String.valueOf(dd);
            Array.weekendmin = String.valueOf(ee);
            checksum = 0;
            first = (data[0] & 240) >> 4;

            newdata1 = new byte[20];
            newdata1[0] = -28;
            newdata1[1] = (byte)first;
            newdata1[2] = 1;
            int starthour = UserSleepInfo.startDetectSleepHH;
            int startmin = UserSleepInfo.startDetectSleepMM;
            newdata1[3] = (byte)starthour;
            newdata1[4] = (byte)startmin;
            int pendhour = UserSleepInfo.wdStopDetectSleepHH;
            int pendrtmin = UserSleepInfo.wdStopDetectSleepMM;
            newdata1[5] = (byte)pendhour;
            newdata1[6] = (byte)pendrtmin;
            int wendhour = UserSleepInfo.rdStopDetectSleepHH;
            wendrtmin = UserSleepInfo.rdStopDetectSleepMM;
            newdata1[7] = (byte)wendhour;
            newdata1[8] = (byte)wendrtmin;
            newdata1[9] = 10;
            newdata1[10] = 40;
            newdata1[11] = 8;
            newdata1[12] = 48;
            newdata1[13] = 0;
            newdata1[14] = 0;
            newdata1[15] = 0;
            newdata1[16] = 0;
            newdata1[17] = 0;
            newdata1[18] = 0;

            for(j = 0; j <= newdata1.length - 2; ++j) {
                 shuju = newdata1[j];
                if (shuju < 0) {
                    shuju += 256;
                }

                checksum += shuju;
            }

            newdata1[19] = (byte)(checksum & 255);
            boolean var49 = mBluetoothLeService.write(newdata1);
        }

        if ((data[0] & 15) == 6) {
            if (Constant.RtNotify == 2) {
                Constant.RtNotify = 1;
                SysHanderManager.setHandler(handler);
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = 34;
                SysHanderManager.SendMsg(msg);
                if (BleState.bleOldProtocolSyncNrtStatus == 61) {
                    SysSendMsg.StartTimerMsg(1, 32, 2000, false);
                }
            }

            checksum = 0;
            bb = (data[0] & 240) >> 4;

            newdata1 = new byte[]{-32, (byte)bb, 0, 0};

            for(j = 0; j <= newdata1.length - 2; ++j) {
                shuju = newdata1[j];
                if (shuju < 0) {
                    shuju += 256;
                }

                checksum += shuju;
            }

            newdata1[3] = (byte)(checksum & 255);
            var34 = mBluetoothLeService.write(newdata1);
        }

        if (((data[0] & 255) == 209 || (data[1] & 255) == 12) && ((data[0] & 255) == 209 || (data[1] & 255) == 16) && ((data[0] & 255) == 209 || (data[1] & 255) == 13 || (data[2] & 255) == 1)) {
            checksum = data[2] & 255;
            bb = data[3] & 255;
            shuju = (byte) (data[4] & 255);
            RtCtrlData.totalSteps = shuju + (bb << 8) + (checksum << 16);
            gg = data[8] & 255;
            j = data[9] & 255;
            ii = data[10] & 255;
            RtCtrlData.totalCalorie = (float)((ii + (j << 8) + (gg << 16)) / 10);
            handler.sendEmptyMessage(1);
        }

    }

    public static void setbledate() {
        getstepdata();
        getsleepdata();
        Array.historyHeartRate = analysisHeartRateHistoryData();
        mHeartRateDataCollection = new ArrayList();
        String action = "com.wrist.ble.NRTDATAEND";
        Intent intent = new Intent(action);
        Constant.appcontext.sendBroadcast(intent);
    }

    public static void getstepdata() {
        String first;
        String test;
        for(int i = 0; i < Array.liststring.size(); ++i) {
            String first11 = (String)Array.liststring.get(i);
            first = first11.substring(0, first11.length() - 3);
            test = first.substring(3, 5);
            if ("01".equals(test)) {
                if (i < Array.liststring.size() - 1) {
                    String second111 = (String)Array.liststring.get(i + 1);
                    String second = second111.substring(0, second111.length() - 3);
                    String checktwo = second.substring(3, 5);
                    if ("02".equals(checktwo)) {
                        StringBuffer stb = new StringBuffer(second);
                        stb.delete(0, 6);
                        String string = stb.toString();
                        String total = first + string;
                        Array.listafterstring.add(total);
                    } else {
                        Array.listafterstring.add(first);
                    }
                } else if (i == Array.liststring.size() - 1) {
                    Array.listafterstring.add(first);
                }
            }
        }

        for(int i = 0; i < Array.listafterstring.size(); ++i) {
            first = (String)Array.listafterstring.get(i);
            test = first.toString().replace(" ", "").toUpperCase();
            byte[] step = Util.hexStringToByte(test);
            long time1 = (long)(step[2] & 255);
            long time2 = (long)(step[3] & 255);
            long time3 = (long)(step[4] & 255);
            long time4 = (long)(step[5] & 255);
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            long aa = time4 + (time3 << 8) + (time2 << 16) + (time1 << 24) - (long)(cal.get(15) / 1000);
            SimpleDateFormat foo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for(int j = 6; j < step.length; j += 2) {
                StepData stepData = new StepData();
                int stepsum = step[j] & 255;
                int caloriesum = step[j + 1] & 255;
                long xx = aa + (long)(60 * (j - (j / 2 + 3)));
                long ss = xx * 1000L;
                Date date = new Date(ss);
                String time = foo.format(date.getTime());
                String marktime = time.substring(0, 10);
                stepData.setSteptime(time);
                stepData.setStepdata(stepsum);
                stepData.setCaloriedata(caloriesum);
                stepData.setMarktime(marktime);
                stepData.setUtctime((int)xx);
                Array.liststep.add(stepData);
            }
        }

        HashMap<String, StepData> map = new HashMap();

        for(int i = 0; i < Array.liststep.size(); ++i) {
            test = ((StepData)Array.liststep.get(i)).getSteptime();
            if (map.containsKey(test)) {
                map.put(test, (StepData)Array.liststep.get(i));
            } else {
                map.put(test, (StepData)Array.liststep.get(i));
            }
        }

        Array.liststep.clear();
        Set<String> get = map.keySet();
        Iterator var33 = get.iterator();

        while(var33.hasNext()) {
            test = (String)var33.next();
            Array.liststep.add((StepData)map.get(test));
        }

        Collections.sort(Array.liststep, new SortByid());
    }

    @SuppressLint({"DefaultLocale"})
    public static void getsleepdata() {
        int i;
        String string;
        String change;
        for(i = 0; i < Array.listsleepstring.size(); ++i) {
            string = (String)Array.listsleepstring.get(i);
            change = string.substring(0, string.length() - 3);
            Array.listsleepafterstring.add(change);
        }

        for(i = 0; i < Array.listsleepafterstring.size(); ++i) {
            string = (String)Array.listsleepafterstring.get(i);
            change = string.toString().replace(" ", "").toUpperCase();
            byte[] sleep = Util.hexStringToByte(change);
            long time1 = (long)(sleep[2] & 255);
            long time2 = (long)(sleep[3] & 255);
            long time3 = (long)(sleep[4] & 255);
            long time4 = (long)(sleep[5] & 255);
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            long aa = time4 + (time3 << 8) + (time2 << 16) + (time1 << 24) - (long)(cal.get(15) / 1000);
            SimpleDateFormat foo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for(int j = 6; j < sleep.length; ++j) {
                SleepData sleepData = new SleepData();
                int sleepsum = sleep[j] & 255;
                long xx = aa + (long)(300 * (j - 6));
                long ss = xx * 1000L;
                Date date = new Date(ss);
                String time = foo.format(date.getTime());
                sleepData.setSleeptime(xx);
                sleepData.setSleepdata(sleepsum);
                int hour = Integer.valueOf(time.substring(11, 13));
                if (hour < 12) {
                    sleepData.setMarktime(time.substring(0, 10));
                } else {
                    ss += 43200000L;
                    Date date1 = new Date(ss);
                    String timehour = foo.format(date1.getTime());
                    sleepData.setMarktime(timehour.substring(0, 10));
                }

                Array.listsleep.add(sleepData);

                for(int k = 0; k < Array.listsleep.size(); ++k) {
                }
            }
        }

    }

    private static void collectHistoryData(byte[] result, String value, BleService mBluetoothLeService) {
        int two = HexUtil.byteArrayToInt(new byte[]{result[1]});
        if (two == 1) {
            mTempHeartRateData = new StringBuffer();
        }

        if (two == 5) {
            byte[] newdata1 = getReplyX0E0(result);
            mBluetoothLeService.write(newdata1);
            mHeartRateDataCollection.add(mTempHeartRateData.toString());
        } else {
            mTempHeartRateData.append(value);
        }
    }

    private static byte[] getReplyX0E0(byte[] result) {
        int first = (result[0] & 240) >> 4;
        byte[] newdata1 = new byte[]{-32, (byte)first, 0, 0};
        newdata1[3] = Utils.getSumFromByteArray(newdata1);
        return newdata1;
    }

    private static List<HeartRateDataInfo> analysisHeartRateHistoryData() {
        List<HeartRateDataInfo> list = new ArrayList();

        for(int i = 0; i < mHeartRateDataCollection.size(); ++i) {
            byte[] result = HexUtil.hexStringToBytes(((String)mHeartRateDataCollection.get(i)).replace(" ", ""));
            long utc = HexUtil.byteArrayToLong(new byte[]{result[0], result[1], result[2], result[3]});

            for(int j = 4; j < result.length; ++j) {
                long tt = utc + (long)(4 * (j - 4)) - (long)getZome();
                int heart = HexUtil.byteArrayToInt(new byte[]{result[j]});
                String time = TimeUtils.stampToDate(String.valueOf(tt * 1000L));
                list.add(new HeartRateDataInfo(time, (float)heart));
            }
        }

        return list;
    }

    private static int getZome() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        int zoneOffset = cal.get(15) / 1000;
        return zoneOffset;
    }

    static class SortByid implements Comparator {
        SortByid() {
        }

        public int compare(Object o1, Object o2) {
            StepData s1 = (StepData)o1;
            StepData s2 = (StepData)o2;
            long id1 = (long)s1.getUtctime();
            long id2 = (long)s2.getUtctime();
            return id1 > id2 ? 1 : -1;
        }
    }
}
