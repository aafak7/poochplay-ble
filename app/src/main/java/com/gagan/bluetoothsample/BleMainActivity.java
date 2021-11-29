package com.amit.poochplayble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.location.LocationManagerCompat;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class BleMainActivity extends AppCompatActivity implements BleCallBacks {

    private List<BluetoothDevice> lstDevices = new ArrayList<>();
    private List<String> lstDevicesName = new ArrayList<>();
    private TextView tvScanning, tvBatteryPercentage, tvSteps;
    private Button btnScan;
    private ListView listView;



    //for getting tracker list

    private String deviseList = "";
    private BluetoothAdapter mBluetoothAdapter;

    private static final int REQUEST_ENABLE_BT = 1;
    private Handler mHandler;
    private BluetoothLeScanner mLEScanner;

    private static final long SCAN_PERIOD = 10000;
    private List<ScanFilter> filters;
    private ScanSettings settings;


    public static String mDeviceAddress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan = findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        listView = findViewById(R.id.listView);



        ListAdapter adapter = new ArrayAdapter<>(BleMainActivity.this, android.R.layout.simple_list_item_1, lstDevicesName);
        listView.setAdapter(adapter);

        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Check whether the Bluetooth device


        mHandler = new Handler();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }


        checkLocationOn();


        // Initializes list view adapter.


        listView.setOnItemClickListener((parent, view, position, id) -> {

            BluetoothDevice device = lstDevices.get(position);
            mDeviceAddress=device.getAddress();

            Intent intent= new Intent(this, com.amit.poochplayble.TrackerActivity.class);
            intent.putExtra("device",mDeviceAddress);
            startActivity(intent);
        });

    }



    private void checkLocationOn() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!LocationManagerCompat.isLocationEnabled(lm)) {
            scanLeDevice(false);


        } else {
            scanLeDevice(true);

        }
    }


    private void scanLeDevice(final boolean enable) {
        try {
            if (enable) {
                mHandler.postDelayed(() -> {
                    if (mLEScanner != null && mScanCallback != null &&
                            mBluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {

                        mLEScanner.stopScan(mScanCallback);

                    }
                }, SCAN_PERIOD);


                if (mScanCallback != null && mLEScanner != null) {
                    filters.add(new ScanFilter.Builder().setDeviceName("PoochPlay").build());

                    if (mScanCallback != null)
                        mLEScanner.startScan(filters, settings, mScanCallback);

                } else if (mLEScanner == null && mScanCallback != null) {
                    mLEScanner = mBluetoothAdapter.getBluetoothLeScanner();

                    settings = new ScanSettings.Builder()
                            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                            .build();

                    filters = new ArrayList<>();
                    filters.add(new ScanFilter.Builder().setDeviceName("PoochPlay").build());

                    if (mScanCallback != null)
                        mLEScanner.startScan(filters, settings, mScanCallback);
                }
            } else {
                if (mScanCallback != null && mLEScanner != null) {
                    mLEScanner.stopScan(mScanCallback);
                }

            }
        } catch (Exception e) {
        }
    }

    private ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice btDevice;
            btDevice = result.getDevice();


            if (!deviseList.contains(btDevice.getAddress())) {
                deviseList = deviseList.concat(result.getDevice().getName() + " - " + result.getDevice().getAddress() + "\n");

                lstDevices.add(result.getDevice());
                lstDevicesName.add(result.getDevice().getName() + " - " + result.getDevice().getAddress());

                ListAdapter adapter = new ArrayAdapter<>(BleMainActivity.this, android.R.layout.simple_list_item_1, lstDevicesName);
                listView.setAdapter(adapter);
            }


//            adapter.addDevice(btDevice);
            Log.e("list",btDevice.getAddress());

        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            for (ScanResult sr : results) {
                Log.e("ScanResult - Results", sr.toString());
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            Log.e("Scan Failed", "Error Code: " + errorCode);
        }
    };

    @Override
    public void scanStart() {

    }

    @Override
    public void scanEnd() {
        Log.e(TAG, "scanEnd: " );
    }

    @Override
    public void getDeviceList(List<BluetoothDevice> bluetoothDeviceList) {


    }

    @Override
    public void getDevicePercentage(String batteryPercentage) {
        Log.e(TAG, "getDevicePercentage: "+batteryPercentage );
    }

    @Override
    public void getDeviceData(byte[] data) {
        Log.e(TAG, "getDeviceData: "+ data);
    }

    @Override
    public void getTotalCalories(String totalCalories) {
        Log.e(TAG, "getTotalCalories: "+totalCalories );
    }

    @Override
    public void getTotalSteps(String totalSteps) {
        Log.e(TAG, "getTotalSteps: "+totalSteps );
    }

    @Override
    public void startConnect() {
        Log.e(TAG, "startConnect: " );
    }

    @Override
    public void connectFailed(BluetoothDevice device) {
        Log.e(TAG, "connectFailed: "+device.getAddress() );
    }

    @Override
    public void connectSuccess(String macAddress) {
        Log.e(TAG, "connect Success:"+macAddress );
    }
}