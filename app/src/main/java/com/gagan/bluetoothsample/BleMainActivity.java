package com.gagan.bluetoothsample;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amit.poochplayble.BleCallBacks;
import com.amit.poochplayble.DataManager;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class BleMainActivity extends AppCompatActivity implements BleCallBacks {
    private List<BluetoothDevice> lstDevices = new ArrayList<>();
    private List<String> lstDevicesName = new ArrayList<>();
    private TextView tvScanning, tvBatteryPercentage, tvSteps;
    private Button btnScan;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan = findViewById(R.id.btnScan);
        DataManager.getInstance().setApplication(getApplication(),this);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getInstance().scan();
            }
        });
        listView = findViewById(R.id.listView);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            DataManager.getInstance().deviceConnect(lstDevices.get(position));
        });
    }


    @Override
    public void scanStart() {
        lstDevicesName = new ArrayList<>();
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lstDevicesName);
        listView.setAdapter(adapter);
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
            lstDevices = new ArrayList<>(bluetoothDeviceList);
            lstDevicesName = new ArrayList<>();
            for (BluetoothDevice device: lstDevices){
                lstDevicesName.add(device.getName()+"\n"+device.getAddress());
            }
            ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lstDevicesName);
            listView.setAdapter(adapter);
        }

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