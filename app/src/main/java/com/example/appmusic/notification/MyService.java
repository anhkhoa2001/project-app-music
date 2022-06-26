package com.example.appmusic.notification;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.appmusic.activities.Base;
import com.example.appmusic.adapters.SongListAdapter;

import java.io.IOException;

public class MyService extends Service {
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
        if(Base.musicStatic.isType()) {
            player = new MediaPlayer();
            startMedia(Base.musicStatic.getSource());
        } else {
            player = MediaPlayer.create(getApplicationContext(), Integer.parseInt(Base.musicStatic.getSource()));
            player.start();
        }
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

    public void setPlayer(MediaPlayer player) {
        this.player = player;
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
