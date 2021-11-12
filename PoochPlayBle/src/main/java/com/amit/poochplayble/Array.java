package com.amit.poochplayble;


import java.util.ArrayList;
import java.util.List;

public class Array {
    public static List<DaysTotal> listdaysTotal = new ArrayList();
    public static List<JumpData> listjump = new ArrayList();
    public static List<SleepData> listsleep = new ArrayList();
    public static List<StepData> liststep = new ArrayList();
    public static List<byte[]> listbyte = new ArrayList();
    public static List<String> liststring = new ArrayList();
    public static List<String> listafterstring = new ArrayList();
    public static List<String> listsleepstring = new ArrayList();
    public static List<HeartRateDataInfo> historyHeartRate = new ArrayList();
    public static List<String> listsleepafterstring = new ArrayList();
    public static int[] junp = new int[2];
    public static String starthour = "21";
    public static String startmin = "30";
    public static String peaceendhour = "08";
    public static String peaceendmin = "00";
    public static String weekendhour = "09";
    public static String weekendmin = "30";
    public static String nowtime;
    public static int totaldata;
    public static int totalcalorie;
    public static int notifyStps;
    public static int notifyKcal;
    public static int DecideProtocol = 0;
    public static int mileage;
    public static boolean isSync = false;
    public static boolean isConnect = false;
    public static boolean isLoading = false;
    public static boolean isSyncEnding = false;
    public final boolean ENABLE = true;
    public final boolean DISABLE = false;
    public static boolean ISSETTINGINFO = false;
    public static final int MISSEDCALL_POS = 8;
    public static final int EMAIL_POS = 9;
    public static final int SMS_POS = 10;
    public static final int WECHAT_POS = 11;
    public static final int QQ_POS = 12;
    public static final int SKYPE_POS = 13;
    public static final int WHATSAPP_POS = 14;
    public static final int FACEBOOK_POS = 15;
    public static final int OTHER_POS = 0;
    public final int ALARM_1ST_POS = 0;
    public final int ALARM_2ND_POS = 1;
    public final int ALARM_3RD_POS = 2;
    public final int ALARM_4TH_POS = 3;
    public final int ALARM_5TH_POS = 4;
    public final int ALARM_6TH_POS = 5;
    public final int ALARM_7TH_POS = 6;
    public final int ALARM_8TH_POS = 7;
    public static final int MISSEDCALL_TYPE = 0;
    public static final int EMAIL_TYPE = 1;
    public static final int SMS_TYPE = 2;
    public static final int WECHAT_TYPE = 3;
    public static final int QQ_TYPE = 4;
    public static final int SYKPE_TYPE = 5;
    public static final int WHATSAPP_TYPE = 6;
    public static final int FACEBOOK_TYPE = 7;
    public static final int OTHER_TYPE = 8;
    public static final int DEVICE_SURPORT_CALL_REMIND_POS = 0;
    public static final int DEVICE_SURPORT_CALLINFO_DISPLAY_POS = 1;
    public static final int DEVICE_SURPORT_MESSAGE_REMIND_POS = 2;
    public static final int DEVICE_SURPORT_MESSAGEINFO_DISPLAY_POS = 3;
    public static final int DEVICE_SURPORT_ALARM_REMIND_POS = 4;
    public static final int DEVICE_SURPORT_ALARM_MSG_DISPLAY_POS = 5;
    public static final int DEVICE_SURPORT_MOVE_REMIND_POS = 6;
    public static final int DEVICE_SURPORT_WATER_REMIND_POS = 7;
    public static final int DEVICE_SURPORT_RT_DATA_REMIND_POS = 8;
    public static final int HEALTH_WATER_POS = 0;
    public static final int HEALTH_MOVE_POS = 1;
    public static final int DEVICE_SURPORT_SPORT_DATA_SAVE_POS = 0;
    public static final int DEVICE_SURPORT_HEART_DATA_SAVE_POS = 1;
    public static final int DEVICE_SURPORT_SPD_CAD_DATA_SAVE_POS = 2;
    public static final int DEVICE_SURPORT_TRAIN_DATA_SAVE_POS = 3;
    public static final int DEVICE_SURPORT_SLEEP_DATA_SAVE_POS = 4;
    public static final int DEVICE_SURPORT_ONEDAY_DATA_SAVE_POS = 5;
    public static final int DEVICE_SURPORT_BYCICLE_PARA_SET_POS = 0;
    public static final int DEVICE_SURPORT_HEART_PARA_SET_POS = 1;
    public static final int DEVICE_SURPORT_ANT_PARA_SET_POS = 2;
    public static int transLayerVersion = 0;
    public static Array.DeviceSurport deviceSurport = new Array.DeviceSurport();
    public static Array.UploadCtrlSwitch swtihInfo = new Array.UploadCtrlSwitch();
    public static Array.UploadSettingTime settingTimeInfo = new Array.UploadSettingTime();
    public static Array.AndroidCallInfo androidCallInfo = new Array.AndroidCallInfo();
    public static Array.AndroidMsgInfo androidMsgInfo = new Array.AndroidMsgInfo();

