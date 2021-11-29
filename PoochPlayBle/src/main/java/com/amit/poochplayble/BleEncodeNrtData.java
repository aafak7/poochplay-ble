package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.util.Log;

public class BleEncodeNrtData {
    private static final int NO_NEED_ACK_MSK = 0;
    private static final int CONFIRM_ACK_MSK = 1;
    private static final int DATA_ACK_MSK = 2;
    private static final int ENCODE_PKT_KEY_VAL_LEN_Pos = 4;

    public BleEncodeNrtData() {
    }

    public static int EncodePublicGetNrtData(int key) {
        byte[] buf = new byte[20];
        int offset = 0;
        int err_code = 0;
        offset = offset + 1;
        buf[offset] = 4;
        buf[offset++] = 16;
        buf[offset++] = (byte)key;
        switch(key) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                buf[offset++] = 2;
                buf[offset++] = 0;
                buf[4] = (byte)(offset - 4 - 1);
                err_code = BleAppLayerDataProcess.AppLayerNrtEncodeDat(buf, offset);
                Log.e("ble", "EncodePublicGetNrtData:err_code: " + err_code);
            default:
                return err_code;
        }
    }
}
