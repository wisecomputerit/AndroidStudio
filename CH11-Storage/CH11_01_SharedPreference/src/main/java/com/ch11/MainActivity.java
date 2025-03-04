package com.ch11;

import java.util.Date;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private Context context;
	private SharedPreferences sp;
	private EditText usernameEditText, passwordEditText;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;
        
        // 建立與設定偏好資訊
        sp = getSharedPreferences("PREF_LOGIN", 
        						Context.MODE_PRIVATE);
        usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        
        // 呼叫讀取偏好資料
        readPref();
    }
    
    public void onClick(View view) {
    	switch(view.getId()) {
			case R.id.submitButton:
				if(checkLogin()) {
		    		Toast.makeText(context, "Welcome !", Toast.LENGTH_SHORT).show();
		    		Intent intent = new Intent();
		    		intent.setClass(context, Android.class);
		    		startActivity(intent);
		    		finish();
		    	} else {
		    		Toast.makeText(context, "Login incorrect !", Toast.LENGTH_SHORT).show();
		    	}
				break;
    	}
	}
    
    private boolean checkLogin() {
    	String username = getResources().getString(R.string.username);
    	String password = getResources().getString(R.string.password);
    	if(usernameEditText.getText().toString().equals(username) && 
    			passwordEditText.getText().toString().equals(password)) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    @Override
    // 通常會在應用程式的onPause()方法中進行偏好設定儲存作業。
    public void onPause() {
    	super.onPause();
    	// 儲存偏好資料
    	restorePref();
    }
    
    // 儲存偏好資料
    private void restorePref() {
    	String usn = usernameEditText.getText().toString();
		String pwd = passwordEditText.getText().toString();
		String time = new Date(System.currentTimeMillis ()).toString();
		
		// 取得偏好編輯模式
		SharedPreferences.Editor edit = sp.edit();
		edit.putString("username", usn);
		edit.putString("password", pwd);
		edit.putString("createtime", time);
		
		// 確認儲存
		edit.apply();
    }
    
    // 讀取偏好資料
    private void readPref() {
    	String usn = sp.getString("username", "");
    	String pwd = sp.getString("password", "");
    	String time  = sp.getString("createtime", "");
    	usernameEditText.setText(usn);
    	passwordEditText.setText(String.valueOf(pwd));
    	if(time.equals("")) {
    		this.setTitle("無偏好資料可讀取 !");
    	} else {
    		this.setTitle("上次記錄時間:" + time);
    	}
    }
}