package com.example.appmusic.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appmusic.models.MusicOnDB;
import com.example.appmusic.R;
import com.example.appmusic.adapters.PlayMusicViewPagerAdapter;
import com.example.appmusic.adapters.SongListAdapter;
import com.example.appmusic.fragments.MusicDiscFragment;
import com.example.appmusic.fragments.PlayListFragment;
import com.example.appmusic.models.AMusic;
import com.example.appmusic.models.Music;
import com.example.appmusic.models.MusicOnDevice;
import com.example.appmusic.notification.CreateNotification;
import com.example.appmusic.notification.IPlayable;
import com.example.appmusic.notification.MyService;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PlayMusicActivity extends Base implements
        MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, View.OnTouchListener, IPlayable {
    Toolbar toolbar;
    TextView txtTime, txtTotalTime;
    SeekBar seekBar;
    ImageButton btnRandom, btnPreview, btnPlay, btnNext, btnRepeat;
    ViewPager2 viewPager;
    public static PlayMusicViewPagerAdapter adapternhac;
    boolean repeat = false, isPause = false;
    private int mediaFileLengthInMilliseconds;
    private int music_id;
    private AMusic[] aMusics;
    //true la nhac dang duoc mo
    private int doRepeat = 0;
    private int calculatorListen = 0;
    private int totalListen, currentListen;
    private boolean doListen;
    private AMusic music;
    private boolean isPlaying; // true la nhac dang phat
    public static Intent intentInService;
    private MyService mService;
    boolean mBound = false;
    private static final int LISTEN_TIME_THRESH_HOLD = 30; //30s
    private NotificationManager notificationManager;
    private final Handler handler = new Handler();

    private void createChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANEL_ID,
                    "KhoaDev", NotificationManager.IMPORTANCE_LOW);

            notificationManager = getSystemService(NotificationManager.class);

            if(notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        createChannel();
        registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
        Log.v("Music", "on start" + music);
        createNewNotification(music, R.drawable.ic_pause_white);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
//        eventClick();
        intentInService = new Intent(this, MyService.class);
        this.stopService(intentInService);
        this.startService(intentInService);
        init();
        this.bindService(intentInService, serviceConnection, Context.BIND_AUTO_CREATE);
        isPlaying = true;
    }

    public void runMusic(AMusic music) {
        mService.getPlayer().reset();
        this.music = music;
        if(music.isType()) {
            try {
                mService.startMedia(music.getSource());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mService.setPlayer(MediaPlayer.create(getApplicationContext(), Integer.parseInt(music.getSource())));
            mService.getPlayer().start();
        }
        adapternhac = new PlayMusicViewPagerAdapter(getSupportFragmentManager(),getLifecycle(),
                            new MusicDiscFragment(music),
                            new PlayListFragment(music, mService));
        btnPlay.setImageResource(R.drawable.ic_pause_white);
        handlerSeekBar(mService.getPlayer());

        mService.getPlayer().setOnBufferingUpdateListener(this);
        mService.getPlayer().setOnCompletionListener(this);
    }

    public String convertMillisecondsToMinutes(int miniseconds) {
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(miniseconds);
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(miniseconds)%60;

        return minutes + ":" + String.format("%02d", seconds);
    }

/*
    public void saveMusicMostRecently(AMusic music, boolean status) {
        MusicOnDB musicOnDB = new MusicOnDB();
        musicOnDB.setName(music.getName());
        musicOnDB.setSource(music.getSource());
        musicOnDB.setImage(music.getImage());
        musicOnDB.setStatus(status);
        if(music.isType()) {
            musicOnDB.setType(true);
            musicOnDB.setSinger(((Music) music).singersToString());
        } else {
            musicOnDB.setType(false);
            musicOnDB.setSinger(((MusicOnDevice) music).getSinger());
        }
    }
*/

    private void primarySeekBarProgressUpdater() {
        seekBar.setProgress((int)(((float) mService.getPlayer().getCurrentPosition()/mediaFileLengthInMilliseconds)*100));
        calculatorListen++;
        if(calculatorListen > LISTEN_TIME_THRESH_HOLD && doListen) {
            totalListen++;
            doListen = false;
            if(currentListen < totalListen) {
                currentListen = totalListen;
            }
        }
        txtTime.setText(convertMillisecondsToMinutes(mService.getPlayer().getCurrentPosition()));
        isPlaying = mService.getPlayer().isPlaying();

        if (mService.getPlayer().isPlaying()) {
            Runnable runnable= new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(runnable,1000);
        } else {
            btnPlay.setImageResource(R.drawable.ic_play_white);
            eventHandlerRepeat();
        }
    }

    public void createNewNotification(AMusic music, int icon) {
        CreateNotification.createNotification(getApplicationContext(), music, icon, 1,
                10);
    }

    private void init() {
        music = SongListAdapter.musicStatic;
        int count = 0;
        Log.v("Music", music.getSource());
        aMusics = new AMusic[music.getPlaylists().size()];
        music_id = music.getId();
        for(AMusic aMusic:music.getPlaylists()) {
            aMusics[count++] = aMusic;
        }
        doListen = true;
        toolbar = findViewById(R.id.toolbar_play_nhac);
        seekBar = findViewById(R.id.seekbar_song);
        txtTime = findViewById(R.id.txt_time_song);
        txtTotalTime = findViewById(R.id.txt_total_time_song);
        btnRandom = findViewById(R.id.noti_random);
        btnPreview = findViewById(R.id.btn_preview);
        btnPlay = findViewById(R.id.btn_play);
        btnNext = findViewById(R.id.btn_next);
        btnRepeat = findViewById(R.id.btn_repeat);
        viewPager = findViewById(R.id.viewpaper_play_nhac);

        adapternhac = new PlayMusicViewPagerAdapter(getSupportFragmentManager(),getLifecycle(),
                            new MusicDiscFragment(music),
                            new PlayListFragment(music, mService));

        btnPlay.setImageResource(R.drawable.ic_pause_white);

        viewPager.setAdapter(adapternhac);
        eventOnclickMusic();
        seekBar.setOnTouchListener(this);
        dbManage.deleteAll();
        for(int i=0; i<aMusics.length; i++) {
            if(i == music_id) {
                saveMusicMostRecently(aMusics[i], true);
            } else {
                saveMusicMostRecently(aMusics[i], false);
            }
        }
    }

    public void saveMusicMostRecently(AMusic music, boolean status) {
        MusicOnDB musicOnDB = new MusicOnDB();
        musicOnDB.setName(music.getName());
        musicOnDB.setSource(music.getSource());
        musicOnDB.setImage(music.getImage());
        musicOnDB.setStatus(status);
        if(music.isType()) {
            musicOnDB.setType(true);
            musicOnDB.setSinger(((Music) music).singersToString());
        } else {
            musicOnDB.setType(false);
            musicOnDB.setSinger(((MusicOnDevice) music).getSinger());
        }
        dbManage.add(musicOnDB);
    }

    public void eventOnclickMusic() {
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRepeat.setImageResource(!repeat ? R.drawable.icon_repeat_true : R.drawable.icon_repeat);
                repeat = !repeat;
            }
        });

        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rd = new Random().nextInt();
                int randomIdMusic = (rd < 0) ? rd*(-1) : rd;
                runMusic(aMusics[randomIdMusic%aMusics.length]);
                createNewNotification(music, R.drawable.ic_pause_white);
                adapternhac.musicDiscFragment.playMusic(music);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPlay.setImageResource(R.drawable.ic_pause_white);
                onMusicNotiNext();
            }
        });

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPlay.setImageResource(R.drawable.ic_pause_white);
                onMusicNotiPre();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    btnPlay.setImageResource(R.drawable.iconplay);
                    onMusicNotiPause();
                    createNewNotification(music, R.drawable.ic_play_white);
                } else {
                    btnPlay.setImageResource(R.drawable.ic_pause_white);
                    onMusicNotiPlay();
                    createNewNotification(music, R.drawable.ic_pause_white);
                }
            }
        });
    }

