package com.ch21;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;


public class MainActivity extends YouTubeBaseActivity {
    public static final String DEVELOPER_KEY = "AIzaSyCxkRRt-vDAMFWkTUEUXd_nDISzUX1Ih7k";
    private YouTubeThumbnailView youtube_thumbnail;
    private YouTubePlayerView youtube_view;
    private Context context;
    private YouTubePlayer player;
    private String vid = "B7tc8Vil1tA";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        // Youtube 縮略圖
        youtube_thumbnail = (YouTubeThumbnailView) findViewById(R.id.youtube_thumbnail);
        youtube_thumbnail.initialize(DEVELOPER_KEY, new YoutubeThumbnailOnInitializedListener());
        youtube_thumbnail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Play !", Toast.LENGTH_SHORT).show();
                player.cueVideo(vid);
            }

        });
        // Youtube VideoView
        youtube_view = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youtube_view.initialize(DEVELOPER_KEY, new YoutubeOnInitializedListener());
    }

    private class YoutubeThumbnailOnInitializedListener implements YouTubeThumbnailView.OnInitializedListener {
        @Override
        public void onInitializationFailure(YouTubeThumbnailView arg0,
                                            YouTubeInitializationResult arg1) {
            Toast.makeText(context, "Youtube onInitializationFailure !", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onInitializationSuccess(YouTubeThumbnailView arg0,
                                            YouTubeThumbnailLoader thumbnailLoader) {
            thumbnailLoader.setVideo(vid);
        }
    }

    private class YoutubeOnInitializedListener implements YouTubePlayer.OnInitializedListener {
        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
            Toast.makeText(context, "Youtube onInitializationFailure !", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
            if (!wasRestored) {
                player.loadVideo(vid);
            }
        }
    }
}
