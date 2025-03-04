package com.ch21;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {

    private Map<String, Integer> piano = new HashMap<>();
    SoundPool soundPool = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildSoundPool();

        PlayOnTouchListener listener = new PlayOnTouchListener();
        findViewById(R.id.do1).setOnTouchListener(listener);
        findViewById(R.id.re).setOnTouchListener(listener);
        findViewById(R.id.mi).setOnTouchListener(listener);
        findViewById(R.id.fa).setOnTouchListener(listener);
        findViewById(R.id.so).setOnTouchListener(listener);
        findViewById(R.id.la).setOnTouchListener(listener);
        findViewById(R.id.si).setOnTouchListener(listener);
    }

    private void buildSoundPool() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attr = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(attr)
                    .build();
        } else {
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }

        piano.put("do1", soundPool.load(this, R.raw.do1, 1));
        piano.put("re", soundPool.load(this, R.raw.re, 1));
        piano.put("mi", soundPool.load(this, R.raw.mi, 1));
        piano.put("fa", soundPool.load(this, R.raw.fa, 1));
        piano.put("so", soundPool.load(this, R.raw.so, 1));
        piano.put("la", soundPool.load(this, R.raw.la, 1));
        piano.put("si", soundPool.load(this, R.raw.si, 1));
    }

    private class PlayOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                String soundName = (String)v.getTag();
                soundPool.play(piano.get(soundName), 1, 1, 1, 0, 1);
            }
            return false;
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
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
