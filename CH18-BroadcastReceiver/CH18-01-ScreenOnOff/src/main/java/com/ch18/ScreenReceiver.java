package com.ch18;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        switch(intent.getAction()) {
            case Intent.ACTION_SCREEN_ON:
                Log.i("mylog", "SCREEN ON");
                break;
            case Intent.ACTION_SCREEN_OFF:
                Log.i("mylog", "SCREEN OFF");
                break;
        }
    }
}
