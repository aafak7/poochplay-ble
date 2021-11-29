package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.util.Log;

import com.amit.poochplayble.Array.AndroidCallInfo;
import com.amit.poochplayble.Array.AndroidMsgInfo;
import com.amit.poochplayble.Array.DeviceSurport;
import com.amit.poochplayble.Array.SettingAlarmTime;
import com.amit.poochplayble.Array.UploadCtrlSwitch;
import com.amit.poochplayble.Array.UserBodyInfo;
import com.amit.poochplayble.Array.UserSleepInfo;
import com.amit.poochplayble.Array.syncUtc;
import com.amit.poochplayble.ProtocolTimerManager.TimerPara;

import java.util.Calendar;
import java.util.Locale;

public class BleEncodeRtData {
    private static Context context;
    private static final int NO_NEED_ACK_MSK = 0;
    private static final int CONFIRM_ACK_MSK = 1;
    private static final int DATA_ACK_MSK = 2;
    private static final int ENCODE_PKT_KEY_VAL_LEN_Pos = 4;

    public BleEncodeRtData() {
    }

    public static int EncodePublicCtrlData(int key) {
        byte[] buf = new byte[30];
        int offset = 0;
        Log.d("moofit-ENCODERTDATA", "EncodePublicCtrlData: ");
        int err_code = 0;
        offset = offset + 1;
        buf[offset] = 1;
        buf[offset++] = 16;
        buf[offset++] = (byte)key;
        int j;
        switch(key) {
            case 1:
                Log.d("moofit-ENCODERTDATA", "UPLOAD_SET_MESSAGE_REMIND_KEY: ");
                buf[offset++] = 1;
                buf[offset++] = 0;
                buf[offset++] = (byte)(UploadCtrlSwitch.MsgSetting >> 8);
                buf[offset++] = (byte) UploadCtrlSwitch.MsgSetting;
                buf[4] = (byte)(offset - 4 - 1);
                err_code = BleAppLayerDataProcess.AppLayerRtEncodeDat(buf, offset);
                break;
            case 2:
                Log.d("moofit-ENCODERTDATA", "UPLOAD_SET_ALARM_REMIND_KEY: " + UploadCtrlSwitch.AlarmSetting);
                buf[offset++] = 1;
                buf[offset++] = 0;
                buf[offset++] = (byte) UploadCtrlSwitch.AlarmSetting;
                buf[4] = (byte)(offset - 4 - 1);
                err_code = BleAppLayerDataProcess.AppLayerRtEncodeDat(buf, offset);
                break;
            case 3:
                Log.d("moofit-ENCODERTDATA", "UPLOAD_SET_HEALTH_REMIND_KEY: ");
                buf[offset++] = 1;
                buf[offset++] = 0;
                buf[offset++] = (byte) UploadCtrlSwitch.HealthSetting;
                buf[4] = (byte)(offset - 4 - 1);
                err_code = BleAppLayerDataProcess.AppLayerRtEncodeDat(buf, offset);
                break;
            case 4:
                Log.d("moofit-ENCODERTDATA", "UPLOAD_SET_RT_DATA_REMIND_KEY: ");
                buf[offset++] = 1;
                buf[offset++] = 0;
                buf[offset++] = (byte) UploadCtrlSwitch.RtDatSetting;
                buf[4] = (byte)(offset - 4 - 1);
                err_code = BleAppLayerDataProcess.AppLayerRtEncodeDat(buf, offset);
                break;
            case 5:
                Log.d("moofit-ENCODERTDATA", "UPLOAD_SET_ALARM_TIME_REMIND_KEY: " + SettingAlarmTime.needUploadAlarmSeq + SettingAlarmTime.isUploadAllAlarm);

                for(int i = SettingAlarmTime.needUploadAlarmSeq; i < 8; ++i) {
                    Log.d("moofit-ENCODERTDATA", "UPLOAD_SET_ALARM_TIME_REMIND_KEY11: " + i);
                    if (SettingAlarmTime.alarmInfo[i].isAvailable || !SettingAlarmTime.isUploadAllAlarm) {
                        if (!SettingAlarmTime.alarmInfo[i].isAvailable && !SettingAlarmTime.isUploadAllAlarm) {
                            Log.d("moofit-ENCODERTDATA", "UPLOAD_SET_ALARM_TIME_REMIND_KEY22:break ");
                            return err_code;
                        } else {
                            offset = 0;
                            offset = offset + 1;
                            buf[offset] = 1;
                            buf[offset++] = 16;
                            buf[offset++] = (byte)key;
                            buf[offset++] = 1;
                            buf[offset++] = 0;
                            buf[offset++] = (byte)((SettingAlarmTime.alarmInfo[i].alarmSeq << 1) + SettingAlarmTime.alarmInfo[i].alarmFormat);
                            if (SettingAlarmTime.alarmInfo[i].alarmFormat == 1) {
                                buf[offset++] = (byte)(SettingAlarmTime.alarmInfo[i].alarmTimeUtc >> 24);
                                buf[offset++] = (byte)(SettingAlarmTime.alarmInfo[i].alarmTimeUtc >> 16);
                                buf[offset++] = (byte)(SettingAlarmTime.alarmInfo[i].alarmTimeUtc >> 8);
                                buf[offset++] = (byte) SettingAlarmTime.alarmInfo[i].alarmTimeUtc;
                            } else {
                                buf[offset++] = (byte) SettingAlarmTime.alarmInfo[i].alarmTimeHH;
                                buf[offset++] = (byte) SettingAlarmTime.alarmInfo[i].alarmTimeMM;
                                buf[offset++] = (byte) SettingAlarmTime.alarmInfo[i].alarmTimeE;
                            }

                            if ((DeviceSurport.RemindField & 32) != 0) {
                                System.arraycopy(SettingAlarmTime.alarmInfo[i].alarmContext, 0, buf, offset, SettingAlarmTime.alarmInfo[i].alarmContextLen);
                                offset += SettingAlarmTime.alarmInfo[i].alarmContextLen;
                            }

                            buf[4] = (byte)(offset - 4 - 1);
                            System.out.print("alram encode data");

                            for(j = 0; j < offset; ++j) {
                                System.out.print(buf[j] + "  ");
                            }

                            System.out.println("");
                            err_code = BleAppLayerDataProcess.AppLayerRtEncodeDat(buf, offset);
                            if (err_code != 0) {
                                Log.d("moofit-ENCODERTDATA", "UPLOAD_SET_ALARM_TIME_REMIND_KEY33:break ");
                            } else if (SettingAlarmTime.isUploadAllAlarm) {
                                SettingAlarmTime.needUploadAlarmSeq = i + 1;
                                TimerPara timerPara = new TimerPara();
                                Log.d("moofit-ENCODERTDATA", "UPLOAD_SET_ALARM_TIME_REMIND_KEY44 " + SettingAlarmTime.needUploadAlarmSeq);
                                timerPara.timerEventPara.what = 2;
                                timerPara.timerEventPara.arg1 = 11;
                                timerPara.timerEventPara.arg2 = 19;
                                timerPara.timerTimePara.timeout = 100;
                                ProtocolTimerManager.ProtocolTimerStart(timerPara, false);
                                return err_code;
                            }

                            return err_code;
                        }
                    }

                    Log.d("moofit-ENCODERTDATA", "UPLOAD_SET_ALARM_TIME_REMIND_KEY11 continue: ");
                }

                return err_code;
            case 6:
                Log.d("moofit-ENCODERTDATA", "UPLOAD_SET_MOVE_TIME_KEY: ");
                buf[offset++] = 1;
                buf[offset++] = 0;
                buf[offset++] = (byte) Array.settingTimeInfo.moveInfo.startTimeHH1;
                buf[offset++] = (byte) Array.settingTimeInfo.moveInfo.startTimeMM1;
                buf[offset++] = (byte) Array.settingTimeInfo.moveInfo.EndTimeHH1;
                buf[offset++] = (byte) Array.settingTimeInfo.moveInfo.EndTimeMM1;
                buf[offset++] = (byte) Array.settingTimeInfo.moveInfo.startTimeHH2;
                buf[offset++] = (byte) Array.settingTimeInfo.moveInfo.startTimeMM2;
                buf[offset++] = (byte) Array.settingTimeInfo.moveInfo.EndTimeHH2;
                buf[offset++] = (byte) Array.settingTimeInfo.moveInfo.EndTimeMM2;
                buf[offset++] = (byte) Array.settingTimeInfo.moveInfo.period;
                buf[4] = (byte)(offset - 4 - 1);
                err_code = BleAppLayerDataProcess.AppLayerRtEncodeDat(buf, offset);
                break;
            case 7:
                Log.d("moofit-ENCODERTDATA", "UPLOAD_SET_WATER_TIME_KEY: ");
                buf[offset++] = 1;
                buf[offset++] = 0;
                buf[offset++] = (byte) Array.settingTimeInfo.waterInfo.startTimeHH1;
                buf[offset++] = (byte) Array.settingTimeInfo.waterInfo.startTimeMM1;
                buf[offset++] = (byte) Array.settingTimeInfo.waterInfo.EndTimeHH1;
                buf[offset++] = (byte) Array.settingTimeInfo.waterInfo.EndTimeMM1;
                buf[offset++] = (byte) Array.settingTimeInfo.waterInfo.startTimeHH2;
                buf[offset++] = (byte) Array.settingTimeInfo.waterInfo.startTimeMM2;
                buf[offset++] = (byte) Array.settingTimeInfo.waterInfo.EndTimeHH2;
                buf[offset++] = (byte) Array.settingTimeInfo.waterInfo.EndTimeMM2;
                buf[offset++] = (byte) Array.settingTimeInfo.waterInfo.period;
                buf[4] = (byte)(offset - 4 - 1);
                err_code = BleAppLayerDataProcess.AppLayerRtEncodeDat(buf, offset);
                break;
            case 8:
                Log.d("moofit-ENCODERTDATA", "UPLOAD_SET_UTC_KEY: ");
                Calendar calendar = Calendar.getInstance(Locale.getDefault());
                j = calendar.get(15) / 1000;
                long localTime = calendar.getTimeInMillis() / 1000L + (long)j;
                syncUtc.lastSyncUtc = localTime;
                Log.d("moofit", "发送UTC   " + syncUtc.lastSyncUtc);
                SharedPreUtils.getInstance(Constant.appcontext).addOrModify("lastSyncUtc", syncUtc.lastSyncUtc);
                buf[offset++] = 1;
                buf[offset++] = 0;
                buf[offset++] = (byte)((int)(localTime >> 24));
                buf[offset++] = (byte)((int)(localTime >> 16));
                buf[offset++] = (byte)((int)(localTime >> 8));
                buf[offset++] = (byte)((int)(localTime & 255L));
                buf[4] = (byte)(offset - 4 - 1);
                err_code = BleAppLayerDataProcess.AppLayerRtEncodeDat(buf, offset);
                break;
            case 9:
                Log.d("moofit-ENCODERTDATA", "UPLOAD_SET_USERBODYINFO_KEY: ");
                int stpeSize = (int)((double) UserBodyInfo.height * 0.414D);
                buf[offset++] = 1;
                buf[offset++] = 0;
                buf[offset++] = (byte)(UserBodyInfo.weight >> 8);
                buf[offset++] = (byte) UserBodyInfo.weight;
                buf[offset++] = (byte) UserBodyInfo.age;
                buf[offset++] = (byte) UserBodyInfo.height;
                buf[offset++] = (byte)stpeSize;
                buf[offset++] = (byte) UserBodyInfo.sex;
                buf[offset++] = (byte)(UserBodyInfo.target >> 16);
                buf[offset++] = (byte)(UserBodyInfo.target >> 8);
                buf[offset++] = (byte) UserBodyInfo.target;
                buf[4] = (byte)(offset - 4 - 1);
                err_code = BleAppLayerDataProcess.AppLayerRtEncodeDat(buf, offset);
                break;
            case 10:
                buf[offset++] = 1;
                buf[offset++] = 0;
                buf[offset++] = (byte) UserSleepInfo.enableAutoSleepWakeup;
                buf[offset++] = (byte) UserSleepInfo.startDetectSleepHH;
                buf[offset++] = (byte) UserSleepInfo.startDetectSleepMM;
                buf[offset++] = (byte) UserSleepInfo.wdStopDetectSleepHH;
                buf[offset++] = (byte) UserSleepInfo.wdStopDetectSleepMM;
                buf[offset++] = (byte) UserSleepInfo.rdStopDetectSleepHH;
                buf[offset++] = (byte) UserSleepInfo.rdStopDetectSleepMM;
                buf[offset++] = (byte) UserSleepInfo.autoSleepNoMoveMinCntsThreshold;
                buf[offset++] = (byte) UserSleepInfo.autoWakeupActiveIndexValThreshold;
                buf[4] = (byte)(offset - 4 - 1);
                err_code = BleAppLayerDataProcess.AppLayerRtEncodeDat(buf, offset);
                Log.i("jj", "err_code" + err_code);
        }

        return err_code;
    }

