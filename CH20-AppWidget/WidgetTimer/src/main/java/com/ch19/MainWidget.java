package com.ch19;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;

public class MainWidget extends AppWidgetProvider {
    private Context context;
    private Handler handler = new Handler();
    private Bitmap[] bmpTime = new Bitmap[10];
    private Runnable callback = new Runnable() {
        public void run() {
            updateDatetime();
        }
    };

    @Override
    public void onUpdate(Context context,
                         AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.i("mylog", "onUpdate");
        this.context = context;
        int imgW=73; int imgH=112;
        for(int i=0;i<bmpTime.length;i++) {
            int x1=i*imgW; int y1=0;
            Bitmap resource = BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.digital);
            bmpTime[i] = Bitmap.createBitmap(resource, x1,  y1,  imgW,  imgH);
        }
        updateDatetime();
    }

    private void updateDatetime() {
        // 取得遠端 widget 視圖
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget);
        // 取得現在時間
        Calendar cal = Calendar.getInstance();
        int h = cal.get(Calendar.HOUR_OF_DAY);
        int m = cal.get(Calendar.MINUTE);
        int s = cal.get(Calendar.SECOND);
        // 配置（時）的圖形
        setBitmapByTime(remoteViews, R.id.imageViewH1, R.id.imageViewH2, h);
        // 配置（分）的圖形
        setBitmapByTime(remoteViews, R.id.imageViewM1, R.id.imageViewM2, m);
        // 配置（秒）的圖形
        setBitmapByTime(remoteViews, R.id.imageViewS1, R.id.imageViewS2, s);
        // 取得 widget 元件
        ComponentName thisWidget = new ComponentName(context, MainWidget.class);
        // 取得 widget 管理器
        AppWidgetManager manager = AppWidgetManager.getInstance(context);

        int[] ids = manager.getAppWidgetIds(thisWidget);
        System.out.println("ids length : " + ids.length);
        for(int i:ids) {
            System.out.println("==> " + i);
        }

        // 透過管理器來更新 widget
        manager.updateAppWidget(thisWidget, remoteViews);
        // 調用 postDelayed()方法來進行遞迴呼叫
        handler.postDelayed(callback, 1000);
    }

    // 設定時間圖樣
    private void setBitmapByTime(RemoteViews remoteViews,
                                 int view1, int view2, int time) {
        String str = (time < 10)?"0" + time:""+time;
        Bitmap[] bmp = new Bitmap[2];
        bmp[0] = bmpTime[Integer.parseInt(str.substring(0, 1))];
        bmp[1] = bmpTime[Integer.parseInt(str.substring(1, 2))];
        // 改變遠端 widget 內的視圖元件內容
        remoteViews.setImageViewBitmap(view1, bmp[0]);
        remoteViews.setImageViewBitmap(view2, bmp[1]);
    }
}

