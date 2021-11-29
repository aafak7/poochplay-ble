package com.amit.poochplayble;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.util.Log;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class GattServices {
    public BleService bluetoothLeService;

    public int decideProtocol(List<BluetoothGattService> gattServices) {
        int new_or_old = 0;
        if (gattServices.size() > 0) {
            UUID NEW_SERVER_UUID = UUID.fromString(SampleGattAttributes.SERVER_UUID);
            UUID NEW_NOTIFY_UUID = UUID.fromString(SampleGattAttributes.NOTIFY_UUID);
            UUID OLD_SERVER_UUID = UUID.fromString(SampleGattAttributes.BRACELETSERVER_UUID);
            UUID OLD_NOTIFY_UUID = UUID.fromString(SampleGattAttributes.BRACELETNOTIFY_UUID);
            Iterator var8 = gattServices.iterator();

            while(true) {
                while(var8.hasNext()) {
                    BluetoothGattService gattService = (BluetoothGattService)var8.next();
                    List gattCharacteristics;
                    BluetoothGattCharacteristic gattCharacteristic;
                    Iterator var11;
                    if (gattService.getUuid().equals(NEW_SERVER_UUID)) {
                        gattCharacteristics = gattService.getCharacteristics();
                        var11 = gattCharacteristics.iterator();

                        while(var11.hasNext()) {
                            gattCharacteristic = (BluetoothGattCharacteristic)var11.next();
                            if (gattCharacteristic.getUuid().equals(NEW_NOTIFY_UUID)) {
                                Log.e("moofit", "decideProtocol: 新协议");
                                new_or_old = 1;
                            }
                        }
                    } else if (gattService.getUuid().equals(OLD_SERVER_UUID)) {
                        gattCharacteristics = gattService.getCharacteristics();
                        var11 = gattCharacteristics.iterator();

                        while(var11.hasNext()) {
                            gattCharacteristic = (BluetoothGattCharacteristic)var11.next();
                            if (gattCharacteristic.getUuid().equals(OLD_NOTIFY_UUID)) {
                                Log.e("moofit", "decideProtocol: 旧协议");
                                new_or_old = 2;
                            }
                        }
                    }
                }

                return new_or_old;
            }
        } else {
            return new_or_old;
        }
    }

    public void SetGattServices_old(List<BluetoothGattService> gattServices) {
        Log.e("xxy", "+++++++");
        if (gattServices == null) {
            Log.e("xxy", "gattServices == null");
        } else {
            UUID READ_UUID = UUID.fromString(SampleGattAttributes.BRACELETREAD_UUID);
            UUID SERVER_UUID = UUID.fromString(SampleGattAttributes.BRACELETSERVER_UUID);
            Iterator var5 = gattServices.iterator();

            while(true) {
                BluetoothGattService gattService;
                do {
                    if (!var5.hasNext()) {
                        return;
                    }

                    gattService = (BluetoothGattService)var5.next();
                } while(!gattService.getUuid().equals(SERVER_UUID));

                List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                Iterator var8 = gattCharacteristics.iterator();

                while(var8.hasNext()) {
                    BluetoothGattCharacteristic gattCharacteristic = (BluetoothGattCharacteristic)var8.next();
                    if (gattCharacteristic.getUuid().equals(READ_UUID)) {
                        Log.e("xxy", "READ_UUID");
                        int charaProp = gattCharacteristic.getProperties();
                        if ((charaProp | 16) > 0) {
                            this.bluetoothLeService.setCharacteristicNotification(gattCharacteristic, true);
                        }
                    }
                }
            }
        }
    }

    public void SetGattServicesfalse_old(List<BluetoothGattService> gattServices) {
        if (gattServices != null) {
            UUID READ_UUID = UUID.fromString(SampleGattAttributes.BRACELETREAD_UUID);
            UUID SERVER_UUID = UUID.fromString(SampleGattAttributes.BRACELETSERVER_UUID);
            Iterator var5 = gattServices.iterator();

            while(true) {
                BluetoothGattService gattService;
                do {
                    if (!var5.hasNext()) {
                        return;
                    }

                    gattService = (BluetoothGattService)var5.next();
                } while(!gattService.getUuid().equals(SERVER_UUID));

                List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                Iterator var8 = gattCharacteristics.iterator();

                while(var8.hasNext()) {
                    BluetoothGattCharacteristic gattCharacteristic = (BluetoothGattCharacteristic)var8.next();
                    if (gattCharacteristic.getUuid().equals(READ_UUID)) {
                        int charaProp = gattCharacteristic.getProperties();
                        if ((charaProp | 16) > 0) {
                            this.bluetoothLeService.setCharacteristicNotification(gattCharacteristic, false);
                        }
                    }
                }
            }
        }
    }

    public boolean SetnotifyGattServices_old(List<BluetoothGattService> gattServices) {
        if (gattServices == null) {
            return false;
        } else {
            boolean issuccess = false;
            UUID READ_UUID = UUID.fromString(SampleGattAttributes.BRACELETNOTIFY_UUID);
            UUID SERVER_UUID = UUID.fromString(SampleGattAttributes.BRACELETSERVER_UUID);
            Iterator var6 = gattServices.iterator();

            while(true) {
                BluetoothGattService gattService;
                do {
                    if (!var6.hasNext()) {
                        return issuccess;
                    }

                    gattService = (BluetoothGattService)var6.next();
                } while(!gattService.getUuid().equals(SERVER_UUID));

                List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                Iterator var9 = gattCharacteristics.iterator();

                while(var9.hasNext()) {
                    BluetoothGattCharacteristic gattCharacteristic = (BluetoothGattCharacteristic)var9.next();
                    if (gattCharacteristic.getUuid().equals(READ_UUID)) {
                        int charaProp = gattCharacteristic.getProperties();
                        if ((charaProp | 16) > 0) {
                            issuccess = this.bluetoothLeService.setCharacteristicNotification1(gattCharacteristic, true);
                        }
                    }
                }
            }
        }
    }

    public void SetnotifyGattServicesfalse_old(List<BluetoothGattService> gattServices) {
        if (gattServices != null) {
            UUID READ_UUID = UUID.fromString(SampleGattAttributes.BRACELETNOTIFY_UUID);
            UUID SERVER_UUID = UUID.fromString(SampleGattAttributes.BRACELETSERVER_UUID);
            Iterator var5 = gattServices.iterator();

            while(true) {
                BluetoothGattService gattService;
                do {
                    if (!var5.hasNext()) {
                        return;
                    }

                    gattService = (BluetoothGattService)var5.next();
                } while(!gattService.getUuid().equals(SERVER_UUID));

                List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                Iterator var8 = gattCharacteristics.iterator();

                while(var8.hasNext()) {
                    BluetoothGattCharacteristic gattCharacteristic = (BluetoothGattCharacteristic)var8.next();
                    if (gattCharacteristic.getUuid().equals(READ_UUID)) {
                        int charaProp = gattCharacteristic.getProperties();
                        if ((charaProp | 16) > 0) {
                            this.bluetoothLeService.setCharacteristicNotification1(gattCharacteristic, false);
                        }
                    }
                }
            }
        }
    }

    public boolean SetheartGattServices(List<BluetoothGattService> gattServices) {
        boolean issuccess = false;
        if (gattServices == null) {
            Log.e("ly", "gattServices == null");
            return false;
        } else {
            UUID READ_UUID = UUID.fromString(SampleGattAttributes.HEARTREAD_UUID);
            UUID SERVER_UUID = UUID.fromString(SampleGattAttributes.HEARTSERVER_UUID);
            Iterator var6 = gattServices.iterator();

            while(true) {
                BluetoothGattService gattService;
                do {
                    if (!var6.hasNext()) {
                        return issuccess;
                    }

                    gattService = (BluetoothGattService)var6.next();
                } while(!gattService.getUuid().equals(SERVER_UUID));

                List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                Iterator var9 = gattCharacteristics.iterator();

                while(var9.hasNext()) {
                    BluetoothGattCharacteristic gattCharacteristic = (BluetoothGattCharacteristic)var9.next();
                    if (gattCharacteristic.getUuid().equals(READ_UUID)) {
                        int charaProp = gattCharacteristic.getProperties();
                        if ((charaProp | 16) > 0) {
                            issuccess = this.bluetoothLeService.setCharacteristicNotification2(gattCharacteristic, true);
                        }
                    }
                }
            }
        }
    }

    public boolean SetheartGattServicesfalse(List<BluetoothGattService> gattServices) {
        boolean issuccess = false;
        if (gattServices == null) {
            Log.e("ly", "gattServices == null");
            return false;
        } else {
            UUID READ_UUID = UUID.fromString(SampleGattAttributes.HEARTREAD_UUID);
            UUID SERVER_UUID = UUID.fromString(SampleGattAttributes.HEARTSERVER_UUID);
            Iterator var6 = gattServices.iterator();

            while(true) {
                BluetoothGattService gattService;
                do {
                    if (!var6.hasNext()) {
                        return issuccess;
                    }

                    gattService = (BluetoothGattService)var6.next();
                } while(!gattService.getUuid().equals(SERVER_UUID));

                List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                Iterator var9 = gattCharacteristics.iterator();

                while(var9.hasNext()) {
                    BluetoothGattCharacteristic gattCharacteristic = (BluetoothGattCharacteristic)var9.next();
                    if (gattCharacteristic.getUuid().equals(READ_UUID)) {
                        int charaProp = gattCharacteristic.getProperties();
                        if ((charaProp | 16) > 0) {
                            issuccess = this.bluetoothLeService.setCharacteristicNotification2(gattCharacteristic, false);
                        }
                    }
                }
            }
        }
    }

    public boolean OpenAppointCharacteristic(List<BluetoothGattService> gattServices, String ServicesUuid, String NotifyUuid) {
        boolean success = false;
        if (gattServices == null) {
            Log.e("ly", "SetnotifyGattServices++++++gattServices == null");
            return false;
        } else {
            UUID READ_UUID = UUID.fromString(NotifyUuid);
            UUID SERVER_UUID = UUID.fromString(ServicesUuid);
            Iterator var8 = gattServices.iterator();

            while(true) {
                BluetoothGattService gattService;
                do {
                    if (!var8.hasNext()) {
                        return success;
                    }

                    gattService = (BluetoothGattService)var8.next();
                } while(!gattService.getUuid().equals(SERVER_UUID));

                List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                Iterator var11 = gattCharacteristics.iterator();

                while(var11.hasNext()) {
                    BluetoothGattCharacteristic gattCharacteristic = (BluetoothGattCharacteristic)var11.next();
                    if (gattCharacteristic.getUuid().equals(READ_UUID)) {
                        int charaProp = gattCharacteristic.getProperties();
                        if ((charaProp | 16) > 0) {
                            success = this.bluetoothLeService.setAppointCharacteristicNotification(gattCharacteristic, ServicesUuid, NotifyUuid, true);
                        }
                    }
                }
            }
        }
    }

    public boolean CloseAppointCharacteristic(List<BluetoothGattService> gattServices, String ServicesUuid, String NotifyUuid) {
        boolean success = false;
        if (gattServices == null) {
            Log.e("ly", "SetnotifyGattServices++++++gattServices == null");
            return false;
        } else {
            UUID READ_UUID = UUID.fromString(NotifyUuid);
            UUID SERVER_UUID = UUID.fromString(ServicesUuid);
            Iterator var8 = gattServices.iterator();

            while(true) {
                BluetoothGattService gattService;
                do {
                    if (!var8.hasNext()) {
                        return success;
                    }

                    gattService = (BluetoothGattService)var8.next();
                } while(!gattService.getUuid().equals(SERVER_UUID));

                List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                Iterator var11 = gattCharacteristics.iterator();

                while(var11.hasNext()) {
                    BluetoothGattCharacteristic gattCharacteristic = (BluetoothGattCharacteristic)var11.next();
                    if (gattCharacteristic.getUuid().equals(READ_UUID)) {
                        int charaProp = gattCharacteristic.getProperties();
                        if ((charaProp | 16) > 0) {
                            success = this.bluetoothLeService.setAppointCharacteristicNotification(gattCharacteristic, ServicesUuid, NotifyUuid, false);
                        }
                    }
                }
            }
        }
    }

    public boolean SetnotifyGattServices(List<BluetoothGattService> gattServices) {
        boolean success = false;
        if (gattServices == null) {
            return false;
        } else {
            UUID READ_UUID = UUID.fromString(SampleGattAttributes.NOTIFY_UUID);
            UUID SERVER_UUID = UUID.fromString(SampleGattAttributes.SERVER_UUID);
            Iterator var6 = gattServices.iterator();

            while(true) {
                BluetoothGattService gattService;
                do {
                    if (!var6.hasNext()) {
                        return success;
                    }

                    gattService = (BluetoothGattService)var6.next();
                } while(!gattService.getUuid().equals(SERVER_UUID));

                List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                Iterator var9 = gattCharacteristics.iterator();

                while(var9.hasNext()) {
                    BluetoothGattCharacteristic gattCharacteristic = (BluetoothGattCharacteristic)var9.next();
                    if (gattCharacteristic.getUuid().equals(READ_UUID)) {
                        int charaProp = gattCharacteristic.getProperties();
                        if ((charaProp | 16) > 0) {
                            success = this.bluetoothLeService.setCharacteristicNotification_now(gattCharacteristic, true);
                        }
                    }
                }
            }
        }
    }

    public GattServices(BleService mBluetoothLeService) {
        this.bluetoothLeService = mBluetoothLeService;
    }
}
