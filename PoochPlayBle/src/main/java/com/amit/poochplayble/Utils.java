package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String TAG = "PushDemoActivity";
    public static final String RESPONSE_METHOD = "method";
    public static final String RESPONSE_CONTENT = "content";
    public static final String RESPONSE_ERRCODE = "errcode";
    protected static final String ACTION_LOGIN = "com.baidu.pushdemo.action.LOGIN";
    public static final String ACTION_MESSAGE = "com.baiud.pushdemo.action.MESSAGE";
    public static final String ACTION_RESPONSE = "bccsclient.action.RESPONSE";
    public static final String ACTION_SHOW_MESSAGE = "bccsclient.action.SHOW_MESSAGE";
    protected static final String EXTRA_ACCESS_TOKEN = "access_token";
    public static final String EXTRA_MESSAGE = "message";
    public static GattServices gattServices;
    public static String logStringCache = "";

    public Utils() {
    }



    public static List<String> getTagsList(String originalText) {
        if (originalText != null && !originalText.equals("")) {
            List<String> tags = new ArrayList();

            for(int indexOfComma = originalText.indexOf(44); indexOfComma != -1; indexOfComma = originalText.indexOf(44)) {
                String tag = originalText.substring(0, indexOfComma);
                tags.add(tag);
                originalText = originalText.substring(indexOfComma + 1);
            }

            tags.add(originalText);
            return tags;
        } else {
            return null;
        }
    }

    public static String getLogText(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString("log_text", "");
    }

    public static void setLogText(Context context, String text) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putString("log_text", text);
        editor.commit();
    }

    public static byte toByte(char c) {
        byte b = (byte)"0123456789ABCDEF".indexOf(c);
        return b;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src != null && src.length > 0) {
            for(int i = 0; i < src.length; ++i) {
                int v = src[i] & 255;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }

                stringBuilder.append(hv);
            }

            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public static byte[] hexStringToByte(String hex) {
        int len = hex.length() / 2;
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();

        for(int i = 0; i < len; ++i) {
            int pos = i * 2;
            result[i] = (byte)(toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }

        return result;
    }

    public static int getLowBit4(byte b) {
        int low = b & 15;
        return low;
    }

    public static byte getSumFromByteArray(byte[] data) {
        int checksum = 0;

        for(int j = 0; j <= data.length - 2; ++j) {
            int shuju = data[j];
            if (shuju < 0) {
                shuju += 256;
            }

            checksum += shuju;
        }

        byte result = (byte)(checksum & 255);
        return result;
    }
}
