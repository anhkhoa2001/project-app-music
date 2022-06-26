package com.example.appmusic.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appmusic.R;
import com.example.appmusic.adapters.PlayListAdapter;
import com.example.appmusic.models.AMusic;
import com.example.appmusic.models.Music;
import com.example.appmusic.models.MusicOnDevice;
import com.example.appmusic.notification.MyService;

import java.util.ArrayList;
import java.util.HashSet;


public class PlayListFragment extends Fragment {
    View view;
    Context context;
    public PlayListAdapter playListAdapter;
    public RecyclerView recyclerView;
    public TextView song_name;
    public TextView song_singer;
    public AMusic music;

    public PlayListFragment(AMusic music) {
        this.music = music;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_list, container, false);
        context = getActivity();
        recyclerView = view.findViewById(R.id.recyclerView_album);
        song_name = view.findViewById(R.id.song_name);
        song_singer = view.findViewById(R.id.song_singer);

        playListAdapter = new PlayListAdapter(context, new ArrayList<>(music.getPlaylists()));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(playListAdapter);

        init(music);
        return view;
    }

    public void init(AMusic music) {
        song_name.setText("Bài hát: " + music.getName());
        if(music.isType()) {
            Music music1 = (Music) music;
            song_singer.setText("Ca sĩ: " + music1.singersToString());
        } else {
            MusicOnDevice musicOnDevice = (MusicOnDevice) music;
            song_singer.setText("Ca sĩ: " + musicOnDevice.getSinger());
        }
    }
}