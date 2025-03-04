package ch19.com.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class MainActivity extends Activity {
    ImageView imageView, imageView2, imageView3, imageView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        int x1, y1, imgW, imgH;
        x1=0; y1=0; imgW=75; imgH=112;
        Bitmap resource = BitmapFactory.decodeResource(getResources(), R.drawable.digital);
        Bitmap tmp = Bitmap.createBitmap(resource, x1,  y1,  imgW,  imgH);
        imageView.setImageBitmap(tmp);
        x1=75; y1=0; imgW=75; imgH=112;
        Bitmap tmp2 = Bitmap.createBitmap(resource, x1,  y1,  imgW,  imgH);
        imageView2.setImageBitmap(tmp2);
        x1=75+75; y1=0; imgW=75; imgH=112;
        Bitmap tmp3 = Bitmap.createBitmap(resource, x1,  y1,  imgW,  imgH);
        imageView3.setImageBitmap(tmp3);
        x1=75+75+75; y1=0; imgW=75; imgH=112;
        Bitmap tmp4 = Bitmap.createBitmap(resource, x1,  y1,  imgW,  imgH);
        imageView4.setImageBitmap(tmp4);
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
