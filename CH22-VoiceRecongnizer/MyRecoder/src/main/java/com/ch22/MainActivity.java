package com.ch22;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends Activity {

    private File recordAudioFile;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private TextView textView1;
    private ImageView imageView1;
    private Button btnPlay, btnDelete;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        textView1 = (TextView)findViewById(R.id.textView1);
        imageView1 = (ImageView)findViewById(R.id.imageView1);

        imageView1.setOnTouchListener(new MyTouchListener());

        btnPlay.setEnabled(false);
        btnDelete.setEnabled(false);

    }

    public class MyTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    imageView1.setImageResource(R.drawable.media_record_on);

                    try {
                        recordAudioFile = File.createTempFile(
                                "raw",
                                ".amr",
                                Environment.getExternalStorageDirectory());
                        textView1.setText("路徑：" + recordAudioFile.getAbsolutePath());
                        mediaRecorder = new MediaRecorder();
                        mediaRecorder.setAudioSource(
                                MediaRecorder.AudioSource.MIC);
                        mediaRecorder.setOutputFormat(
                                MediaRecorder.OutputFormat.DEFAULT);
                        mediaRecorder.setAudioEncoder(
                                MediaRecorder.AudioEncoder.DEFAULT);
                        mediaRecorder.setOutputFile(
                                recordAudioFile.getAbsolutePath());
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch(Exception e) {

                    }

                    Toast.makeText(context, "Record", Toast.LENGTH_SHORT).show();

                    break;
                case MotionEvent.ACTION_UP:
                    imageView1.setImageResource(R.drawable.media_record_off);

                    if (mediaRecorder != null) {

                        mediaRecorder.stop();
                        mediaRecorder.release();
                        mediaRecorder = null;

                        btnPlay.setEnabled(true);
                        btnPlay.setCompoundDrawablesWithIntrinsicBounds(
                                null,
                                getResources().getDrawable(R.drawable.player_play),
                                null,
                                null);// left, top, right, bottom
                        btnDelete.setEnabled(true);
                        btnDelete.setCompoundDrawablesWithIntrinsicBounds(
                                null,
                                getResources().getDrawable(R.drawable.delete),
                                null,
                                null);// left, top, right, bottom

                        playAudioFile();
                        Toast.makeText(context, "Play", Toast.LENGTH_SHORT).show();

                    }

                    break;
            }
            return true;
        }

    }

    private void playAudioFile() {
        File f = new File(recordAudioFile.getAbsolutePath());
        Uri uri = Uri.fromFile(f);
        mediaPlayer = MediaPlayer.create(context, uri);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer player) {
                player.start();
            }

        });
    }

    private void deleteAudioFile() {
        recordAudioFile.delete();
        textView1.setText("");
        btnPlay.setEnabled(false);
        btnPlay.setCompoundDrawablesWithIntrinsicBounds(
                null,
                getResources().getDrawable(R.drawable.player_play_gray) ,
                null,
                null); // left, top, right, bottom
        btnDelete.setEnabled(false);
        btnDelete.setCompoundDrawablesWithIntrinsicBounds(
                null,
                getResources().getDrawable(R.drawable.delete_gray) ,
                null,
                null);// left, top, right, bottom

    }
    public void onClick(View view) {
        try {
            switch (view.getId()) {

                case R.id.btnPlay:
                    playAudioFile();
                    Toast.makeText(context, "Re play !", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnDelete:
                    deleteAudioFile();
                    Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                    break;

            }
        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        if(mediaPlayer != null) mediaPlayer.release();
        super.onDestroy();
    }

}
