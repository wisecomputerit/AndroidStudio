package com.ch17.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends Activity {

    private Calendar calendar = Calendar.getInstance();
    private EditText stockNoBtn;
    private PendingIntent sender;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        stockNoBtn = (EditText) findViewById(R.id.stockNoBtn);

    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.setupAlarmBtn:
                // 指定鬧鐘設定時間到時要執行AlarmService.class
                Intent intent = new Intent(context, AlarmService.class);
                intent.putExtra("stockNo", stockNoBtn.getText().toString());
                // 建立PendingIntent
                sender = PendingIntent.getService(context, 100, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                // 取得按下按鈕時的時間做為TimePickerDialog的預設值
                calendar.setTimeInMillis(System.currentTimeMillis());
                int hour   = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

				// 跳出TimePickerDialog來設定時間 */
                TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                        new MyOnTimeSetListener(), hour, minute, true);
                timePickerDialog.show();
                break;
            case R.id.removeAlarmBtn:
                // 由AlarmManager中移除
                AlarmManager alarmManager =
                        (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.cancel(sender);
				// 以Toast提示已刪除設定，並更新顯示的鬧鐘時間
                Toast.makeText(context, R.string.remove_alarm,
                        Toast.LENGTH_SHORT).show();
                ((Button)findViewById(R.id.setupAlarmBtn))
                        .setText(R.string.setup_alarm);
                break;
        }
    }

    // 設定on time監聽器
    private class MyOnTimeSetListener implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// 取得設定後的時間，秒跟毫秒設為 0
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
			/*
			 * AlarmManager.RTC_WAKEUP設定服務在系統休眠時同樣會執行
			 * 以set()設定的PendingIntent只會執行一次
			 */
            AlarmManager alarmManager =
                    (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), sender);

            String tmpS = format(hourOfDay) + "：" + format(minute);
            // 以Toast提示設定已完成
            Toast.makeText(context, "設定鬧鐘時間為" + tmpS,
                    Toast.LENGTH_SHORT).show();
        }
    }

    // 日期時間顯示兩位數的method
    private String format(int x) {
        String s = String.valueOf(x);
        return (s.length() == 1)?"0" + s:s;
    }
}