package com.example.appmusic.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("Music", "click vao day nay ");

        context.sendBroadcast(new Intent("TRACKS_TRACKS")
                .putExtra("actionname", intent.getAction()));
    }
}
