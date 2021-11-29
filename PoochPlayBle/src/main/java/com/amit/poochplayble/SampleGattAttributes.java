package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.util.HashMap;

public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
    public static String BRACELETSERVER_UUID = "0000fc00-0000-1000-8000-00805f9b34fb";
    public static String BRACELETREAD_UUID = "0000fc20-0000-1000-8000-00805f9b34fb";
    public static String BRACELETNOTIFY_UUID = "0000fc22-0000-1000-8000-00805f9b34fb";
    public static String BRACELETWRITE_UUID = "0000fc21-0000-1000-8000-00805f9b34fb";
    public static String JUMPSERVER_UUID = "8fc3fc00-f21d-11e3-976c-0002a5d5c51b";
    public static String JUMPREAD_UUID = "8fc3fc25-f21d-11e3-976c-0002a5d5c51b";
    public static String SERVER_UUID = "0000fd00-0000-1000-8000-00805f9b34fb";
    public static String WRITE_CALL_UUID = "0000fd18-0000-1000-8000-00805f9b34fb";
    public static String NOTIFY_WRITE_UUID = "0000fd1a-0000-1000-8000-00805f9b34fb";
    public static String NOTIFY_UUID = "0000fd19-0000-1000-8000-00805f9b34fb";
    public static String HEARTSERVER_UUID = "0000180d-0000-1000-8000-00805f9b34fb";
    public static String HEARTREAD_UUID = "00002a37-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";

    static {
        attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
    }

    public SampleGattAttributes() {
    }

    public static String lookup(String uuid, String defaultName) {
        String name = (String)attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}
