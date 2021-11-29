package com.amit.poochplayble;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.bluetooth.BluetoothGattService;
import android.os.Message;
import android.util.Log;

import com.amit.poochplayble.Array.AndroidCallInfo;
import com.amit.poochplayble.Array.AndroidMsgInfo;
import com.amit.poochplayble.Array.DeviceSurport;
import com.amit.poochplayble.Array.SettingAlarmTime;
import com.amit.poochplayble.Array.UploadCtrlSwitch;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Funtion {
    public Funtion() {
    }

    public static void SendAllSwitch(RemindStutasInfo remindInfo) {
        if (remindInfo.getFacebook() == 1) {
            UploadCtrlSwitch.MsgSetting |= 32768;
        }

        if (remindInfo.getMisscall() == 1) {
            UploadCtrlSwitch.MsgSetting |= 256;
        }

        if (remindInfo.getMail() == 1) {
            UploadCtrlSwitch.MsgSetting |= 512;
        }

        if (remindInfo.getQq() == 1) {
            UploadCtrlSwitch.MsgSetting |= 4096;
        }

        if (remindInfo.getShortmsg() == 1) {
            UploadCtrlSwitch.MsgSetting |= 1024;
        }

        if (remindInfo.getWechat() == 1) {
            UploadCtrlSwitch.MsgSetting |= 2048;
        }

        if (remindInfo.getWhatapps() == 1) {
            UploadCtrlSwitch.MsgSetting |= 16384;
        }

        if (remindInfo.getSkype() == 1) {
            UploadCtrlSwitch.MsgSetting |= 8192;
        }

        if (remindInfo.getOther() == 1) {
            UploadCtrlSwitch.MsgSetting |= 1;
        }

        Log.e("moofit", "MsgSwitch: SendAllSwitch" + Integer.toBinaryString(UploadCtrlSwitch.MsgSetting));
    }

    public static void SettingAlarm(ArrayList<AlarmPlanData> alarmPlanDatas) throws UnsupportedEncodingException {
        int alarmSeqId = 65535;
        long tmpUtc = 0L;
        Log.d("xxy", "alarm SIZE:" + alarmPlanDatas.size());
        if (alarmPlanDatas != null) {
            for(int i = 0; i < alarmPlanDatas.size(); ++i) {
                alarmSeqId = ((AlarmPlanData)alarmPlanDatas.get(i)).getNumbers();
                Log.e("moofit-alarm time", "alarmseq: " + alarmSeqId);
                int alarmFormat = 0;
                if (((AlarmPlanData)alarmPlanDatas.get(i)).getDay().length == 1) {
                    int mark = ((AlarmPlanData)alarmPlanDatas.get(i)).getDay()[0];
                    if (mark == 8) {
                        alarmFormat = 1;
                    }
                }

                byte[] unicode = ((AlarmPlanData)alarmPlanDatas.get(i)).getPlanName().getBytes("UNICODE");
                byte[] alarmContext = new byte[10];
                int alaramLen = unicode.length;
                if (alaramLen > 12) {
                    alaramLen = 12;
                }

                int openStatus;
                for(openStatus = 0; openStatus < (alaramLen - 2) / 2; ++openStatus) {
                    alarmContext[openStatus * 2] = unicode[(openStatus + 1) * 2 + 1];
                    alarmContext[openStatus * 2 + 1] = unicode[(openStatus + 1) * 2];
                }

                Log.d("xxy", "alarm seq:" + alarmSeqId);
                SettingAlarmTime.alarmInfo[alarmSeqId].isAvailable = true;
                SettingAlarmTime.alarmInfo[alarmSeqId].alarmSeq = alarmSeqId;
                SettingAlarmTime.alarmInfo[alarmSeqId].alarmFormat = alarmFormat;
                SettingAlarmTime.alarmInfo[alarmSeqId].alarmContextLen = alaramLen - 2;
                SettingAlarmTime.alarmInfo[alarmSeqId].alarmContext = alarmContext;

                for(openStatus = 0; openStatus < 8; ++openStatus) {
                    Log.d("bug debug", "alarm valid==:" + openStatus + ":" + SettingAlarmTime.alarmInfo[openStatus].isAvailable);
                }

                int j;
                if (alarmFormat == 1) {
                    Calendar calendar = Calendar.getInstance(Locale.getDefault());
                    j = calendar.get(15) / 1000;
                    String dateString = ((AlarmPlanData)alarmPlanDatas.get(i)).getRemindUtc() + ":00";
                    Log.e("xxy", "dateString" + dateString);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    try {
                        Date date = sdf.parse(dateString);
                        tmpUtc = date.getTime() / 1000L + (long)j;
                    } catch (ParseException var14) {
                        var14.printStackTrace();
                    }

                    SettingAlarmTime.alarmInfo[alarmSeqId].alarmTimeUtc = (int)tmpUtc;
                } else {
                    openStatus = 0;

                    for(j = 0; j < ((AlarmPlanData)alarmPlanDatas.get(i)).getDay().length; ++j) {
                        if (((AlarmPlanData)alarmPlanDatas.get(i)).getDay()[j] == 7) {
                            openStatus |= 1;
                        } else {
                            openStatus |= 1 << ((AlarmPlanData)alarmPlanDatas.get(i)).getDay()[j];
                        }
                    }

                    String timeString = ((AlarmPlanData)alarmPlanDatas.get(i)).getRemindTime();
                    Log.e("xxy", "timeString" + timeString);
                    SettingAlarmTime.alarmInfo[alarmSeqId].alarmTimeHH = Integer.parseInt(timeString.substring(0, 2));
                    SettingAlarmTime.alarmInfo[alarmSeqId].alarmTimeMM = Integer.parseInt(timeString.substring(3, 5));
                    SettingAlarmTime.alarmInfo[alarmSeqId].alarmTimeE = openStatus;
                }

                openStatus = ((AlarmPlanData)alarmPlanDatas.get(i)).getOpenStatus();
                Message msg1;
                if (openStatus == 1) {
                    Log.d("xxy", "moofit-openStatus true");
                    UploadCtrlSwitch.AlarmSetting |= 1 << alarmSeqId;
                    msg1 = new Message();
                    msg1.what = 2;
                    msg1.arg1 = 11;
                    msg1.arg2 = 16;
                    ProtocolHanderManager.SendMsg(msg1);
                } else if (openStatus == 2) {
                    Log.d("xxy", "moofit-openStatus false");
                    UploadCtrlSwitch.AlarmSetting &= ~(1 << alarmSeqId);
                    msg1 = new Message();
                    msg1.what = 2;
                    msg1.arg1 = 11;
                    msg1.arg2 = 16;
                    ProtocolHanderManager.SendMsg(msg1);
                }
            }

            if (alarmSeqId != 65535) {
                if (alarmPlanDatas.size() == 1) {
                    Log.d("xxy", "moofit-isUploadAllAlarm false");
                    SettingAlarmTime.isUploadAllAlarm = false;
                    SettingAlarmTime.needUploadAlarmSeq = alarmSeqId;
                } else {
                    Log.d("xxy", "moofit-isUploadAllAlarm true");
                    SettingAlarmTime.isUploadAllAlarm = true;
                    SettingAlarmTime.needUploadAlarmSeq = 0;
                }
            }
        }

    }

    public static void sendwaterTimeMsg(WaterSetInfo waterinfo) {
        if (waterinfo != null) {
            Array.settingTimeInfo.waterInfo.startTimeHH1 = waterinfo.getAmStartHH();
            Array.settingTimeInfo.waterInfo.startTimeMM1 = waterinfo.getAmStartMM();
            Array.settingTimeInfo.waterInfo.EndTimeHH1 = waterinfo.getAmEndHH();
            Array.settingTimeInfo.waterInfo.EndTimeMM1 = waterinfo.getAmEndMM();
            Array.settingTimeInfo.waterInfo.startTimeHH2 = waterinfo.getPmStartHH();
            Array.settingTimeInfo.waterInfo.startTimeMM2 = waterinfo.getPmStartMM();
            Array.settingTimeInfo.waterInfo.EndTimeHH2 = waterinfo.getPmEndHH();
            Array.settingTimeInfo.waterInfo.EndTimeMM2 = waterinfo.getPmEndMM();
            Array.settingTimeInfo.waterInfo.period = waterinfo.getIntervalTime();
            Message msg1;
            if (waterinfo.getOpenStatus() == 1) {
                UploadCtrlSwitch.HealthSetting |= 1;
                msg1 = new Message();
                msg1.what = 2;
                msg1.arg1 = 11;
                msg1.arg2 = 17;
                ProtocolHanderManager.SendMsg(msg1);
            } else if (waterinfo.getOpenStatus() == 2) {
                UploadCtrlSwitch.HealthSetting &= -2;
                msg1 = new Message();
                msg1.what = 2;
                msg1.arg1 = 11;
                msg1.arg2 = 17;
                ProtocolHanderManager.SendMsg(msg1);
            }
        }

    }

    public static void sendHealthTimeMsg(MoveSetInfo moveInfo) {
        if (moveInfo != null) {
            Array.settingTimeInfo.moveInfo.startTimeHH1 = moveInfo.getAmStartHH();
            Array.settingTimeInfo.moveInfo.startTimeMM1 = moveInfo.getAmStartMM();
            Array.settingTimeInfo.moveInfo.EndTimeHH1 = moveInfo.getAmEndHH();
            Array.settingTimeInfo.moveInfo.EndTimeMM1 = moveInfo.getAmEndMM();
            Array.settingTimeInfo.moveInfo.startTimeHH2 = moveInfo.getPmStartHH();
            Array.settingTimeInfo.moveInfo.startTimeMM2 = moveInfo.getPmStartMM();
            Array.settingTimeInfo.moveInfo.EndTimeHH2 = moveInfo.getPmEndHH();
            Array.settingTimeInfo.moveInfo.EndTimeMM2 = moveInfo.getPmEndMM();
            Array.settingTimeInfo.moveInfo.period = moveInfo.getIntervalTime();
            Message msg1;
            if (moveInfo.getOpenStatus() == 1) {
                UploadCtrlSwitch.HealthSetting |= 2;
                msg1 = new Message();
                msg1.what = 2;
                msg1.arg1 = 11;
                msg1.arg2 = 17;
                ProtocolHanderManager.SendMsg(msg1);
            } else if (moveInfo.getOpenStatus() == 2) {
                UploadCtrlSwitch.HealthSetting &= -3;
                msg1 = new Message();
                msg1.what = 2;
                msg1.arg1 = 11;
                msg1.arg2 = 17;
                ProtocolHanderManager.SendMsg(msg1);
            }
        }

    }

    public void setdecideProtocol() {
        List<BluetoothGattService> supportedGattServices = Constant.bleService.getSupportedGattServices();
        GattServices gattServicer = Constant.mgattServices;
        int decideProtocol = gattServicer.decideProtocol(supportedGattServices);
        Array.DecideProtocol = decideProtocol;
    }

    public void Controlrtdata(int type) {
        if (Array.DecideProtocol == 1) {
           BleHelper.RTSwitch(type);
        }

        if (Array.DecideProtocol == 2) {
            if (type == 1) {
                this.openoldrtdecideProtocol();
            } else {
               BleHelper.bleCloseRealtimeChannel();
            }
        }

    }

    public boolean OpenNotifyProtocol() {
        boolean issuccess = false;
        if (Array.DecideProtocol == 1) {
            issuccess = this.opennewdecideProtocol();
            BleHelper.checkinfo();
        }

        return issuccess;
    }

    public void getnrtdata() {
        Message msg = new Message();
        if (Constant.RtNotify == 1) {
            if (Array.DecideProtocol == 1) {
                Constant.RtNotify = 2;
                msg.what = 2;
                msg.arg1 = 14;
                msg.arg2 = 29;
                ProtocolHanderManager.SendMsg(msg);
                Log.e("ble--onRefresh", "onRefresh: 获取历史数据");
            } else if (Array.DecideProtocol == 2 && BleState.bleOldProtocolSyncNrtStatus == 60) {
                Constant.RtNotify = 2;
                SysSendMsg.StartTimerMsg(1, 30, 500, false);
                BleState.bleOldProtocolSyncNrtStatus = 61;
            }
        }

    }

    public void getheartnrtdata() {
        new Message();
        if (Constant.RtNotify == 1 && BleState.bleOldProtocolSyncNrtStatus == 60) {
            Constant.RtNotify = 2;
            SysSendMsg.StartTimerMsg(1, 30, 500, false);
            BleState.bleOldProtocolSyncNrtStatus = 61;
        }

    }

    public boolean opennewdecideProtocol() {
        return BleHelper.WaitTimeForGattServicesDiscoveredNew();
    }

    public boolean openheartChannel() {
        return BleHelper.bleOpenheartChannel();
    }

    public boolean closeheartChannel() {
        return BleHelper.blecloseheartChannel();
    }

    public boolean OpenAppointCharacteristic(String ServicesUuid, String NotifyUuid) {
        return BleHelper.bleOpenAppointChannel(ServicesUuid, NotifyUuid);
    }

    public boolean CloseAppointCharacteristic(String ServicesUuid, String NotifyUuid) {
        return BleHelper.bleCloseAppointChannel(ServicesUuid, NotifyUuid);
    }

    public boolean openoldrtdecideProtocol() {
        boolean issuccess = false;
        BleState.bleOldProtocolRtNotifyOnOffStatus = 52;
        BleState.bleOldProtocolNrtNotifyOnOffStatus = 52;
        if (BleState.bleOldProtocolRtNotifyOnOffStatus == 52) {
            issuccess = BleHelper.bleOpenRealtimeChannel();
            BleState.bleOldProtocolRtNotifyOnOffStatus = 51;
        } else {
            issuccess = true;
        }

        return issuccess;
    }

    private void SendQqWx(int type, int whenHH, int whenMM, int whenSS, String context) {
        if (whenHH != AndroidMsgInfo.msgWhenHH || whenMM != AndroidMsgInfo.msgWhenMM || whenSS != AndroidMsgInfo.msgWhenSS) {
            if (context != null) {
                Message msg = new Message();
                String end = "...";
                byte[] QQWXmsg = context.getBytes();
                byte[] endchar = end.getBytes();
                int msglen = QQWXmsg.length;
                int reallen;
                if ((DeviceSurport.RemindField & 8) == 0) {
                    reallen = 0;
                } else if (msglen > 57) {
                    reallen = 60;
                    QQWXmsg[57] = endchar[0];
                    QQWXmsg[58] = endchar[1];
                    QQWXmsg[59] = endchar[2];
                } else {
                    reallen = msglen;
                }

                Log.e("moofit", "继续");
                AndroidMsgInfo.msgTotal = 1;
                AndroidMsgInfo.msgType = type;
                AndroidMsgInfo.msgWhenHH = whenHH;
                AndroidMsgInfo.msgWhenMM = whenMM;
                AndroidMsgInfo.msgWhenSS = whenSS;
                AndroidMsgInfo.msgContxtLen = reallen;
                AndroidMsgInfo.msgContxt = QQWXmsg;
                msg.what = 2;
                msg.arg1 = 13;
                msg.arg2 = 28;
                ProtocolHanderManager.SendMsg(msg);
            }

        }
    }

    public void CallUpload(String phonename, boolean isCalling) {
        if ((DeviceSurport.RemindField & 1) != 0) {
            Message msg = new Message();
            String calname = "";
            int ctrlcode = 0;
            if (isCalling) {
                ctrlcode = 1;
            }

            Log.d("moofit-phone", "*******calname来电了：********" + calname);
            byte[] missCallmsg = calname.getBytes();
            int phoneLen = missCallmsg.length;
            int reallen;
            if ((DeviceSurport.RemindField & 2) == 0) {
                reallen = 0;
            } else if (phoneLen > 15) {
                reallen = 15;
            } else {
                reallen = phoneLen;
            }

            AndroidCallInfo.ctrlCode = ctrlcode;
            AndroidCallInfo.phoneNumLen = reallen;
            AndroidCallInfo.phoneNumber = missCallmsg;
            msg.what = 2;
            msg.arg1 = 13;
            msg.arg2 = 27;
            ProtocolHanderManager.SendMsg(msg);
        }
    }
}
