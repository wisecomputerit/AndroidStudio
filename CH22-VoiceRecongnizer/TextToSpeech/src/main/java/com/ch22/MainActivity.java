package com.ch22;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends Activity {

    private TextToSpeech tts;
    private Context context;
    private EditText editText1;
    private Button talkBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        editText1 = (EditText)findViewById(R.id.editText1);
        talkBtn = (Button)findViewById(R.id.talkBtn);
        tts =  new TextToSpeech (this, new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    // 設定語系
                    int result = tts.setLanguage(Locale.US);
                    tts.setPitch(5); // 設定語音間距
                    tts.setSpeechRate(2); // 設定語音速率
                    // 確認手機所設定的TTS引擎是否支援該語系的語音
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(context, "TTS 不支援此語系語音",
                                Toast.LENGTH_SHORT).show();;
                    } else {
                        Toast.makeText(context, "TTS 開啟", Toast.LENGTH_SHORT).show();
                        talkBtn.setEnabled(true);
                        Drawable top = getResources().getDrawable(R.drawable.user_chat);
                        talkBtn.setCompoundDrawablesWithIntrinsicBounds(
                                null, top, null, null);
                    }
                } else {
                    Toast.makeText(context, "TTS 起動失敗!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) ;
    }
    public void onClick(View view) {
        String talkString = editText1.getText().toString();
        tts.speak (talkString, TextToSpeech.QUEUE_FLUSH, null, "Test") ;
    }
    @Override
    public void onDestroy() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
        Toast.makeText(context, "TTS 關閉!", Toast.LENGTH_SHORT).show();
    }
}
