package com.ch26;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private Intent recognizerIntent = null;
    private GridView gridView;
    private List<String> messageList;
    private ArrayAdapter<String> adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        gridView = (GridView) findViewById(R.id.gridView);

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        if (!hasRecognizer()) {
            Toast.makeText(context, "無語音辨識服務", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        messageList = new ArrayList<>();
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, messageList);
        gridView.setNumColumns(3);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new MyOnItemClickListener());
    }

    private boolean hasRecognizer() {
        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(recognizerIntent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String keyword = parent.getItemAtPosition(position).toString();

            Intent web = new Intent(Intent.ACTION_WEB_SEARCH);
            web.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            web.putExtra(SearchManager.QUERY, keyword);
            startActivity(web);

            Toast.makeText(context, keyword, Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View view) {
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "請說...");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        startActivityForResult(recognizerIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent it) {
        messageList.clear();
        if (requestCode != 1) {
            return;
        }
        if (resultCode != RESULT_OK) {
            return;
        }
        List<String> list = it.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        for (String s : list) {
            messageList.add(s);
        }
        adapter.notifyDataSetChanged();
    }
}
