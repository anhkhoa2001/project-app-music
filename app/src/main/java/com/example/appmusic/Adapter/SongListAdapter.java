package com.example.appmusic.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.Activity.PlayMusicActivity;
import com.example.appmusic.Model.Music;
import com.example.appmusic.Model.Song;
import com.example.appmusic.R;

import java.util.ArrayList;
import java.util.List;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder>{
    private Context context;
    private List<Music> songList;

    public SongListAdapter(Context context, List<Music> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.song_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Music music = songList.get(i);
        holder.songSinger.setText(music.getSinger());
        holder.songName.setText(music.getName());
        holder.songIndex.setText(i + 1 + "");
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView songIndex, songSinger, songName;
        ImageView songLike, songMyPlaylist;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            songIndex = itemView.findViewById(R.id.song_index);
            songSinger = itemView.findViewById(R.id.song_singer);
            songName = itemView.findViewById(R.id.song_name);
            songLike = itemView.findViewById(R.id.song_like);
            songMyPlaylist = itemView.findViewById(R.id.song_myPlayList);

            songLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    songLike.setImageResource(R.drawable.iconloved);

                }
            });

            songMyPlaylist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    songMyPlaylist.setImageResource(R.drawable.iconsdoubletick);

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PlayMusicActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
