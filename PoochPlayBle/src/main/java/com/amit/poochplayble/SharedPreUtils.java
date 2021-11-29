package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.io.File;

public class SharedPreUtils {
    private Context context;
    private static final String TAG = "SharedPreUtils";
    private static SharedPreUtils instance;

    public SharedPreUtils(Context context) {
        this.context = context;
    }

    public static SharedPreUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreUtils(context);
        }

        return instance;
    }

    public boolean setUserImage(String imagepath) {
        return this.addOrModify("user_image", imagepath);
    }

    public boolean getClick_1() {
        return this.getBoolean("pushSetting_click_1", false);
    }

    public boolean setClick_1(boolean isClick_1) {
        return this.addOrModify("pushSetting_click_1", isClick_1);
    }

    public boolean getClick_2() {
        return this.getBoolean("pushSetting_click_2", false);
    }

    public boolean setClick_2(boolean isClick_2) {
        return this.addOrModify("pushSetting_click_2", isClick_2);
    }

    public String getClick_2_item() {
        return this.getString("pushSetting_click_2_item", (String)null);
    }

    public boolean setClick_2_item(String item) {
        return this.addOrModify("pushSetting_click_2_item", item);
    }

    public String getClick_2_item_time() {
        return this.getString("pushSetting_click_2_item_time", (String)null);
    }

    public boolean setClick_2_item_time(String item) {
        return this.addOrModify("pushSetting_click_2_item_time", item);
    }

    public String getString(String key, String defValue) {
        return this.getString("jumpadd", key, defValue);
    }

    public int getInt(String key, int defValue) {
        return this.getInt("jumpadd", key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return this.getBoolean("jumpadd", key, defValue);
    }

    public long getLong(String key, long defValue) {
        return this.getLong("jumpadd", key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return this.getFloat("jumpadd", key, defValue);
    }

    public String getString(String fileName, String key, String defValue) {
        SharedPreferences prefs = this.context.getSharedPreferences(fileName, 0);
        return prefs.getString(key, defValue);
    }

    public int getInt(String fileName, String key, int defValue) {
        SharedPreferences prefs = this.context.getSharedPreferences(fileName, 0);
        return prefs.getInt(key, defValue);
    }

    public boolean getBoolean(String fileName, String key, boolean defValue) {
        SharedPreferences prefs = this.context.getSharedPreferences(fileName, 0);
        return prefs.getBoolean(key, defValue);
    }

    public long getLong(String fileName, String key, long defValue) {
        SharedPreferences prefs = this.context.getSharedPreferences(fileName, 0);
        return prefs.getLong(key, defValue);
    }

    public float getFloat(String fileName, String key, float defValue) {
        SharedPreferences prefs = this.context.getSharedPreferences(fileName, 0);
        return prefs.getFloat(key, defValue);
    }

    public boolean addOrModify(String key, String value) {
        return this.addOrModify("jumpadd", key, value);
    }

    public boolean addOrModify(String key, int value) {
        return this.addOrModify("jumpadd", key, value);
    }

    public boolean addOrModify(String key, boolean value) {
        return this.addOrModify("jumpadd", key, value);
    }

    public boolean addOrModify(String key, long value) {
        return this.addOrModify("jumpadd", key, value);
    }

    public boolean addOrModify(String key, float value) {
        return this.addOrModify("jumpadd", key, value);
    }

    public boolean addOrModify(String fileName, String key, String value) {
        SharedPreferences prefs = this.context.getSharedPreferences(fileName, 0);
        Editor editor = prefs.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public boolean addOrModify(String fileName, String key, int value) {
        SharedPreferences prefs = this.context.getSharedPreferences(fileName, 0);
        Editor editor = prefs.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public boolean addOrModify(String fileName, String key, boolean value) {
        SharedPreferences prefs = this.context.getSharedPreferences(fileName, 0);
        Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public boolean addOrModify(String fileName, String key, long value) {
        SharedPreferences prefs = this.context.getSharedPreferences(fileName, 0);
        Editor editor = prefs.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public boolean addOrModify(String fileName, String key, float value) {
        SharedPreferences prefs = this.context.getSharedPreferences(fileName, 0);
        Editor editor = prefs.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static void clearData(Context paramContext) {
        paramContext.getSharedPreferences("jumpadd", 0).edit().clear().commit();
    }

    public boolean deleteItem(String key) {
        return this.deleteItem("jumpadd", key);
    }

    public boolean deleteItem(String fileName, String key) {
        SharedPreferences prefs = this.context.getSharedPreferences(fileName, 0);
        if (prefs.contains(key)) {
            Editor editor = prefs.edit();
            editor.remove(key);
            return editor.commit();
        } else {
            return false;
        }
    }

    public boolean deleteAll(String fileName) {
        File file = new File("/data/data/" + this.context.getPackageName() + "/shared_prefs", fileName + ".xml");
        return file.exists() ? file.delete() : false;
    }
}
