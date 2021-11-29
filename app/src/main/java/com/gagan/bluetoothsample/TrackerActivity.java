package com.gagan.bluetoothsample;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amit.poochplayble.Array;
import com.amit.poochplayble.BleData;
import com.amit.poochplayble.BleDecodeData;
import com.amit.poochplayble.BleDecodeRtData;
import com.amit.poochplayble.BleHelper;
import com.amit.poochplayble.BleReceiver;
import com.amit.poochplayble.BleService;
import com.amit.poochplayble.BleTransLayer;
import com.amit.poochplayble.Constant;
import com.amit.poochplayble.Funtion;
import com.amit.poochplayble.Nrtanalysis;
import com.amit.poochplayble.ProtocolHanderManager;
import com.amit.poochplayble.R;
import com.amit.poochplayble.SampleGattAttributes;
import com.amit.poochplayble.StepData;
import com.amit.poochplayble.SysHanderManager;
import com.amit.poochplayble.Util;
import com.amit.poochplayble.WaterSetInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TrackerActivity extends AppCompatActivity {


    private final static String TAG = "Tracker";

    private List<BluetoothDevice> lstDevices = new ArrayList<>();
    private List<String> lstDevicesName = new ArrayList<>();
    private ListView listView;
    private String deviseList = "";
    private TextView tvScanning, tvBatteryPercentage, tvSteps;
    private Button btnScan;
    static int goalWalk = 0;

    private BluetoothDevice device;
    private BluetoothGatt bluetoothGatt;

    private final static String BATTERY_UUID = "0000180f-0000-1000-8000-00805f9b34fb";
    private final static String BATTERY_LEVEL = "00002a19-0000-1000-8000-00805f9b34fb";

    // new code
    public BleService mBluetoothLeService;
    public BroadcastReceiver broadcastReceiver;
    public Funtion funtion = new Funtion();
    private final Handler getOldData = new Handler();
    private final Handler countRealSteps = new Handler();


    public static String mDeviceAddress;

    private BluetoothDevice mDevice;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;


    private BluetoothLeScanner mLEScanner;
    private ScanSettings settings;
    private List<ScanFilter> filters;
    private static final int REQUEST_ENABLE_BT = 1;

    private Handler mHandler;
    private static final long SCAN_PERIOD = 10000;

    public ArrayList<String> mLeDevices = new ArrayList<String>();
    public ArrayList<BluetoothDevice> mLeDevicesConnected = new ArrayList<BluetoothDevice>();



    private Runnable countRealStepsHandler = new Runnable() {
        @Override
        public void run() {
            try {
                funtion.Controlrtdata(1);
                getOldData.postDelayed(getOldDataHandler, 3000);
            } catch (Exception e) {
            }
            //countRealSteps.postDelayed(this, 1000);
        }
    };

    private Runnable getOldDataHandler = new Runnable() {
        @Override
        public void run() {
            try {
                funtion.getnrtdata();
            } catch (Exception e) {
            }
            // countRealSteps.postDelayed(this, 1000);
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, makeGattUpdateIntentFilter());
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BleService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BleService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BleService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BleService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction("com.wrist.ble.SUCCESSSETINFO");
        intentFilter.addAction("com.wrist.ble.NRTDATAEND");
        return intentFilter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDeviceAddress=getIntent().getStringExtra("device");

        listView = findViewById(R.id.listView);
        tvScanning = findViewById(R.id.tvScanning);
        tvBatteryPercentage = findViewById(R.id.tvBatteryPercentage);
        tvSteps = findViewById(R.id.tvSteps);
        btnScan = findViewById(R.id.btnScan);



        connectToTracker();

        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        mLEScanner = mBluetoothAdapter.getBluetoothLeScanner();
        settings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build();
        filters = new ArrayList<ScanFilter>();
        filters.add(new ScanFilter.Builder().setDeviceName("PoochPlay").build());

        mBluetoothLeService = Constant.bleService;
        mHandler = new Handler();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshDevice();
//                dailyTV.performClick();
            }
        }, 2000);



    }

    private boolean mScanning;

    public void refreshDevice() {
        try {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else {
                //mBluetoothLeService.close();

                    scanLeDevice(true);

            }
        } catch (Exception e) {
            Log.d(TAG, "refreshDevice: " + e.getMessage());
            mHandler = new Handler();
            e.printStackTrace();
        }
    }



    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {

            mLeDevices.add(device.getAddress());

            //if(device.get) {
            mLeDevicesConnected.add(device);
            //  }
            Log.d(TAG, "onLeScan: " + device.getAddress());
        }
    };




    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);

                    if (mLeDevices.contains(mDeviceAddress)) {
                        if (mBluetoothLeService != null) {
                            mBluetoothLeService.close();
                            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
                        } else {
                            mBluetoothLeService = Constant.bleService;
                        }
                    }
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    public void connectToTracker() {
        ProtocolHanderManager.TimerStart();
        SysHanderManager.TimerStart();
        BleDecodeData.initializeAlarmInfo();
        BleDecodeRtData.sethandler(datahandler);

        broadcastReceiver = new BleReceiver(ConnectHandler);
        //   getActivity().registerReceiver(broadcastReceiver, makeGattUpdateIntentFilter());
    }

    Handler datahandler = new Handler(Looper.getMainLooper()) {
        @SuppressLint("LongLogTag")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Log.e(TAG,"data handler");
                try {
                    if (msg.what == 1) {
                        Calendar cal = Calendar.getInstance();
                        int second = cal.get(Calendar.SECOND);
                        int minute = cal.get(Calendar.MINUTE);
                        int hour = cal.get(Calendar.HOUR);

                        Log.e("total step", Array.RtCtrlData.totalSteps+"=====");
                        if (Array.RtCtrlData.totalSteps > 0) {
                            Log.d("TAG", "handleMessage: second " + second);


                            tvSteps.setText("Total steps -"+Array.RtCtrlData.totalSteps+"");

                            }
                        }
                    if (msg.what == 2) {
                        String dataString = msg.obj.toString().replace(" ", "")
                                .toUpperCase();
                        byte[] data = Util.hexStringToByte(dataString);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler ConnectHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 2:
                    Log.e(TAG, "=== case 2");
                    try {
                        if (Array.DecideProtocol == 1) {
                            BleData bledata = (BleData) msg.getData().getSerializable("data");
                            String uuid = bledata.getUuid();
                            String value = bledata.getValue();
                            String dataString = value.toString().replace(" ", "")
                                    .toUpperCase();
                            byte[] data = Util.hexStringToByte(dataString);
                            if (uuid.equals(SampleGattAttributes.NOTIFY_UUID)) {
                                BleTransLayer.TranslayerRecievePkt(data);
                            } else {
                                Message message = new Message();
                                message.what = 2;
                                message.obj = value;
                                datahandler.sendMessage(message);
                            }
                        } else if (Array.DecideProtocol == 2) {
                            BleData bledata = (BleData) msg.getData().getSerializable("data");
                            String uuid = bledata.getUuid();
                            String value = bledata.getValue();
                            Nrtanalysis.setdata(value, mBluetoothLeService, datahandler);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return;
                case 1:
                    Log.e(TAG, "=== case 1");
                    try {
                        funtion.setdecideProtocol();
                        if (Array.DecideProtocol == 1) {
                            boolean issuccess = funtion.opennewdecideProtocol();
                            BleHelper.checkinfo();
                        } else if (Array.DecideProtocol == 2) {
                            boolean issuccess = funtion.openoldrtdecideProtocol();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return;
                case 3:
                    Log.e(TAG, "connect handler connect");

                        try {

                            countRealSteps.postDelayed(countRealStepsHandler, 3000);

                            //TODO battery indication
                          /*  Intent gattServiceIntent = new Intent(getContext(), BluetoothLeService.class);
                            getActivity().startService(gattServiceIntent);
                            getActivity().bindService(gattServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);*/
                            //insertConnectingData1();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    return;
                case 4:
                    try {
                        Log.e(TAG, "connect handler disconnect");


                        //    mBluetoothLeService.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                case 5:
                    Log.e(TAG, "=== case 5");
                    try {
                        WaterSetInfo info = null;
//				BleHelper.SetUTC();
//				BleHelper.setUserSleep();
//				BleHelper.setUserBodyInfo();
                        //          BleHelper.RTSwitch();
//				BleHelper.setAlarmPlan();
                        BleHelper.setWaterInfo(info);
//				BleHelper.setMoveInfo();
//				BleHelper.setRemindInfo();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                case 6:

                    Log.e(TAG, "=== case 6");
                   Log.e(TAG,Array.liststep.toString());

                   for (int i=0;i<Array.liststep.size();i++) {
                       StepData stepData=Array.liststep.get(i);
                       lstDevicesName.add(stepData.getSteptime() + " - " + stepData.getStepdata());
                   }

                    ListAdapter adapter = new ArrayAdapter<>(TrackerActivity.this, android.R.layout.simple_list_item_1, lstDevicesName);
                    listView.setAdapter(adapter);


                    return;

            }
        }
    };



}