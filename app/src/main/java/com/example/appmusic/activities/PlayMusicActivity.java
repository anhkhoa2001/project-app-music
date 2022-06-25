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
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appmusic.adapters.PlayMusicViewPagerAdapter;
import com.example.appmusic.adapters.SongListAdapter;
import com.example.appmusic.fragments.MusicDiscFragment;
import com.example.appmusic.fragments.PlayListFragment;
import com.example.appmusic.models.AMusic;
import com.example.appmusic.models.Music;
import com.example.appmusic.models.MusicOnDevice;
import com.example.appmusic.models.Singer;
import com.example.appmusic.notification.MyService;
import com.example.appmusic.notification.CreateNotification;
import com.example.appmusic.notification.IPlayable;
import com.example.appmusic.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PlayMusicActivity extends Base implements
        MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, View.OnTouchListener, IPlayable {
    Toolbar toolbar;
    TextView txtTime, txtTotalTime;
    SeekBar seekBar;
    ImageButton btnRandom, btnPreview, btnPlay, btnNext, btnRepeat;
    ViewPager2 viewPager;
    MusicDiscFragment fragment_dia_nhac;
    PlayListFragment fragment_play_danhsach_baihat;
    public static PlayMusicViewPagerAdapter adapternhac;
    int position = 0;
    boolean repeat = false;
    private int mediaFileLengthInMilliseconds;
    private String URL_MUSIC;
    private String URL_MUSIC_PRIOR;
    private String URL_MUSIC_AFTER;
    private int music_id;
    private String[] musics;
    //true la nhac dang duoc mo
    private int doRepeat = 0;
    private boolean isPause; // true la da dung
    private int calculatorListen = 0;
    private int totalListen, currentListen;
    private boolean doListen;
    private String singerCurrent;
    public RemoteViews remoteViews;
    private AMusic music;
    private boolean isPlaying; // true la nhac dang phat
    private Intent intentInService;

    private MyService mService;
    boolean mBound = false;

    private static final int LISTEN_TIME_THRESH_HOLD = 30; //30s


    private NotificationManager notificationManager;

    private final Handler handler = new Handler();

    private void createChanel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANEL_ID,
                    "KOD Dex", NotificationManager.IMPORTANCE_LOW);

            notificationManager = getSystemService(NotificationManager.class);

            if(notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("Music", "app finished");
    }

