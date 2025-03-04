package com.bluetooth.test;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class Bluetooth_Test extends Activity {
    private static final int REQUEST_ENABLE_BT = 2;
    private TextView devicesInfo_1, devicesInfo_2;
    private Button scanBoundBtn, scanBtn, disBtn;
    private BluetoothAdapter btAdapter;
    private Set<BluetoothDevice> devices;
    private BTReceiver receiver;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // 已配對藍芽設備
        devicesInfo_1 = (TextView)findViewById(R.id.devicesInfo_1);
        scanBoundBtn = (Button)findViewById(R.id.scanBoundBtn);
        scanBoundBtn.setOnClickListener(new BtnOnClickListener());
        
        // 尋找藍芽設備
        devicesInfo_2 = (TextView)findViewById(R.id.devicesInfo_2);
        scanBtn = (Button)findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(new BtnOnClickListener());
        
        // 開啟藍芽許可要求
        disBtn = (Button)findViewById(R.id.disBtn);
        disBtn.setOnClickListener(new BtnOnClickListener());
        
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        
        if(btAdapter == null) {
        	Toast.makeText(this, "請開啟 Bluetooth", Toast.LENGTH_SHORT).show();
        	finish();
        } else if(!btAdapter.isEnabled()) {
        	Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        	startActivityForResult(intent, REQUEST_ENABLE_BT);
        }
    }
    
    private class BtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			switch(view.getId()) {
				case R.id.scanBoundBtn:
					devicesInfo_1.setText("");
					devices = btAdapter.getBondedDevices();
					if(devices.size() > 0) {
						for(BluetoothDevice device : devices) {
							devicesInfo_1.append(device.getName() + ":" + 
									device.getAddress() + "==>" + device.getBondState() + "\n");
						}						
					}
					break;
				case R.id.scanBtn:
					//devicesInfo_2.setText("");
					btAdapter.startDiscovery();
					receiver = new BTReceiver();
					IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
					registerReceiver(receiver, filter);
					Toast.makeText(Bluetooth_Test.this, "Begin to scan", Toast.LENGTH_SHORT).show();
					break;
				case R.id.disBtn:
					Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
					discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
					startActivity(discoverableIntent);
					break;
			}
		}
    	
    } 
    
    private class BTReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				devicesInfo_2.append(device.getName() + ":" + 
						device.getAddress() + "==>" + device.getBondState() + "\n");
			}			
		}    	
    }

    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	if(receiver != null) {
    		unregisterReceiver(receiver);
    	}
    	
    }
    
}