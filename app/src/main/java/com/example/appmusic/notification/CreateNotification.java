package com.example.appmusic.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.appmusic.models.AMusic;
import com.example.appmusic.models.Music;
import com.example.appmusic.R;
import com.example.appmusic.models.MusicOnDevice;

import java.io.InputStream;
import java.net.URL;

public class CreateNotification {

    public static final String CHANEL_ID = "chanel1";
    public static final String ACTION_PREVIUOS = "actionprevious";
    public static final String ACTION_PLAY = "actionplay";
    public static final String ACTION_NEXT = "actionnext";

    public static Notification notification;
    public static NotificationManagerCompat notificationCompat;

    public static void createNotification(Context context, AMusic music, int playbutton, int pos, int size) {
        notificationCompat = NotificationManagerCompat.from(context);
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat( context, "tag");
        Log.v("Music", "CHECK MUSIC IS NULL" + music);
        Bitmap bitmap = null;
        if(music.isType()) {
            try {
                InputStream inputStream = new URL(music.getImage()).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), Integer.parseInt(music.getImage()));
        }

        PendingIntent pendingIntentPrevious;
        int drw_previous;
        if (pos == 0){
            pendingIntentPrevious = null;
            drw_previous = 0;
        } else {
            Intent intentPrevious = new Intent(context, NotificationReceiver.class)
                    .setAction(ACTION_PREVIUOS);
            pendingIntentPrevious = PendingIntent.getBroadcast(context, 0,
                    intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT);
            drw_previous = R.drawable.ic_pre_white;
        }

        Intent intentPlay = new Intent(context, NotificationReceiver.class)
                .setAction(ACTION_PLAY);
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0,
                intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent pendingIntentNext;
        int drw_next;
        if (pos == size){
            pendingIntentNext = null;
            drw_next = 0;
        } else {
            Intent intentNext = new Intent(context, NotificationReceiver.class)
                    .setAction(ACTION_NEXT);
            pendingIntentNext = PendingIntent.getBroadcast(context, 0,
                    intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
            drw_next = R.drawable.ic_next_white;
        }

        notification = new NotificationCompat.Builder(context, CHANEL_ID)
                .setSmallIcon(R.drawable.iconplay)
                .setContentTitle(music.getName())
                .setContentText(music.isType() ? ((Music) music).getSingers().get(0).getName() : ((MusicOnDevice) music).getSinger())
                .setLargeIcon(bitmap)
                .addAction(drw_previous, "Previous", pendingIntentPrevious)
                .addAction(playbutton, "Play", pendingIntentPlay)
                .addAction(drw_next, "Next", pendingIntentNext)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationCompat.notify(1, notification);
    }

    public static void updateNoti() {
        notificationCompat.notify(1, notification);
    }

    public static void deleteNoti() {
        notificationCompat.cancel(1);
    }
}
