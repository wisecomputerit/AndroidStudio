package com.ch25;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {
    private ImageView imageView;
    private int[] gopher;
    private Handler handler;
    private GopherSprite g1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        gopher = new int[] {R.drawable.hole,
                            R.drawable.mole1, R.drawable.mole2, R.drawable.mole3,
                            R.drawable.mole2, R.drawable.mole1, R.drawable.hole};
        handler = new Handler();
        g1 = new GopherSprite(imageView);
    }

    private class GopherSprite implements Runnable {
        ImageView imageView;
        int idx;
        GopherSprite(ImageView imageView) {
            this.imageView = imageView;
        }
        @Override
        public void run() {
            draw();
        }
        private void draw() {
            idx = idx % gopher.length;
            imageView.setImageResource(gopher[idx]);
            handler.postDelayed(this, 300);
            idx = ++idx % gopher.length;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            handler.post(g1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