/*    public AMusic getMusic(String musicString) {
        int id = Integer.parseInt(musicString.split(" =-= ")[0]);
        String name = musicString.split(" =-= ")[1];
        String source = musicString.split(" =-= ")[2];
        int musicTracks = Integer.parseInt(musicString.split(" =-= ")[3]);
        String image = musicString.split(" =-= ")[4];
        String singer = musicString.split(" =-= ")[5];
        int listens = Integer.parseInt(musicString.split(" =-= ")[6]);
        int likes = Integer.parseInt(musicString.split(" =-= ")[7]);
        boolean typeIsOnline = musicString.split(" =-= ")[8].equals("true");
        currentListen = listens;
        if(typeIsOnline) {
            Music music = new Music(id, name, source, musicTracks, image , listens, likes);
            music.setType(true);
            Singer newSinger = new Singer();
            newSinger.setName(singerCurrent);
            singerCurrent = singer;
            List<Singer> singers = new ArrayList<>();
            singers.add(newSinger);
            music.setSingers(singers);
            this.music = music;
            return music;
        } else {
            MusicOnDevice music = new MusicOnDevice(id, name, Integer.parseInt(source), musicTracks,
                    Integer.parseInt(image) , singerCurrent);
            music.setType(false);
            this.music = music;
            return music;
        }
    }*/

    public void eventHandlerRepeat() {
        if(repeat & doRepeat==0) {
            Log.v("Music", "chay vao day may lan");
            btnPlay.setImageResource(R.drawable.ic_pause_white);
            if(mService != null) {
                mService.getPlayer().reset();
            }
            doListen = true;
            calculatorListen = 0;
            runMusic(music);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBar.setSecondaryProgress(percent);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId() == R.id.seekbar_song){
            if(mService.getPlayer().isPlaying()){
                SeekBar sb = (SeekBar) view;
                int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
                mService.getPlayer().seekTo(playPositionInMillisecconds);
            }
        }
        return false;
    }

    public void handlerSeekBar(MediaPlayer mediaPlayer) {
        mediaFileLengthInMilliseconds = mediaPlayer.getDuration();
        txtTotalTime.setText(convertMillisecondsToMinutes(mediaFileLengthInMilliseconds));

        primarySeekBarProgressUpdater();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");
            Log.v("Music", action);
            switch (action){
                case CreateNotification.ACTION_PREVIUOS:
                    onMusicNotiPre();
                    break;
                case CreateNotification.ACTION_PLAY:
                    if (isPlaying){
                        btnPlay.setImageResource(R.drawable.iconplay);
                        onMusicNotiPause();
                    } else {
                        btnPlay.setImageResource(R.drawable.ic_pause_white);
                        onMusicNotiPlay();
                    }
                    break;
                case CreateNotification.ACTION_NEXT:
                    onMusicNotiNext();
                    break;
            }
        }
    };

    @Override
    public void onMusicNotiPre() {
        mService.getPlayer().reset();
        music_id = music_id != 0 ? --music_id : aMusics.length - 1;
        music = aMusics[music_id];
        if(music.isType()) {
            try {
                mService.getPlayer().setDataSource(music.getSource());
                mService.getPlayer().prepare();
                mService.getPlayer().start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mService.setPlayer(MediaPlayer.create(this, Integer.parseInt(music.getSource())));
            mService.getPlayer().start();
        }
        handlerSeekBar(mService.getPlayer());
        createNewNotification(music, R.drawable.ic_pause_white);
        adapternhac.musicDiscFragment.playMusic(music);
    }

    @Override
    public void onMusicNotiPlay() {
        doRepeat--;
        if(mService != null) {
            mService.getPlayer().seekTo(media_length);
            mService.getPlayer().start();
            isPlaying = true;
            handlerSeekBar(mService.getPlayer());
        } else {
            dropMessage();
        }
        createNewNotification(music, R.drawable.ic_pause_white);
    }

    private int media_length;

    @Override
    public void onMusicNotiPause() {
        doRepeat++;
        if(mService != null) {
            mService.getPlayer().pause();
            media_length = mService.getPlayer().getCurrentPosition();
            isPlaying = false;
            handlerSeekBar(mService.getPlayer());
        } else {
            dropMessage();
        }
        createNewNotification(music, R.drawable.ic_play_white);
    }

    @Override
    public void onMusicNotiNext() {
        mService.getPlayer().reset();
        music_id = music_id < aMusics.length - 1 ? ++music_id : 0;
        music = aMusics[music_id];
        if(music.isType()) {
            try {
                mService.getPlayer().setDataSource(music.getSource());
                mService.getPlayer().prepare();
                mService.getPlayer().start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mService.setPlayer(MediaPlayer.create(this, Integer.parseInt(music.getSource())));
            mService.getPlayer().start();
        }
        handlerSeekBar(mService.getPlayer());
        createNewNotification(music, R.drawable.ic_pause_white);
        adapternhac.musicDiscFragment.playMusic(music);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            handlerSeekBar(mService.getPlayer());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        if(mBound) {
            this.unbindService(serviceConnection);
            mBound = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void dropMessage() {
        Toast toast = Toast.makeText(this, "Hệ thống đang xử lí. Đợi 1 chút.....", Toast.LENGTH_SHORT);
        toast.show();
    }
}