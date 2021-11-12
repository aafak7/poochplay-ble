package com.gagan.bluetoothsample;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.amit.poochplayble.BleCallBacks;
import com.amit.poochplayble.DataManager;
import com.clj.fastble.data.BleDevice;

import java.util.List;

import static android.content.ContentValues.TAG;

public class BleMainActivity extends AppCompatActivity implements BleCallBacks {


    private ListView listView;
    private String deviseList = "";
    private TextView tvScanning, tvBatteryPercentage, tvSteps;
    private Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan = findViewById(R.id.btnScan);
        DataManager.getInstance().setApplication(getApplication());
        DataManager.getInstance().setBleCallBacks(this);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getInstance().scan();
            }
        });
    }


    @Override
    public void scanStart() {
        Log.e(TAG, "scanStart: " );
    }

    @Override
    public void scanEnd() {
        Log.e(TAG, "scanEnd: " );
    }

    @Override
    public void getDeviceList(List<BluetoothDevice> bluetoothDeviceList) {
        if(bluetoothDeviceList!=null && bluetoothDeviceList.size()>0) {
//            DataManager.getInstance().deviceConnect(bluetoothDeviceList.get(0));
            Log.e(TAG, "getDeviceList: "+ bluetoothDeviceList.size());
        }

    }

    @Override
    public void getDevicePercentage(String batteryPercentage) {
        Log.e(TAG, "getDevicePercentage: "+batteryPercentage );
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
    public void connectSuccess(BleDevice bleDevice) {
        Log.e(TAG, "connectSuccess: "+bleDevice.getMac() );
    }
}