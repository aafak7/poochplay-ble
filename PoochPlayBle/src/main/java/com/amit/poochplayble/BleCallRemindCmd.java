package com.amit.poochplayble;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.util.Log;

public class BleCallRemindCmd {
    public BleCallRemindCmd() {
    }

    public static byte[] askDeviceInfo() {
        String instruct = "A3080100000000AC";
        byte[] bArray = Util.decodeHex(instruct.toCharArray());
        return bArray;
    }

    public static byte[] sendMissingCall() {
        String instruct = "A3080201050000B3";
        byte[] bArray = Util.decodeHex(instruct.toCharArray());
        Log.i("COMING", "sendMissingCall!!!!");
        return bArray;
    }

    public static byte[] sendCommingCall() {
        String instruct = "A3080202010000B0";
        byte[] bArray = Util.decodeHex(instruct.toCharArray());
        Log.i("COMING", "sendCommingCall!!!!");
        return bArray;
    }

    public static byte[] sendCommingCallReject() {
        String instruct = "A3080204040000B5";
        byte[] bArray = Util.decodeHex(instruct.toCharArray());
        Log.i("COMING", "sendCommingCallReplay!!!!");
        return bArray;
    }

    public static byte[] sendCommingCallReplay() {
        String instruct = "A3080205000000B2";
        byte[] bArray = Util.decodeHex(instruct.toCharArray());
        Log.i("COMING", "sendCommingCallReplay!!!!");
        return bArray;
    }

    public static byte[] openCommingCall() {
        String instruct = "A3080601000000B2";
        byte[] bArray = Util.decodeHex(instruct.toCharArray());
        Log.i("COMING", "openCommingCall!!!!");
        return bArray;
    }

    public static byte[] closeCommingCall() {
        String instruct = "A3080600000000B1";
        byte[] bArray = Util.decodeHex(instruct.toCharArray());
        Log.i("COMING", "closeCommingCall!!!!");
        return bArray;
    }
}
