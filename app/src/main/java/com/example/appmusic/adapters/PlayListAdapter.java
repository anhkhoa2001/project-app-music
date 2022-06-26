package com.example.appmusic.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.R;
import com.example.appmusic.models.AMusic;
import com.example.appmusic.models.Music;
import com.example.appmusic.models.MusicOnDevice;
import com.example.appmusic.notification.MyService;

import java.util.List;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ViewHolder>{
    private Context context;
    private List<AMusic> songList;

    public PlayListAdapter(Context context, List<AMusic> songList) {
        this.songList = songList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlayListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.play_list_item, viewGroup, false);
        return new PlayListAdapter.ViewHolder(view, i);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayListAdapter.ViewHolder holder, int i) {
        AMusic music = songList.get(i);
        holder.songName.setText(music.getName());
        holder.songIndex.setText(i + 1 + "");
        if(music.isType()) {
            holder.songSinger.setText(((Music) music).singersToString());
        } else {
            holder.songSinger.setText(((MusicOnDevice) music).getSinger());
        }
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView songIndex, songSinger, songName;
        public ViewHolder(@NonNull View itemView, int i) {
            super(itemView);
            songIndex = itemView.findViewById(R.id.song_index);
            songSinger = itemView.findViewById(R.id.song_singer);
            songName = itemView.findViewById(R.id.song_name);
        }
    }
}