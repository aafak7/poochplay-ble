package com.amit.poochplayble;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleReadCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;

import java.util.ArrayList;
import java.util.List;

import static com.amit.poochplayble.Constant.STEPS_CHARACTERISTICS;
import static com.amit.poochplayble.Constant.STEPS_SERVICE;

public class DataManager {

    private static DataManager ourInstance = new DataManager();
    private final static String BATTERY_UUID = "0000180f-0000-1000-8000-00805f9b34fb";
    private final static String BATTERY_LEVEL = "00002a19-0000-1000-8000-00805f9b34fb";
    private List<BluetoothDevice> lstDevices = new ArrayList<>();
    private BleCallBacks bleCallBacks;
    static int goalWalk = 0;
    private Application application;
    public static DataManager getInstance() {
        return ourInstance;
    }




    public void setApplication(Application application,BleCallBacks bleCallBacks) {
        this.application = application;
        this.bleCallBacks = bleCallBacks;
    }
    public void deviceDisConnect() {
        BleManager.getInstance().disconnectAllDevice();
    }


    public void deviceConnect(BluetoothDevice device) {
        BleManager.getInstance().connect(String.valueOf(device), new BleGattCallback() {
            @Override
            public void onStartConnect() {
                bleCallBacks.startConnect();
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                bleCallBacks.connectFailed(device);
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {

                bleCallBacks.connectSuccess(bleDevice.getMac());
//                tvScanning.setText(device.getAddress());
                BleManager.getInstance().read(
                        bleDevice,
                        BATTERY_UUID,
                        BATTERY_LEVEL,
                        new BleReadCallback() {
                            @Override
                            public void onReadSuccess(byte[] data) {
                                bleCallBacks.getDevicePercentage(String.valueOf(data[0]) + "%");
//                                tvBatteryPercentage.setText("Battery - " + String.valueOf(data[0]) + "%");

                                BleManager.getInstance().notify(
                                        bleDevice,
                                        STEPS_SERVICE,
                                        STEPS_CHARACTERISTICS,
                                        new BleNotifyCallback() {
                                            @Override
                                            public void onNotifySuccess() {
                                            }

                                            @Override
                                            public void onNotifyFailure(BleException exception) {
                                            }

                                            @Override
                                            public void onCharacteristicChanged(byte[] data) {
                                                if (data != null && data.length > 0) {
                                                    StringBuilder stringBuilder = new StringBuilder(data.length);

                                                    for (byte byteChar : data) {
                                                        stringBuilder.append(String.format("%02x ", byteChar));
                                                    }
                                                    bleCallBacks.getDeviceData(stringBuilder.toString());
                                                    poochPlayData(stringBuilder);

                                                }

                                            }
                                        });
                            }

                            @Override
                            public void onReadFailure(BleException exception) {

                            }
                        });
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {

            }
        });
    }
    protected void poochPlayData(StringBuilder stringBuilder) {
        BlueToothActivity blueToothActivity = new BlueToothActivity(bleCallBacks);
        blueToothActivity.setData(stringBuilder.toString());
    }

    public void scan() {
        BleManager.getInstance().init(application);

        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(1, 5000)
                .setSplitWriteNum(20)
                .setConnectOverTime(10000)
                .setOperateTimeout(5000);

        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                .setDeviceName(true, "PoochPlay")
                .setAutoConnect(true)
                .setScanTimeOut(10000)
                .build();
        BleManager.getInstance().initScanRule(scanRuleConfig);

        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
                bleCallBacks.scanStart();
                lstDevices = new ArrayList<>();

            }

            @Override
            public void onScanning(BleDevice bleDevice) {
                if(!lstDevices.contains(bleDevice.getDevice()))
                lstDevices.add(bleDevice.getDevice());
                bleCallBacks.getDeviceList(lstDevices);
            }

            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {
                bleCallBacks.scanEnd();
            }
        });
    }
}
