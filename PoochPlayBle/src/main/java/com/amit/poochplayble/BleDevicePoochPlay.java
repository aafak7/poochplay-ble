package com.amit.poochplayble;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;

import com.clj.fastble.data.BleDevice;

public class BleDevicePoochPlay extends BleDevice {
    public BleDevicePoochPlay(BluetoothDevice device) {
        super(device);
    }

    public BleDevicePoochPlay(BluetoothDevice device, int rssi, byte[] scanRecord, long timestampNanos) {
        super(device, rssi, scanRecord, timestampNanos);
    }

    protected BleDevicePoochPlay(Parcel in) {
        super(in);
    }

    @Override
    public String getMac() {
        return super.getMac();
    }
}
