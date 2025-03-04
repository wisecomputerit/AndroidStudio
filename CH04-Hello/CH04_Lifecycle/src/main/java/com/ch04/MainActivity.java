package com.ch04;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        Log.i("lifecycle", "call onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("lifecycle", "call onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifecycle", "call onCreate()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("lifecycle", "call onRestart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("lifecycle", "call onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("lifecycle", "call onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("lifecycle", "call onDestroy()");

    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button1:
                Intent intent = new Intent();
                intent.setClass(context, NewActivity.class);
                startActivity(intent);
                break;
            case R.id.button2:
                Intent intent2 = new Intent(context, DialogActivity.class);
                startActivity(intent2);
                break;
            case R.id.button3:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("AlertDialog.Builder");
                alertDialogBuilder
                        .setMessage("Click Ok to Close Dialog")
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            case R.id.button4:
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
