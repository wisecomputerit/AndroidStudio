package com.pcschool.map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

public class ProximityIntentReceiver extends BroadcastReceiver {
    
   @Override
    public void onReceive(Context context, Intent intent) {
        
        String key = LocationManager.KEY_PROXIMITY_ENTERING;

        Boolean entering = intent.getBooleanExtra(key, false);
        
        if (entering) {
        	System.out.println("已進入...");
        	Toast.makeText(context, "已進入...", Toast.LENGTH_SHORT).show();
        }
        else {
        	System.out.println("已離開...");
        	Toast.makeText(context, "已離開...", Toast.LENGTH_SHORT).show();
        }
    }
}