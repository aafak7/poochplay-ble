package com.amit.poochplayble;

import android.bluetooth.BluetoothDevice;

import com.clj.fastble.data.BleDevice;

import java.util.List;

public interface BleCallBacks {
    void scanStart();
    void scanEnd();
    void getDeviceList(List<BluetoothDevice> bluetoothDeviceList);
    void getDevicePercentage(String batteryPercentage);

    void getTotalCalories(String totalCalories);
    void getTotalSteps(String totalSteps);

    void startConnect();
    void connectFailed(BluetoothDevice device);
    void connectSuccess(BleDevice bleDevice);
}
