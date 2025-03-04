package com.ch17.startsevice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(MainActivity.this, ChronometerService.class);
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.startServiceBtn:
                // 啟動服務
                startService(intent);
                break;
            case R.id.stopServiceBtn:
                // 關閉服務
                stopService(intent);
                break;
        }

    }
}