    public Array() {
    }

    public static class AndroidCallInfo {
        public static int ctrlCode = 0;
        public static int phoneNumLen = 0;
        public static byte[] phoneNumber = new byte[15];

        public AndroidCallInfo() {
        }
    }

    public static class AndroidMsgInfo {
        public static int msgTotal = 0;
        public static int msgType = 0;
        public static int msgWhenHH = 0;
        public static int msgWhenMM = 0;
        public static int msgWhenSS = 0;
        public static int msgContxtLen = 0;
        public static byte[] msgContxt = new byte[60];

        public AndroidMsgInfo() {
        }
    }

    public static class DeviceSurport {
        public static int RemindField = 0;
        public static int SaveDataField = 0;
        public static int BycicleField = 0;
        public static int HeartField = 0;
        public static int AntField = 0;

        public DeviceSurport() {
        }
    }

    public static class RtCtrlData {
        public static int totalSteps = 0;
        public static float totalCalorie = 0.0F;
        public static int totalDistance = 0;

        public RtCtrlData() {
        }
    }

    public static class SettingAlarmTime {
        public static boolean isUploadAllAlarm;
        public static int needUploadAlarmSeq;
        public static Array.UploadSettingAlarmTime[] alarmInfo = new Array.UploadSettingAlarmTime[8];

        public SettingAlarmTime() {
        }
    }

    public static class UploadCtrlSwitch {
        public static int RtDatSetting = 0;
        public static int MsgSetting = 0;
        public static int AlarmSetting = 0;
        public static int HealthSetting = 0;

        public UploadCtrlSwitch() {
        }
    }

    public static class UploadSettingAlarmTime {
        public boolean isAvailable = false;
        public int alarmSeq = 0;
        public int alarmFormat = 0;
        public int alarmTimeUtc = 0;
        public int alarmTimeHH = 0;
        public int alarmTimeMM = 0;
        public int alarmTimeE = 0;
        public int alarmContextLen = 0;
        public byte[] alarmContext = new byte[10];

        public UploadSettingAlarmTime() {
        }
    }

    public static class UploadSettingHealthTime {
        public int startTimeHH1 = 0;
        public int startTimeMM1 = 0;
        public int EndTimeHH1 = 0;
        public int EndTimeMM1 = 0;
        public int startTimeHH2 = 0;
        public int startTimeMM2 = 0;
        public int EndTimeHH2 = 0;
        public int EndTimeMM2 = 0;
        public int period = 0;

        public UploadSettingHealthTime() {
        }
    }

    public static class UploadSettingTime {
        public Array.SettingAlarmTime settingAlarmTime = new Array.SettingAlarmTime();
        public Array.UploadSettingHealthTime moveInfo = new Array.UploadSettingHealthTime();
        public Array.UploadSettingHealthTime waterInfo = new Array.UploadSettingHealthTime();

        public UploadSettingTime() {
        }
    }

    public static class UserBodyInfo {
        public static int weight = 600;
        public static int age = 20;
        public static int height = 170;
        public static int sex = 1;
        public static int target = 10000;

        public UserBodyInfo() {
        }
    }

    public static class UserSleepInfo {
        public static int enableAutoSleepWakeup = 1;
        public static int startDetectSleepHH = 21;
        public static int startDetectSleepMM = 30;
        public static int wdStopDetectSleepHH = 8;
        public static int wdStopDetectSleepMM = 0;
        public static int rdStopDetectSleepHH = 9;
        public static int rdStopDetectSleepMM = 30;
        public static int autoSleepNoMoveMinCntsThreshold = 10;
        public static int autoWakeupActiveIndexValThreshold = 40;

        public UserSleepInfo() {
        }
    }

    public static class syncUtc {
        public static boolean isSync = false;
        public static long lastSyncUtc;

        public syncUtc() {
        }
    }
}