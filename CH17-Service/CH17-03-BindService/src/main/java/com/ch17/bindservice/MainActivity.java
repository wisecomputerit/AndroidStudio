package com.ch17.bindservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {

    private ChronometerBindService cbs;
    private ServiceConnection serviceConnection;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        // 實作 ServiceConnection
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                cbs = ((ChronometerBindService.MyBinder)service).getService();
                Toast.makeText(context, cbs+"", Toast.LENGTH_SHORT).show();
                Log.i("mylog", "onServiceConnected".toString());
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                cbs = null;
                Log.i("mylog", "onServiceDisconnected".toString());
            }
        };
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.bindBtn:
                // 將服務繫結
                Intent bindIntent = new Intent(context,
                        ChronometerBindService.class);
                bindService(bindIntent, serviceConnection,
                        Context.BIND_ADJUST_WITH_ACTIVITY);
                Toast.makeText(context, "Bind Service 已啟動 !",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.invokeBtn:
                if(cbs != null) {
                    // 調用 run()
                    cbs.run();
                } else {
                    Toast.makeText(context, "cbs = null",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.unbindBtn:
                // 將服務斷開
                unbindService(serviceConnection);
                Toast.makeText(context, "Bind Service 已關閉 !",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
