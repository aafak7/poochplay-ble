package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.bluetooth.BluetoothAdapter;
import android.os.Message;
import android.util.Log;

import com.amit.poochplayble.Array.UploadCtrlSwitch;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class BleHelper {
    private static BleService mBleService;
    private static BluetoothAdapter mBluetoothAdapter;
    private static GattServices gattServices;

    static {
        mBleService = Constant.bleService;
    }

    public BleHelper() {
    }

    public static void bleOpDisconnect() {
        if (mBleService == null) {
            Log.e("ly", "bleOpDisconnect  mBleService==null");
        }

        mBleService.disconnect();
    }

    public static void bleclose_noconnect() {
        mBleService.close();
    }

    public static void bleOpClose() {
        mBleService.close();
        BleState.bleStatus = 18;
    }

    public static void bleOpConnect() {
        Log.e("ly", "手环+++++++连接");
        mBleService.connect(BleConstant.BraceletMac);
    }

    public static boolean newProBleOpenRealtimeChannel() {
        if (mBleService == null) {
            Log.e("ly", "mBleService==null");
        }

        Log.e("xxy", "打开notify");
        gattServices = new GattServices(mBleService);
        return gattServices.SetnotifyGattServices(mBleService.getSupportedGattServices());
    }

    public static void bleOpenNotRealtimeChannel() {
        Log.e("xxy", "打开非实时");
        gattServices = new GattServices(mBleService);
        gattServices.SetGattServices_old(mBleService.getSupportedGattServices());
    }

    public static boolean bleOpenRealtimeChannel() {
        gattServices = new GattServices(mBleService);
        return gattServices.SetnotifyGattServices_old(mBleService.getSupportedGattServices());
    }

    public static boolean bleOpenheartChannel() {
        gattServices = new GattServices(mBleService);
        return gattServices.SetheartGattServices(mBleService.getSupportedGattServices());
    }

    public static boolean blecloseheartChannel() {
        gattServices = new GattServices(mBleService);
        return gattServices.SetheartGattServicesfalse(mBleService.getSupportedGattServices());
    }

    public static boolean bleOpenAppointChannel(String ServicesUuid, String NotifyUuid) {
        gattServices = new GattServices(mBleService);
        return gattServices.OpenAppointCharacteristic(mBleService.getSupportedGattServices(), ServicesUuid, NotifyUuid);
    }

    public static boolean bleCloseAppointChannel(String ServicesUuid, String NotifyUuid) {
        gattServices = new GattServices(mBleService);
        return gattServices.CloseAppointCharacteristic(mBleService.getSupportedGattServices(), ServicesUuid, NotifyUuid);
    }

    public static void bleCloseNotRealtimeChannel() {
        gattServices = new GattServices(mBleService);
        gattServices.SetGattServicesfalse_old(mBleService.getSupportedGattServices());
    }

    public static void bleCloseRealtimeChannel() {
        gattServices = new GattServices(mBleService);
        gattServices.SetnotifyGattServicesfalse_old(mBleService.getSupportedGattServices());
    }

    public static void WriteChannel() {
    }

    public static void bleOpReConnect() {
        DisconnectStatusProcess();
        Log.e("ly", "bleOpReConnect");
        SysSendMsg.StartTimerMsg(1, 3, 500, false);
    }

    public static void DisconnectStatusProcess() {
        BleState.bleOldProtocolRtNotifyOnOffStatus = 52;
        BleState.bleOldProtocolNrtNotifyOnOffStatus = 52;
        BleState.bleOldProtocolSyncNrtStatus = 60;
        SysSendMsg.StopTimerMsg(1, 66);
        SysSendMsg.StopTimerMsg(1, 30);
        SysSendMsg.StopTimerMsg(1, 31);
        SysSendMsg.StopTimerMsg(1, 30);
        SysSendMsg.StopTimerMsg(1, 32);
    }

    public static void setRemindInfo(RemindStutasInfo remindInfo) {
        Log.e("moofit", "设置所有开关");
        Funtion.SendAllSwitch(remindInfo);
        Message msg = new Message();
        msg.what = 2;
        msg.arg1 = 11;
        msg.arg2 = 15;
        ProtocolHanderManager.SendMsg(msg);
    }

    public static void setAlarmPlan(ArrayList<AlarmPlanData> alarmPlanDatas) {
        try {
            Funtion.SettingAlarm(alarmPlanDatas);
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
        }

        Message msg = new Message();
        msg.what = 2;
        msg.arg1 = 11;
        msg.arg2 = 19;
        ProtocolHanderManager.SendMsg(msg);
    }

    public static void setWaterInfo(WaterSetInfo waterinfo) {
        Log.e("xxxx", "设置喝水提醒");
        Funtion.sendwaterTimeMsg(waterinfo);
        Message msg = new Message();
        msg.what = 2;
        msg.arg1 = 11;
        msg.arg2 = 21;
        ProtocolHanderManager.SendMsg(msg);
    }

    public static void setMoveInfo(MoveSetInfo moveInfo) {
        Log.e("moofit", "设置久坐提醒");
        Funtion.sendHealthTimeMsg(moveInfo);
        Message msg = new Message();
        msg.what = 2;
        msg.arg1 = 11;
        msg.arg2 = 20;
        ProtocolHanderManager.SendMsg(msg);
    }

    public static void GetDeviceInfo() {
        getSyncLastUtc();
        getSupport();
    }

    public static void setUserSleep() {
        Message msg = new Message();
        Log.e("moofit", "发送用户睡眠信息");
        msg.what = 2;
        msg.arg1 = 11;
        msg.arg2 = 24;
        ProtocolHanderManager.SendMsg(msg);
    }

    public static void setUserBodyInfo() {
        Message msg = new Message();
        Log.e("moofit", "发送用户基本信息");
        msg.what = 2;
        msg.arg1 = 11;
        msg.arg2 = 23;
        ProtocolHanderManager.SendMsg(msg);
    }

    private static void getSupport() {
        Message msg = new Message();
        Log.e("moofit", "发送获取设备支持消息");
        msg.what = 2;
        msg.arg1 = 12;
        msg.arg2 = 25;
        ProtocolHanderManager.SendMsg(msg);
    }

    private static void getSyncLastUtc() {
        Message msg = new Message();
        Log.e("moofit", "获取上次同步UTC");
        msg.what = 2;
        msg.arg1 = 12;
        msg.arg2 = 26;
        ProtocolHanderManager.SendMsg(msg);
    }

    public static void RTSwitch(int type) {
        Message msg = new Message();
        Log.e("moofit", "发送打开实时数据消息");
        Log.d("moofit", "需要打开实时数据通道");
        UploadCtrlSwitch.RtDatSetting = type;
        msg.what = 2;
        msg.arg1 = 11;
        msg.arg2 = 18;
        ProtocolHanderManager.SendMsg(msg);
    }

    public static void SetUTC() {
        Message msg = new Message();
        Log.e("moofit", "设置UTC");
        msg.what = 2;
        msg.arg1 = 11;
        msg.arg2 = 22;
        ProtocolHanderManager.SendMsg(msg);
    }

    public static boolean WaitTimeForGattServicesDiscoveredNew() {
        BleState.bleStatus = 15;
        return newProBleOpenRealtimeChannel();
    }

    public static void checkinfo() {
        new BleTransLayer(Constant.bleService);
    }

    public static void ResetBleOldProtocolSyncNrtProc() {
        BleState.bleOldProtocolSyncNrtStatus = 60;
        SysSendMsg.StopTimerMsg(1, 30);
    }
}
