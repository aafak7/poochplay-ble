package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.amit.poochplayble.Array.DeviceSurport;
import com.amit.poochplayble.Array.RtCtrlData;
import com.amit.poochplayble.Array.syncUtc;


public class BleDecodeRtData {
    private static final int KEY_Pos = 0;
    private static final int CONFIRM_ACK_MSK = 1;
    private static final int ACK_MSK = 3;
    private static final int KEY_VAL_Pos = 3;
    private static Handler handler;
    private static final int ACK_SUCCESS = 0;
    private static final int ACK_LEN_VALID_ERROR = 1;

    public BleDecodeRtData() {
    }

    public static void sethandler(Handler mhandler) {
        handler = mhandler;
    }

    public static void DecodePublicRtDataCmdid(int cmdid, int[] buf, int offset, int len) {
        switch(buf[0 + offset]) {
            case 1:
                if (len == 9) {
                    RtCtrlData.totalSteps = (buf[3 + offset] << 16) + (buf[4 + offset] << 8) + buf[5 + offset];
                    RtCtrlData.totalDistance = (buf[6 + offset] << 16) + (buf[7 + offset] << 8) + buf[8 + offset];
                    RtCtrlData.totalCalorie = (float)((buf[9 + offset] << 16) + (buf[10 + offset] << 8) + buf[11 + offset]);
                    handler.sendEmptyMessage(1);
                    Log.d("ly", "步数" + RtCtrlData.totalSteps + "卡路里" + RtCtrlData.totalCalorie + "公里数" + RtCtrlData.totalDistance);
                }
            default:
        }
    }

    public static void DecodePublicFindPhoneCmdid(int cmdid, int[] buf, int offset, int len) {
        switch(buf[0 + offset]) {
            case 1:
                if (len == 1) {
                    Util.RingPhone(buf[3 + offset], Constant.appcontext);
                }
            default:
        }
    }

    public static void DecodePublicGetRtDatCmdid(int cmdid, int[] buf, int offset, int len) {
        switch(buf[0 + offset]) {
            case 1:
                if (len >= 3) {
                    DeviceSurport.BycicleField = 0;
                    DeviceSurport.HeartField = 0;
                    DeviceSurport.AntField = 0;
                    DeviceSurport.RemindField = (buf[3 + offset] << 8) + buf[3 + offset + 1];
                    DeviceSurport.SaveDataField = buf[3 + offset + 2];
                    if (len >= 4) {
                        DeviceSurport.BycicleField = buf[3 + offset + 3];
                    }

                    if (len >= 5) {
                        DeviceSurport.BycicleField = buf[3 + offset + 4];
                    }

                    if (len >= 6) {
                        DeviceSurport.BycicleField = buf[3 + offset + 5];
                    }

                    Log.d("moofit", "RemindField" + DeviceSurport.RemindField + "SaveDataField" + DeviceSurport.SaveDataField);
                }
                break;
            case 2:
                if (len == 4) {
                    long tmpUtc = (long)((buf[3 + offset] << 24) + (buf[4 + offset] << 16) + (buf[5 + offset] << 8) + buf[6 + offset]);
                    syncUtc.isSync = false;
                    if (Math.abs(tmpUtc - syncUtc.lastSyncUtc) < 5L) {
                        syncUtc.isSync = true;
                    }

                    Log.d("moofit", "ack sync utc dat = " + tmpUtc + "         " + syncUtc.lastSyncUtc + "   " + syncUtc.isSync);
                    Array.ISSETTINGINFO = true;
                    String action = "com.wrist.ble.SUCCESSSETINFO";
                    Intent intent = new Intent(action);
                    Constant.appcontext.sendBroadcast(intent);
                }

                Log.d("moofit", "DOWNLOAD_LAST_SYNC_UTC_KEY");
        }

    }

    public static void DecodePublicVersionCmdid(int cmdid, int[] buf, int offset, int len) {
        switch(buf[0 + offset]) {
            case 1:
                if (len == 1) {
                    Array.transLayerVersion = buf[0 + offset + 1];
                    Log.d("moofit", "trans layer version : " + Array.transLayerVersion);
                    BleHelper.GetDeviceInfo();
                }
            default:
        }
    }

    public static void DecodePrivateUnknowCmdid(int cmdid, int[] buf, int offset, int len) {
        int var10000 = buf[0 + offset];
    }
}
