package com.example.appmusic.notification;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.appmusic.adapters.SongListAdapter;

import java.io.IOException;

public class MyService extends Service{
    public static MediaPlayer player;

    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {

        public MyService getService()  {
            return MyService.this;
        }
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        player = new MediaPlayer();
        startMedia(SongListAdapter.musicStatic.getSource());
        return this.binder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public void startMedia(String url) {
        try {
            player.setDataSource(url);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