    public static int EncodePublicGetData(int key) {
        byte[] buf = new byte[20];
        int offset = 0;
        Log.d("moofit-ENCODERTDATA", "EncodePublicGetData: ");
        int err_code = 0;
        offset = offset + 1;
        buf[offset] = 3;
        buf[offset++] = 16;
        buf[offset++] = (byte)key;
        switch(key) {
            case 1:
                buf[offset++] = 2;
                buf[offset++] = 0;
                buf[4] = (byte)(offset - 4 - 1);
                err_code = BleAppLayerDataProcess.AppLayerRtEncodeDat(buf, offset);
                break;
            case 2:
                buf[offset++] = 2;
                buf[offset++] = 0;
                buf[4] = (byte)(offset - 4 - 1);
                err_code = BleAppLayerDataProcess.AppLayerRtEncodeDat(buf, offset);
        }

        return err_code;
    }

    public static int EncodePublicAndroidInfoData(int key) {
        byte[] buf = new byte[69];
        int offset = 0;
        Log.d("moofit-ENCODERTDATA", "EncodePublicAndroidInfoData: ");
        int err_code = 0;
        offset = offset + 1;
        buf[offset] = 2;
        buf[offset++] = 16;
        buf[offset++] = (byte)key;
        int i;
        switch(key) {
            case 1:
                buf[offset++] = 1;
                buf[offset++] = 0;
                buf[offset++] = (byte) AndroidCallInfo.ctrlCode;

                for(i = 0; i < AndroidCallInfo.phoneNumLen; ++i) {
                    buf[offset++] = AndroidCallInfo.phoneNumber[i];
                }

                buf[4] = (byte)(offset - 4 - 1);
                err_code = BleAppLayerDataProcess.AppLayerRtEncodeDat(buf, offset);
                break;
            case 2:
                Log.d("moofit", "UPLOAD_ANDROID_MESSAGE_KEY");
                buf[offset++] = 1;
                buf[offset++] = 0;
                buf[offset++] = (byte) AndroidMsgInfo.msgTotal;
                buf[offset++] = (byte) AndroidMsgInfo.msgType;
                buf[offset++] = (byte) AndroidMsgInfo.msgWhenHH;
                buf[offset++] = (byte) AndroidMsgInfo.msgWhenMM;

                for(i = 0; i < AndroidMsgInfo.msgContxtLen; ++i) {
                    buf[offset++] = AndroidMsgInfo.msgContxt[i];
                }

                buf[4] = (byte)(offset - 4 - 1);
                err_code = BleAppLayerDataProcess.AppLayerRtEncodeDat(buf, offset);
        }

        return err_code;
    }
}
