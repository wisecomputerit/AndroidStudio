package com.ch18;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MainActivity extends Activity {
    private CheckBox checkBox;
    private Context context;
    private ScreenReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked) {
                    Toast.makeText(context, R.string.register_receiver,
                            Toast.LENGTH_SHORT).show();
                    receiver = new ScreenReceiver();
                    IntentFilter filter = new IntentFilter();
                    filter.addAction(Intent.ACTION_SCREEN_ON);
                    filter.addAction(Intent.ACTION_SCREEN_OFF);
                    filter.setPriority(100);
                    registerReceiver(receiver, filter);
                } else {
                    Toast.makeText(context, R.string.unregister_receiver,
                            Toast.LENGTH_SHORT).show();
                    if(receiver != null) {
                        unregisterReceiver(receiver);
                    }
                }
            }
        });
    }
}
