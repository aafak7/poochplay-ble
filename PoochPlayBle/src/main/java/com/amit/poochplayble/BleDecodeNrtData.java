package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.amit.poochplayble.Array.DeviceSurport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BleDecodeNrtData {
    private static Context context;
    private static final int PACKET_HEADER_LEN = 2;
    private static final int KEY_HEADER_LEN = 2;
    private static final int KEY_LEN = 1;
    private static final int CMDID_Pos = 0;
    private static final int VERSION_AND_UTILIZE_Pos = 1;
    private static final int VERSION_DEFAULT_MSK = 240;
    private static final int UTILIZE_PRIVATE_MSK = 8;
    private static final int KEY_VAL_LEN_Pos = 2;
    private static final int KEY_Pos = 0;
    private static final int CONFIRM_ACK_MSK = 1;
    private static final int ACK_MSK = 3;
    private static final int KEY_VAL_Pos = 3;
    private static final int ACK_SUCCESS = 0;
    private static final int ACK_LEN_VALID_ERROR = 1;

    public BleDecodeNrtData() {
    }

    public static void DecodePublicGetNrtDataCmdid(int cmdid, int[] buf, int offset, int len) {
        int keyValOffset = 0;
        SimpleDateFormat foo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int vaildDataLen;
        long utc;
        int var10000;
        String time;
        int j;
        Calendar cal;
        long utcStart;
        switch(buf[0 + offset]) {
            case 1:
                Constant.RtNotify = 2;
                if ((DeviceSurport.SaveDataField & 1) != 0) {
                    Log.d("moofit-NrtDateDecode", "len: " + len);

                    for(j = 0; j < len; ++j) {
                        Log.d("moofit-NrtDateDecode", " " + buf[3 + offset + j]);
                    }

                    while(len > keyValOffset) {
                        vaildDataLen = buf[3 + offset + keyValOffset++];
                        utc = (long)((buf[3 + offset + keyValOffset + 0] << 24) + (buf[3 + offset + keyValOffset + 1] << 16) + (buf[3 + offset + keyValOffset + 2] << 8) + buf[3 + offset + keyValOffset + 3]);
                        keyValOffset += 4;
                        cal = Calendar.getInstance(Locale.getDefault());
                        utcStart = utc - (long)(cal.get(15) / 1000);

                        for(j = 0; j < vaildDataLen / 2; ++j) {
                            StepData stepData = new StepData();
                            int stepsum = buf[3 + offset + keyValOffset++];
                            int caloriesum = buf[3 + offset + keyValOffset++];
                            Date date = new Date(utcStart * 1000L);
                            time = foo.format(date.getTime());
                            time = time.substring(0, 10);
                            stepData.setSteptime(time);
                            stepData.setStepdata(stepsum);
                            stepData.setCaloriedata(caloriesum);
                            stepData.setMarktime(time);
                            stepData.setUtctime((int)utcStart);
                            Log.e("moofit-NRT", "utc" + (int)utcStart);
                            Log.e("moofit-NRT", "time" + time);
                            Log.e("moofit-NRT", "step" + stepsum);
                            Log.e("moofit-NRT", "kcal" + caloriesum);
                            Array.liststep.add(stepData);
                            utcStart += 60L;
                        }
                    }
                }
                break;
            case 2:
                var10000 = DeviceSurport.SaveDataField;
                break;
            case 3:
                var10000 = DeviceSurport.SaveDataField;
                break;
            case 4:
                var10000 = DeviceSurport.SaveDataField;
                break;
            case 5:
                Log.e("jj", "睡眠数据");
                if ((DeviceSurport.SaveDataField & 16) != 0) {
                    while(len > keyValOffset) {
                        vaildDataLen = buf[3 + offset + keyValOffset++];
                        utc = (long)((buf[3 + offset + keyValOffset + 0] << 24) + (buf[3 + offset + keyValOffset + 1] << 16) + (buf[3 + offset + keyValOffset + 2] << 8) + buf[3 + offset + keyValOffset + 3]);
                        keyValOffset += 4;
                        cal = Calendar.getInstance(Locale.getDefault());
                        utcStart = utc - (long)(cal.get(15) / 1000);
                        if (utcStart < 1000000000L) {
                            Log.e("moofit-error", "DecodePublicGetNrtDataCmdid: ");
                        }

                        for(j = 0; j < vaildDataLen; ++j) {
                            SleepData sleepData = new SleepData();
                            long ss = utcStart * 1000L;
                            int sleepsum = buf[3 + offset + keyValOffset++];
                            Date date = new Date(ss);
                            time = foo.format(date.getTime());
                            sleepData.setSleeptime(utcStart);
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

                            Log.e("moofit-NRT-SLEEP", "time " + (int)utcStart);
                            Log.e("moofit-NRT-SLEEP", "utc " + time);
                            Log.e("moofit-NRT-SLEEP", "sleepsum " + sleepsum);
                            Array.listsleep.add(sleepData);
                            utcStart += 300L;
                        }
                    }
                }
                break;
            case 6:
                if ((DeviceSurport.SaveDataField & 32) != 0) {
                    while(len > keyValOffset) {
                        utc = (long)((buf[3 + offset + keyValOffset + 0] << 24) + (buf[3 + offset + keyValOffset + 1] << 16) + (buf[3 + offset + keyValOffset + 2] << 8) + buf[3 + offset + keyValOffset + 3]);
                        keyValOffset += 4;
                        j = (buf[3 + offset + keyValOffset + 0] << 24) + (buf[3 + offset + keyValOffset + 1] << 16) + (buf[3 + offset + keyValOffset + 2] << 8) + buf[3 + offset + keyValOffset + 3];
                        keyValOffset += 4;
                        int totalkcl = (buf[3 + offset + keyValOffset + 0] << 24) + (buf[3 + offset + keyValOffset + 1] << 16) + (buf[3 + offset + keyValOffset + 2] << 8) + buf[3 + offset + keyValOffset + 3];
                        keyValOffset += 4;
                        cal = Calendar.getInstance(Locale.getDefault());
                        utcStart = utc - (long) (cal.get(15) / 1000);
                        Date date = new Date(utcStart * 1000L);
                        time = foo.format(date.getTime());
                        DaysTotal daysTotal = new DaysTotal();
                        daysTotal.setUtc((int)utc);
                        daysTotal.setTotalStpe(j);
                        daysTotal.setTotalKcl(totalkcl);
                        daysTotal.setTime(time);
                        Log.e("moofit-NRT-TOTAL", "DOWNLOAD_NRT_ONEDAY_DATA_KEY:  utc " + utc);
                        Log.e("moofit-NRT-TOTAL", "DOWNLOAD_NRT_ONEDAY_DATA_KEY:  time " + time);
                        Log.e("moofit-NRT-TOTAL", "DOWNLOAD_NRT_ONEDAY_DATA_KEY:  totalstpe " + j);
                        Log.e("moofit-NRT-TOTAL", "DOWNLOAD_NRT_ONEDAY_DATA_KEY:  totalkcl " + totalkcl);
                        Array.listdaysTotal.add(daysTotal);
                    }
                }
                break;
            case 255:
                Constant.RtNotify = 1;
                String action = "com.wrist.ble.NRTDATAEND";
                Intent intent = new Intent(action);
                Constant.appcontext.sendBroadcast(intent);
                Nrtanalysis.setbledate();
                Log.e("ble-NrtDateDecode", "Nrt END ");
                Log.e("jj", "数据传输结束");
        }

    }
}
