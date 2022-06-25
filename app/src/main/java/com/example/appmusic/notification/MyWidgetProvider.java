package com.example.appmusic.notification;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.appmusic.R;

public class MyWidgetProvider extends AppWidgetProvider {

    private static final String MyOnClick1 = "myOnClickTag1";
    private static final String MyOnClick2 = "myOnClickTag2";
    private static final String MyOnClick3 = "myOnClickTag3";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // Get all ids
        ComponentName thisWidget = new ComponentName(context, MyWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int widgetId : allWidgetIds) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
            remoteViews.setOnClickPendingIntent(R.id.noti_btn_play,
                    getPendingSelfIntent(context, MyOnClick1));
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (MyOnClick1.equals(intent.getAction())) {
            Log.v("Music", "Clicked button1 djasjdjajdjas");
        }

    };
}