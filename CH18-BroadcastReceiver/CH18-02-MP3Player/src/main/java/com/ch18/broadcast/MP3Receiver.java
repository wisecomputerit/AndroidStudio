package com.ch18.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

public class MP3Receiver extends BroadcastReceiver {
    private static NotificationManager notificationManager;
    private Notification notification;
    private static MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("mylog", intent.getAction());
        if(notificationManager == null) {
            notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
        }
        switch(intent.getAction()) {
            case "PLAY_STARWARS":
                if(mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(context, R.raw.starwar);
                } else {
                    int length = mediaPlayer.getCurrentPosition();
                    if (length > 0 && length < mediaPlayer.getDuration()) {
                        mediaPlayer.seekTo(length);
                    }
                }
                mediaPlayer.start();
                createNotification(context);
                break;
            case "PAUSE_STARWARS":
                mediaPlayer.pause();
                break;
            case "STOP_STARWARS":
                mediaPlayer.stop();
                notificationManager.cancelAll();
                notificationManager = null;
                mediaPlayer = null;
                break;
        }
    }

    private void createNotification(Context context) {

        Notification.Builder builder = new Notification.Builder(context);

        PendingIntent playIntent = PendingIntent.getBroadcast(
                context, 0, new Intent("PLAY_STARWARS"), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pauseIntent = PendingIntent.getBroadcast(
                context, 1, new Intent("PAUSE_STARWARS"), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent stopIntent = PendingIntent.getBroadcast(
                context, 2, new Intent("STOP_STARWARS"), PendingIntent.FLAG_UPDATE_CURRENT);
        long[] vibratepattern = { 100, 400, 500, 400 };

        builder.setSmallIcon(R.drawable.music) // 通知服務 icon
                .setContentTitle("現正播放:星際大戰") // 標題
                .setContentText("StarWars") // 內文
                .setContentInfo("1:35") // 信息
                .setTicker("音樂播放") // Ticker 標題
                .setLights(0xFFFFFFFF, 1000, 1000) // LED
                .setVibrate(vibratepattern) // 震動
                .addAction(R.drawable.media_playback_play, "播放", playIntent)
                .addAction(R.drawable.media_playback_pause, "暫停", pauseIntent)
                .addAction(R.drawable.media_playback_stop, "停止", stopIntent)
                .setOngoing(true)
                .setAutoCancel(false);
        notification = builder.build();
        notificationManager.notify(100, notification);
    }
}
