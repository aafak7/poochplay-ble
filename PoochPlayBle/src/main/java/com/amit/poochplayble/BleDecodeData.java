package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.os.Handler;

import com.amit.poochplayble.Array.SettingAlarmTime;
import com.amit.poochplayble.Array.UploadSettingAlarmTime;
import com.amit.poochplayble.BleAppLayerDataProcess.AppLayerRxInfo;

public class BleDecodeData {
    private static final int PACKET_HEADER_LEN = 2;
    private static final int KEY_HEADER_LEN = 2;
    private static final int KEY_LEN = 1;
    private static final int CMDID_Pos = 0;
    private static final int VERSION_AND_UTILIZE_Pos = 1;
    private static final int VERSION_DEFAULT_MSK = 240;
    private static final int UTILIZE_PRIVATE_MSK = 8;
    public static Handler analysishandler;
    private static final int KEY_VAL_LEN_Pos = 2;

    public BleDecodeData() {
    }

    public static void setHandler(Handler handler) {
        analysishandler = handler;
    }

    public static void initializeAlarmInfo() {
        for(int i = 0; i < 8; ++i) {
            SettingAlarmTime var10000 = Array.settingTimeInfo.settingAlarmTime;
            SettingAlarmTime.alarmInfo[i] = new UploadSettingAlarmTime();
        }

    }

    public static void DataDecode(AppLayerRxInfo rx_dat) {
        int cmdid = rx_dat.appLayerRxBuf[0];
        int read_pos = rx_dat.fifoBuf.readPos;
        int write_pos = rx_dat.fifoBuf.writePos;
        int[] buf = rx_dat.appLayerRxBuf;
        if ((buf[read_pos + 1] & 240) == 16) {
            int key_val_len;
            if ((BleAppLayerDataProcess.appLayerRxInfo.appLayerRxBuf[read_pos + 1] & 8) == 24) {
                for(read_pos += 2; read_pos < write_pos; read_pos += key_val_len + 2 + 1) {
                    key_val_len = BleAppLayerDataProcess.appLayerRxInfo.appLayerRxBuf[read_pos + 2];
                    DecodePrivateCmdid(cmdid, buf, read_pos, key_val_len);
                }
            } else {
                for(read_pos += 2; read_pos < write_pos; read_pos += key_val_len + 2 + 1) {
                    key_val_len = buf[read_pos + 2];
                    DecodePublicCmdid(cmdid, buf, read_pos, key_val_len);
                }
            }
        }

    }

    private static void DecodePublicCmdid(int cmdid, int[] buf, int offset, int key_val_len) {
        switch(cmdid) {
            case 1:
                BleDecodeRtData.DecodePublicRtDataCmdid(cmdid, buf, offset, key_val_len);
                break;
            case 2:
                BleDecodeRtData.DecodePublicFindPhoneCmdid(cmdid, buf, offset, key_val_len);
                break;
            case 3:
                BleDecodeRtData.DecodePublicGetRtDatCmdid(cmdid, buf, offset, key_val_len);
                break;
            case 4:
                BleDecodeNrtData.DecodePublicGetNrtDataCmdid(cmdid, buf, offset, key_val_len);
                break;
            case 5:
                BleDecodeRtData.DecodePublicVersionCmdid(cmdid, buf, offset, key_val_len);
        }

    }

    public static void DecodePrivateCmdid(int cmdid, int[] buf, int offset, int key_val_len) {
        BleDecodeRtData.DecodePrivateUnknowCmdid(cmdid, buf, offset, key_val_len);
    }
}
