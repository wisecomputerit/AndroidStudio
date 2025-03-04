package com.ch19;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.mlb.service.MLBService;
import com.mlb.util.ImageUtils;
import com.mlb.vo.MLB;

public class MLB_AppWidget extends AppWidgetProvider {
    public static int teamId = 14;
    public static String mlb_url;
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        mlb_url = context.getResources().getString(R.string.mlb_url);
        mlb_url += "/" + teamId;
        // 設置執行緒策略
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        // 設置VM策略
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
    }

    @Override
    public void onUpdate(Context context,
                         AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        // 取得 App Widget 的 remoteViews
        defaultSize(context, appWidgetManager, appWidgetIds);
    }

    // 預設佈局
    private void defaultSize(Context context,
                             AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget);
        // 部署 MLB 資料
        deployRemoteViews(context, remoteViews);
        // 更新 App Widget 狀態
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    // 小尺寸佈局
    private void smallSize(Context context,
                           AppWidgetManager appWidgetManager, int appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.smallwidget);
        // 部署 MLB 資料
        deployRemoteViews(context, remoteViews);
        // 更新 App Widget 狀態
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    private void deployRemoteViews(Context context, RemoteViews view) {
        MLB mlb = new MLBService(mlb_url).getMLBByTeamId(teamId);
        if (mlb != null) {
            // 將MLB戰績顯示在AppWidget的UI畫面上
            switch(view.getLayoutId()) {
                case R.layout.widget:
                    view.setTextViewText(R.id.team, mlb.get隊名());
                    view.setTextViewText(R.id.uptime, mlb.get更新時間());
                case R.layout.smallwidget:
                    view.setImageViewBitmap(R.id.imageView1,
                            new ImageUtils().getImageBitmap(mlb.get隊徽URL()));
                    view.setTextViewText(R.id.order, mlb.get戰績().get目前排名());
                    view.setTextViewText(R.id.order, mlb.get戰績().get目前排名());
                    view.setTextViewText(R.id.vs, mlb.get戰績().get勝敗比());
                    view.setTextViewText(R.id.rate, mlb.get戰績().get勝率());
                    view.setTextViewText(R.id.f_rate, mlb.get戰績().get團隊打擊率());
                    view.setTextViewText(R.id.d_rate, mlb.get戰績().get團隊防禦率());
            }
            // 按下 App Widget 圖片後的動作
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(mlb_url));
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context, 0, intent, 0);
            view.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
        }
    }
    @Override
    public void onAppWidgetOptionsChanged(Context context,
                                          AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        int d_min_h = newOptions.getInt(
                AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int d_max_h = newOptions.getInt(
                AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
        // 判斷Widget要使用的佈局資源
        if (d_max_h - d_min_h > 50) {
            defaultSize(context, appWidgetManager, new int[]{appWidgetId});
        } else {
            smallSize(context, appWidgetManager, appWidgetId);
        }
        super.onAppWidgetOptionsChanged(context, appWidgetManager,
                appWidgetId, newOptions);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Toast.makeText(context, "onDeleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Toast.makeText(context, "onDisabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        Toast.makeText(context, "onReceive:" + action, Toast.LENGTH_SHORT)
                .show();
    }
}
