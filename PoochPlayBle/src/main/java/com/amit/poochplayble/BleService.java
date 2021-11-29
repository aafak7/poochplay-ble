package com.amit.poochplayble;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.List;
import java.util.UUID;

@SuppressLint({"NewApi"})
public class BleService extends Service {
    public static final String TAG = BleService.class.getSimpleName();
    public BluetoothManager mBluetoothManager;
    public BluetoothAdapter mBluetoothAdapter;
    public String mBluetoothDeviceAddress;
    public BluetoothGatt mBluetoothGatt;
    public int mConnectionState = 0;
    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_CONNECTED = 2;
    String value;
    public static final String ACTION_GATT_CONNECTED = "com.wrist.ble.ACTION_GATT_CONNECTED";
    public static final String ACTION_GATT_DISCONNECTED = "com.wrist.blei.ACTION_GATT_DISCONNECTED";
    public static final String ACTION_GATT_SERVICES_DISCOVERED = "com.wrist.ble.ACTION_GATT_SERVICES_DISCOVERED";
    public static final String ACTION_DATA_AVAILABLE = "com.wrist.ble.ACTION_DATA_AVAILABLE";
    public static final String EXTRA_DATA = "com.wrist.ble.ui.EXTRA_DATA";
    public static final UUID READ_UUID;
    public static final UUID NOTIFY_UUID;
    public static final UUID heart_UUID;
    public static final UUID WRITE_UUID;
    public static final UUID SERVER_UUID;
    public static final UUID WRITE_UUID_NEW;
    public static final UUID SERVER_UUID_NEW;
    public static final UUID READ_UUID_NEW;
    public final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.e("ly", "onConnectionStateChange" + newState);
            String intentAction;
            if (newState == 2) {
                intentAction = "com.wrist.ble.ACTION_GATT_CONNECTED";
                BleService.this.mConnectionState = 2;
                BleService.this.broadcastUpdate(intentAction);
                Log.i("ly", "发广播ACTION_GATT_CONNECTED");
                Log.i(BleService.TAG, "Connected to GATT server.");
                Log.i(BleService.TAG, "Attempting to start service discovery:" + BleService.this.mBluetoothGatt.discoverServices());
            } else if (newState == 0) {
                intentAction = "com.wrist.blei.ACTION_GATT_DISCONNECTED";
                BleService.this.mConnectionState = 0;
                Log.i(BleService.TAG, "Disconnected from GATT server.");
                BleService.this.broadcastUpdate(intentAction);
            }

        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == 0) {
                BleService.this.broadcastUpdate("com.wrist.ble.ACTION_GATT_SERVICES_DISCOVERED");
            } else {
                Log.w(BleService.TAG, "onServicesDiscovered received: " + status);
            }

        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == 0) {
                BleService.this.broadcastUpdate("com.wrist.ble.ACTION_DATA_AVAILABLE", characteristic);
            }

        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            BleService.this.broadcastUpdate("com.wrist.ble.ACTION_DATA_AVAILABLE", characteristic);
        }
    };
    public IBinder mBinder = new LocalBinder();

    static {
        READ_UUID = UUID.fromString(SampleGattAttributes.BRACELETREAD_UUID);
        NOTIFY_UUID = UUID.fromString(SampleGattAttributes.BRACELETNOTIFY_UUID);
        heart_UUID = UUID.fromString(SampleGattAttributes.HEARTREAD_UUID);
        WRITE_UUID = UUID.fromString(SampleGattAttributes.BRACELETWRITE_UUID);
        SERVER_UUID = UUID.fromString(SampleGattAttributes.BRACELETSERVER_UUID);
        WRITE_UUID_NEW = UUID.fromString(SampleGattAttributes.NOTIFY_WRITE_UUID);
        SERVER_UUID_NEW = UUID.fromString(SampleGattAttributes.SERVER_UUID);
        READ_UUID_NEW = UUID.fromString(SampleGattAttributes.NOTIFY_UUID);
    }

    public BleService() {
    }

    public void broadcastUpdate(String action) {
        Intent intent = new Intent(action);
        this.sendBroadcast(intent);
    }

    public void broadcastUpdate(String action, BluetoothGattCharacteristic characteristic) {
        Intent intent = new Intent(action);
        byte[] data = characteristic.getValue();
        if (data != null && data.length > 0) {
            StringBuilder stringBuilder = new StringBuilder(data.length);
            byte[] var9 = data;
            int var8 = data.length;

            for(int var7 = 0; var7 < var8; ++var7) {
                byte byteChar = var9[var7];
                stringBuilder.append(String.format("%02x ", byteChar));
            }

            intent.putExtra("com.wrist.ble.ui.EXTRA_DATA", stringBuilder.toString());
            intent.putExtra("uuid", characteristic.getUuid().toString());
        }

        this.sendBroadcast(intent);
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public boolean onUnbind(Intent intent) {
        this.close();
        return super.onUnbind(intent);
    }

    public boolean initialize() {
        if (this.mBluetoothManager == null) {
            this.mBluetoothManager = (BluetoothManager)this.getSystemService(BLUETOOTH_SERVICE);
            if (this.mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        this.mBluetoothAdapter = this.mBluetoothManager.getAdapter();
        if (this.mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        } else {
            return true;
        }
    }

    public boolean connect(String address) {
        if (this.mBluetoothAdapter != null && address != null) {
            if (this.mBluetoothDeviceAddress != null && address.equals(this.mBluetoothDeviceAddress) && this.mBluetoothGatt != null) {
                Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
                if (this.mBluetoothGatt.connect()) {
                    this.mConnectionState = 1;
                    return true;
                } else {
                    return false;
                }
            } else {
                BluetoothDevice device = this.mBluetoothAdapter.getRemoteDevice(address);
                if (device == null) {
                    Log.w(TAG, "Device not found.  Unable to connect.");
                    return false;
                } else {
                    this.mBluetoothGatt = device.connectGatt(this, false, this.mGattCallback);
                    Log.d(TAG, "Trying to create a new connection.");
                    this.mBluetoothDeviceAddress = address;
                    this.mConnectionState = 1;
                    Log.e("ly", "connect");
                    return true;
                }
            }
        } else {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }
    }

    public void disconnect() {
        if (this.mBluetoothAdapter != null && this.mBluetoothGatt != null) {
            Log.e("ly", "disconnectaddress" + this.mBluetoothGatt.getDevice().getAddress());
            this.mBluetoothGatt.disconnect();
            Log.e("ly", "disconnect");
        } else {
            Log.w(TAG, "BluetoothAdapter not initialized");
        }
    }

    public void close() {
        if (this.mBluetoothGatt != null) {
            Log.e("ly", "close+++address" + this.mBluetoothGatt.getDevice().getAddress());
            this.mBluetoothGatt.close();
            this.mBluetoothGatt = null;
        }
    }

    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (this.mBluetoothAdapter != null && this.mBluetoothGatt != null) {
            this.mBluetoothGatt.readCharacteristic(characteristic);
        } else {
            Log.w(TAG, "BluetoothAdapter not initialized");
        }
    }

    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
        Log.e("ly", "sssss");
        if (this.mBluetoothAdapter != null && this.mBluetoothGatt != null) {
            BluetoothGattDescriptor descriptor;
            if (enabled) {
                this.mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
                if (READ_UUID.equals(characteristic.getUuid())) {
                    descriptor = characteristic.getDescriptor(UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    boolean ret = this.mBluetoothGatt.writeDescriptor(descriptor);
                    Log.e("ly", String.valueOf(ret));
                }
            } else {
                this.mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
                if (READ_UUID.equals(characteristic.getUuid())) {
                    descriptor = characteristic.getDescriptor(UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
                    descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                    this.mBluetoothGatt.writeDescriptor(descriptor);
                }
            }

        } else {
            Log.w(TAG, "BluetoothAdapter not initialized");
        }
    }

    public boolean setCharacteristicNotification1(BluetoothGattCharacteristic characteristic, boolean enabled) {
        boolean ret = false;
        if (this.mBluetoothAdapter != null && this.mBluetoothGatt != null) {
            if (enabled) {
                this.mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
                if (NOTIFY_UUID.equals(characteristic.getUuid())) {
                    BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    ret = this.mBluetoothGatt.writeDescriptor(descriptor);
                }
            } else {
                this.mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
                if (NOTIFY_UUID.equals(characteristic.getUuid())) {
                    BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
                    descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                    ret = this.mBluetoothGatt.writeDescriptor(descriptor);
                }
            }

            return ret;
        } else {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }
    }

    public boolean setCharacteristicNotification2(BluetoothGattCharacteristic characteristic, boolean enabled) {
        boolean ret = false;
        if (this.mBluetoothAdapter != null && this.mBluetoothGatt != null) {
            BluetoothGattDescriptor descriptor;
            if (enabled) {
                this.mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
                if (heart_UUID.equals(characteristic.getUuid())) {
                    descriptor = characteristic.getDescriptor(UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    ret = this.mBluetoothGatt.writeDescriptor(descriptor);
                }
            } else {
                this.mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
                if (heart_UUID.equals(characteristic.getUuid())) {
                    descriptor = characteristic.getDescriptor(UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
                    descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                    ret = this.mBluetoothGatt.writeDescriptor(descriptor);
                }
            }

            return ret;
        } else {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }
    }

    public boolean setCharacteristicNotification_now(BluetoothGattCharacteristic characteristic, boolean enabled) {
        boolean ret = false;
        if (this.mBluetoothAdapter != null && this.mBluetoothGatt != null) {
            this.mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
            if (READ_UUID_NEW.equals(characteristic.getUuid())) {
                BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                ret = this.mBluetoothGatt.writeDescriptor(descriptor);
            }

            return ret;
        } else {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }
    }

    public boolean setAppointCharacteristicNotification(BluetoothGattCharacteristic characteristic, String ServicesUuid, String NotifyUuid, boolean enabled) {
        Log.w("xxy", "setAppointCharacteristicNotification");
        boolean ret = false;
        if (this.mBluetoothAdapter != null && this.mBluetoothGatt != null) {
            BluetoothGattDescriptor descriptor;
            if (enabled) {
                this.mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
                if (UUID.fromString(NotifyUuid).equals(characteristic.getUuid())) {
                    descriptor = characteristic.getDescriptor(UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    ret = this.mBluetoothGatt.writeDescriptor(descriptor);
                    Log.w("xxy", "ret+++" + ret);
                }
            } else {
                this.mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
                if (UUID.fromString(NotifyUuid).equals(characteristic.getUuid())) {
                    descriptor = characteristic.getDescriptor(UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
                    descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                    ret = this.mBluetoothGatt.writeDescriptor(descriptor);
                }
            }

            return ret;
        } else {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }
    }

    public List<BluetoothGattService> getSupportedGattServices() {
        return this.mBluetoothGatt == null ? null : this.mBluetoothGatt.getServices();
    }

    public Boolean write(byte[] data) {
        if (this.mBluetoothGatt == null) {
            Log.e(TAG, "不存在蓝牙服务");
            return false;
        } else {
            BluetoothGattService gattServer = this.mBluetoothGatt.getService(SERVER_UUID);
            if (gattServer == null) {
                Log.e(TAG, "不存在服务");
                return false;
            } else {
                BluetoothGattCharacteristic Characteristic = null;
                Characteristic = gattServer.getCharacteristic(WRITE_UUID);
                if (Characteristic == null) {
                    Log.e(TAG, "不存在发送服务");
                    return false;
                } else {
                    Characteristic.setValue(data);
                    return this.mBluetoothGatt.writeCharacteristic(Characteristic);
                }
            }
        }
    }

    public Boolean write_notify(byte[] data) {
        if (this.mBluetoothGatt == null) {
            Log.e(TAG, "不存在蓝牙服务");
            return false;
        } else {
            BluetoothGattService gattServer = this.mBluetoothGatt.getService(SERVER_UUID_NEW);
            if (gattServer == null) {
                Log.e(TAG, "不存在服务");
                return false;
            } else {
                BluetoothGattCharacteristic Characteristic = null;
                Characteristic = gattServer.getCharacteristic(WRITE_UUID_NEW);
                if (Characteristic == null) {
                    Log.e(TAG, "不存在发送服务");
                    return false;
                } else {
                    Characteristic.setValue(data);
                    boolean aa = this.mBluetoothGatt.writeCharacteristic(Characteristic);
                    return aa;
                }
            }
        }
    }

    public Boolean write_call(byte[] data) {
        if (this.mBluetoothGatt == null) {
            Log.e(TAG, "不存在蓝牙服务");
            return false;
        } else {
            BluetoothGattService gattServer = this.mBluetoothGatt.getService(UUID.fromString(SampleGattAttributes.SERVER_UUID));
            if (gattServer == null) {
                Log.e(TAG, "不存在服务");
                return false;
            } else {
                BluetoothGattCharacteristic Characteristic = null;
                Characteristic = gattServer.getCharacteristic(UUID.fromString(SampleGattAttributes.WRITE_CALL_UUID));
                if (Characteristic == null) {
                    Log.e(TAG, "不存在发送服务");
                    return false;
                } else {
                    Characteristic.setValue(data);
                    return this.mBluetoothGatt.writeCharacteristic(Characteristic);
                }
            }
        }
    }

    public Boolean write_Appoint(String ServicesUuid, String NotifyUuid, byte[] data) {
        if (this.mBluetoothGatt == null) {
            Log.e(TAG, "不存在蓝牙服务");
            return false;
        } else {
            BluetoothGattService gattServer = this.mBluetoothGatt.getService(UUID.fromString(ServicesUuid));
            if (gattServer == null) {
                Log.e(TAG, "不存在服务");
                return false;
            } else {
                BluetoothGattCharacteristic Characteristic = null;
                Characteristic = gattServer.getCharacteristic(UUID.fromString(NotifyUuid));
                if (Characteristic == null) {
                    Log.e(TAG, "不存在发送服务");
                    return false;
                } else {
                    Characteristic.setValue(data);
                    return this.mBluetoothGatt.writeCharacteristic(Characteristic);
                }
            }
        }
    }

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        public BleService getService() {
            return BleService.this;
        }
    }
}