/*    @Override
    public void onBackPressed() {
        Log.v("Music", "you clicked button back");
        isBack = true;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(getIntent().getStringExtra("Type_ID"), getIntent().getIntExtra("Music_ID", 0));
        this.startActivity(intent);
    }*/

    @Override
    public void onStart() {
        super.onStart();
        createChanel();
        registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
        CreateNotification.createNotification(this, music, R.drawable.ic_pause_white, 1, 10);
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

    public void runMusic(String url) {
        mService.getPlayer().pause();
        mService.getPlayer().reset();
        if(music.isType()) {
            try {
                mService.startMedia(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mService.setPlayer(MediaPlayer.create(getApplicationContext(), Integer.parseInt(url)));
            mService.getPlayer().start();
        }
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
            Runnable notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(notification,1000);
        } else {
            eventHandlerRepeat();
        }
    }

    private void init() {
        Intent intent = getIntent();
        if (intent != null) {
            URL_MUSIC = intent.hasExtra("Music_Source") ? intent.getStringExtra("Music_Source") : null;
            Log.v("Music", "dsajsdja " + URL_MUSIC);
            /*URL_MUSIC_PRIOR = intent.hasExtra("Music_Source_Prior") ? intent.getStringExtra("Music_Source_Prior") : null;
            URL_MUSIC_AFTER = intent.hasExtra("Music_Source_After") ? intent.getStringExtra("Music_Source_After") : null;
        */
            music_id = intent.hasExtra("ID") ? intent.getIntExtra("ID", 0) : null;
            musics = intent.hasExtra("Musics") ? intent.getStringArrayExtra("Musics") : null;
            music = getMusic(musics[music_id]);
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

        adapternhac = new PlayMusicViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        btnPlay.setImageResource(R.drawable.ic_pause_white);

        viewPager.setAdapter(adapternhac);
/*        fragment_dia_nhac = (MusicDiscFragment) getSupportFragmentManager().findFragmentByTag(position + "");*/
        Log.v("Music", "check exist fragment " + fragment_dia_nhac);
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
                runMusic(getMusic(musics[randomIdMusic%musics.length]).getSource());
                URL_MUSIC = getMusic(musics[randomIdMusic%musics.length]).getSource();
                music = getMusic(musics[randomIdMusic%musics.length]);
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
                if (isPlaying){
                    btnPlay.setImageResource(R.drawable.iconplay);
                    onMusicNotiPause();
                    CreateNotification.createNotification(getApplicationContext(), music, R.drawable.ic_play_white, 1,
                            10);
                } else {
                    btnPlay.setImageResource(R.drawable.ic_pause_white);
                    onMusicNotiPlay();
                    CreateNotification.createNotification(getApplicationContext(), music, R.drawable.ic_pause_white, 1,
                            10);
                }
            }
        });

        seekBar.setOnTouchListener(this);
    }

    public AMusic getMusic(String musicString) {
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
    }

    public void eventHandlerRepeat() {
        if(repeat) {
            btnPlay.setImageResource(R.drawable.ic_pause_white);
            if(mService != null) {
                mService.getPlayer().reset();
            }
            doListen = true;
            calculatorListen = 0;
            runMusic(URL_MUSIC);
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
        Log.v("Music", URL_MUSIC);
        mService.getPlayer().reset();
        if(music_id != 0) {
            music_id--;
            URL_MUSIC = getMusic(musics[music_id]).getSource();
            music = getMusic(musics[music_id]);
        }
        if(music.isType()) {
            try {
                mService.getPlayer().setDataSource(URL_MUSIC);
                mService.getPlayer().prepare();
                mService.getPlayer().start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mService.setPlayer(MediaPlayer.create(this, Integer.parseInt(URL_MUSIC)));
            mService.getPlayer().start();
        }
        handlerSeekBar(mService.getPlayer());
        CreateNotification.createNotification(this, music, R.drawable.ic_pause_white, 1,
                10);
    }

    @Override
    public void onMusicNotiPlay() {
        if(mService != null) {
            mService.getPlayer().seekTo(media_length);
            mService.getPlayer().start();
            isPlaying = true;
            handlerSeekBar(mService.getPlayer());
        } else {
            dropMessage();
        }
        CreateNotification.createNotification(this, music, R.drawable.ic_pause_white, 1,
                10);
    }

    private int media_length;

    @Override
    public void onMusicNotiPause() {
        if(mService != null) {
            mService.getPlayer().pause();
            media_length = mService.getPlayer().getCurrentPosition();
            isPlaying = false;
            handlerSeekBar(mService.getPlayer());
            Log.v("Music", "on pause" + mService.getPlayer().getCurrentPosition());
        } else {
            dropMessage();
        }
        CreateNotification.createNotification(this, music, R.drawable.ic_play_white, 1,
                10);
    }

    @Override
    public void onMusicNotiNext() {
        Log.v("Music", URL_MUSIC);
        mService.getPlayer().reset();
        if(music_id < musics.length - 1) {
            music_id++;
            URL_MUSIC = getMusic(musics[music_id]).getSource();
            music = getMusic(musics[music_id]);
        }
        if(music.isType()) {
            try {
                mService.getPlayer().setDataSource(URL_MUSIC);
                mService.getPlayer().prepare();
                mService.getPlayer().start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mService.setPlayer(MediaPlayer.create(this, Integer.parseInt(URL_MUSIC)));
            mService.getPlayer().start();
        }
        handlerSeekBar(mService.getPlayer());
        CreateNotification.createNotification(this, music, R.drawable.ic_pause_white, 1,
                10);
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

    public void dropMessage() {
        Toast toast = Toast.makeText(this, "Hệ thống đang xử lí. Đợi 1 chút.....", Toast.LENGTH_SHORT);
        toast.show();
    }
}