package com.gcm.parse;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

public class BingoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this,
                "UtFEsVX4ZcHMRsVHHbmmC1LVVdCrtnFkQ8kyvEJi",
                "ZIfbm9kjMRIURAfkuoteGf0VWjbywUyhcuq82zN2");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground("bingo");
    }
}
