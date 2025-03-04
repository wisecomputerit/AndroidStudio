package com.ch18.broadcast;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
    private ReceiverC receiverC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.orderedBroadcastBtn:
                sendOrderedBroadcast(new Intent("TEST_ORDERED"), null);
                Log.i("mylog", "Send ordered broadcast");
                break;
            case R.id.stickyBroadcastBtn:
                sendStickyBroadcast(new Intent("TEST_STICKY"));
                Log.i("mylog", "Send sticky broadcast");
                break;
            case R.id.newActivityBtn:
                receiverC = new ReceiverC();
                IntentFilter mIntentFilter = new IntentFilter();
                mIntentFilter.addAction("TEST_STICKY");
                registerReceiver(receiverC, mIntentFilter);
                Log.i("mylog", "Register receiverC");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(receiverC != null) {
            unregisterReceiver(receiverC);
            Log.i("mylog", "Unregister receiverC");
        }
    }
}
