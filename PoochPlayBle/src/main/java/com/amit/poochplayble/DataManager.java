package com.amit.poochplayble;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.os.Message;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleReadCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
    private BluetoothDevice device;
    private BluetoothGatt bluetoothGatt;
    public static DataManager getInstance() {
        return ourInstance;
    }


    void setData(String value) {
        String tt = value.replace(" ", "").toUpperCase();
        byte[] data = hexStringToByte(tt);
        int one = getLowBit4(data[0]);
/*
        if (one == 8) {
            collectHistoryData(data, value.substring(6, value.length() - 2), mBluetoothLeService);
        }
*/

        int bb;
        int gg;
        int j;
        int ii;
        int checksum;
        int first;
        int shuju;
        if (data[0] == 1) {
            byte[] newdata = new byte[12];
            bb = 0;
            newdata[0] = -31;
            newdata[1] = 0;
            gg = Array.UserBodyInfo.weight;
            newdata[2] = (byte) (gg >> 8);
            newdata[3] = (byte) gg;
            j = Array.UserBodyInfo.age;
            newdata[4] = (byte) j;
            ii = Array.UserBodyInfo.height;
            newdata[5] = (byte) ii;
            newdata[6] = (byte) (ii / 7);
            checksum = Array.UserBodyInfo.sex;
            if (checksum == 1) {
                newdata[7] = 1;
            } else {
                newdata[7] = 0;
            }

            goalWalk = Array.UserBodyInfo.target;
            newdata[8] = (byte) (goalWalk >> 16);
            newdata[9] = (byte) (goalWalk >> 8);
            newdata[10] = (byte) goalWalk;

            for (first = 0; first <= newdata.length - 2; ++first) {
                shuju = newdata[first];
                if (shuju < 0) {
                    shuju += 256;
                }

                bb += shuju;
            }

            newdata[11] = (byte) (bb & 255);
            //        boolean var40 = mBluetoothLeService.write(newdata);
        }

        int wendrtmin;
        checksum = 0;
        if ((data[0] & 15) == 2) {
            checksum = 0;
            Calendar calendar = Calendar.getInstance();
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            long localTime = calendar.getTimeInMillis() / 1000L + (long) (cal.get(15) / 1000);
            long time4 = localTime / 16777216L;
            long time3 = localTime % 16777216L / 65536L;
            long time2 = localTime % 65536L / 256L;
            long time1 = localTime % 256L;
            byte[] newdata = new byte[]{-30, 1, (byte) ((int) time4), (byte) ((int) time3), (byte) ((int) time2), (byte) ((int) time1), 0};

            for (wendrtmin = 0; wendrtmin <= newdata.length - 2; ++wendrtmin) {
                bb = newdata[wendrtmin];
                if (bb < 0) {
                    bb += 256;
                }

                checksum += bb;
            }

            newdata[6] = (byte) (checksum & 255);
//            boolean var48 = mBluetoothLeService.write(newdata);
        }

        if ((data[0] & 15) == 3) {
            checksum = data[5] & 255;
            bb = data[6] & 255;
            shuju = data[7] & 255;
            Array.totaldata = shuju + (bb << 8) + (checksum << 16);
            gg = data[11] & 255;
            j = data[12] & 255;
            ii = data[13] & 255;
            Array.totalcalorie = (ii + (j << 8) + (gg << 16)) / 10;
            String xx = "E0 02 00 E2";
            String ss = xx.toString().replace(" ", "");
            byte[] data1 = hexStringToByte(ss);
            //mBluetoothLeService.write(data1);
        }

        if ((data[0] & 15) == 4 && (data[1] & 15) == 1) {
            Array.liststring.add(value);
        }

        if ((data[0] & 15) == 4 && (data[1] & 15) == 2) {
            Array.liststring.add(value);
        }

        byte[] newdata1;
        boolean var34;
        if ((data[0] & 15) == 4 && (data[1] & 15) == 3) {
            checksum = 0;
            bb = (data[0] & 240) >> 4;
            newdata1 = new byte[]{-32, (byte) bb, 0, 0};

            for (j = 0; j <= newdata1.length - 2; ++j) {
                shuju = newdata1[j];
                if (shuju < 0) {
                    shuju += 256;
                }

                checksum += shuju;
            }

            newdata1[3] = (byte) (checksum & 255);
//            var34 = mBluetoothLeService.write(newdata1);
        }

        if ((data[0] & 15) == 5 && (data[1] & 15) == 1) {
            Array.listsleepstring.add(value);
        }

        if ((data[0] & 15) == 5 && (data[1] & 15) == 3) {
            checksum = 0;
            bb = (data[0] & 240) >> 4;
            newdata1 = new byte[]{-32, (byte) bb, 0, 0};

            for (j = 0; j <= newdata1.length - 2; ++j) {
                shuju = newdata1[j];
                if (shuju < 0) {
                    shuju += 256;
                }

                checksum += shuju;
            }

            newdata1[3] = (byte) (checksum & 255);
//            var34 = mBluetoothLeService.write(newdata1);
        }

        if ((data[0] & 15) == 7) {
            int aa = data[2];
            int ff = data[3];
            bb = data[4];
            int cc = data[5];
            int dd = data[6];
            int ee = data[7];
            Array.starthour = String.valueOf(aa);
            Array.startmin = String.valueOf(ff);
            Array.peaceendhour = String.valueOf(bb);
            Array.peaceendmin = String.valueOf(cc);
            Array.weekendhour = String.valueOf(dd);
            Array.weekendmin = String.valueOf(ee);
            checksum = 0;
            first = (data[0] & 240) >> 4;
            newdata1 = new byte[20];
            newdata1[0] = -28;
            newdata1[1] = (byte) first;
            newdata1[2] = 1;
            int starthour = UserSleepInfo.startDetectSleepHH;
            int startmin = UserSleepInfo.startDetectSleepMM;
            newdata1[3] = (byte) starthour;
            newdata1[4] = (byte) startmin;
            int pendhour = UserSleepInfo.wdStopDetectSleepHH;
            int pendrtmin = UserSleepInfo.wdStopDetectSleepMM;
            newdata1[5] = (byte) pendhour;
            newdata1[6] = (byte) pendrtmin;
            int wendhour = UserSleepInfo.rdStopDetectSleepHH;
            wendrtmin = UserSleepInfo.rdStopDetectSleepMM;
            newdata1[7] = (byte) wendhour;
            newdata1[8] = (byte) wendrtmin;
            newdata1[9] = 10;
            newdata1[10] = 40;
            newdata1[11] = 8;
            newdata1[12] = 48;
            newdata1[13] = 0;
            newdata1[14] = 0;
            newdata1[15] = 0;
            newdata1[16] = 0;
            newdata1[17] = 0;
            newdata1[18] = 0;

            for (j = 0; j <= newdata1.length - 2; ++j) {
                shuju = newdata1[j];
                if (shuju < 0) {
                    shuju += 256;
                }

                checksum += shuju;
            }

            newdata1[19] = (byte) (checksum & 255);
//            boolean var49 = mBluetoothLeService.write(newdata1);
        }

        if ((data[0] & 15) == 6) {
            if (Constant.RtNotify == 2) {
                Constant.RtNotify = 1;
//                SysHanderManager.setHandler(handler);
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = 34;
                //              SysHanderManager.SendMsg(msg);
    /*            if (BleState.bleOldProtocolSyncNrtStatus == 61) {
                    SysSendMsg.StartTimerMsg(1, 32, 2000, false);
                }
    */
            }

            checksum = 0;
            bb = (data[0] & 240) >> 4;
            newdata1 = new byte[]{-32, (byte) bb, 0, 0};

            for (j = 0; j <= newdata1.length - 2; ++j) {
                shuju = newdata1[j];
                if (shuju < 0) {
                    shuju += 256;
                }

                checksum += shuju;
            }

            newdata1[3] = (byte) (checksum & 255);
            //var34 = mBluetoothLeService.write(newdata1);
        }

        if (((data[0] & 255) == 209 || (data[1] & 255) == 12) && ((data[0] & 255) == 209 || (data[1] & 255) == 16) && ((data[0] & 255) == 209 || (data[1] & 255) == 13 || (data[2] & 255) == 1)) {
            checksum = data[2] & 255;
            bb = data[3] & 255;
            shuju = data[4] & 255;
            Array.RtCtrlData.totalSteps = shuju + (bb << 8) + (checksum << 16);
            gg = data[8] & 255;
            j = data[9] & 255;
            ii = data[10] & 255;
            Array.RtCtrlData.totalCalorie = (float) ((ii + (j << 8) + (gg << 16)) / 10);
            bleCallBacks.getTotalSteps(""+Array.RtCtrlData.totalSteps);
            bleCallBacks.getTotalCalories(""+Array.RtCtrlData.totalCalorie);

        }

    }

    private static byte[] hexStringToByte(String hex) {
        int len = hex.length() / 2;
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();

        for (int i = 0; i < len; ++i) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }

        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    public static int getLowBit4(byte b) {
        int low = b & 15;
        return low;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public void setBleCallBacks(BleCallBacks bleCallBacks) {
        this.bleCallBacks = bleCallBacks;
    }
    private static class UserSleepInfo {
        public static int enableAutoSleepWakeup = 1;
        public static int startDetectSleepHH = 21;
        public static int startDetectSleepMM = 30;
        public static int wdStopDetectSleepHH = 8;
        public static int wdStopDetectSleepMM = 0;
        public static int rdStopDetectSleepHH = 9;
        public static int rdStopDetectSleepMM = 30;
        public static int autoSleepNoMoveMinCntsThreshold = 10;
        public static int autoWakeupActiveIndexValThreshold = 40;

        public UserSleepInfo() {
        }
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
                bluetoothGatt = gatt;
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

                                                    setData(stringBuilder.toString());
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
