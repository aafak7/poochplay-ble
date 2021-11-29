package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class BleReceiver extends BroadcastReceiver {
    private boolean mConnected = false;
    public BleService bluetoothLeService;
    private String value;
    public Funtion funtion = new Funtion();
    public StepData stepData;
    public Handler handler;

    public BleReceiver(Handler handler) {
        this.handler = handler;
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Message ness = new Message();
        if ("com.wrist.ble.ACTION_GATT_CONNECTED".equals(action)) {
            BleState.bleStatus = 13;
            Log.i("ly", "ACTION_GATT_CONNECTED");
            ness.arg1 = 3;
        } else if ("com.wrist.blei.ACTION_GATT_DISCONNECTED".equals(action)) {
            Log.i("ly", "ACTION_GATT_DISCONNECTED");
            BleState.bleStatus = 17;
            ness.arg1 = 4;
        } else if ("com.wrist.ble.ACTION_GATT_SERVICES_DISCOVERED".equals(action)) {
            Log.i("ly", "ACTION_GATT_SERVICES_DISCOVERED");
            BleState.bleStatus = 15;
            ness.arg1 = 1;
        } else if ("com.wrist.ble.ACTION_DATA_AVAILABLE".equals(action)) {
            BleState.bleStatus = 19;
            ness.arg1 = 2;
            BleData bleData = new BleData();
            bleData.setUuid(intent.getStringExtra("uuid").toString());
            bleData.setValue(intent.getStringExtra("com.wrist.ble.ui.EXTRA_DATA"));
            Bundle b = new Bundle();
            b.putSerializable("data", bleData);
            ness.setData(b);
        } else if ("com.wrist.ble.SUCCESSSETINFO".equals(action)) {
            Log.i("xxy", "com.wrist.ble.SUCCESSSETINFO");
            ness.arg1 = 5;
        } else if ("com.wrist.ble.NRTDATAEND".equals(action)) {
            Log.i("ly", "com.wrist.ble.NRTDATAEND");
            ness.arg1 = 6;
        } else if ("com.heart.ble.ACTION_GATT_CONNECTED".equals(action)) {
            ness.arg1 = 7;
        } else if ("com.heart.ble.ACTION_GATT_SERVICES_DISCOVERED".equals(action)) {
            ness.arg1 = 8;
        }

        this.handler.sendMessage(ness);
    }
}
