package com.example.appmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.Activity.PlayMusicActivity;
import com.example.appmusic.Model.Music;
import com.example.appmusic.R;

import java.util.List;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder>{
    private Context context;
    private List<Music> songList;
    private int id;
    private String type_id;

    public SongListAdapter(Context context, List<Music> songList, int id, String type_id) {
        this.context = context;
        this.songList = songList;
        this.id = id;
        this.type_id = type_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.song_list_item, viewGroup, false);
        return new ViewHolder(view, i);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Music music = songList.get(i);
        holder.songSinger.setText(music.singersToString());
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
        ViewHolder(@NonNull View itemView, int i) {
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
                    intent.putExtra("Music_Source", songList.get(Integer.parseInt(songIndex.getText().toString()) - 1).getSource());
                    intent.putExtra("Music_ID", id);
                    intent.putExtra("Type_ID", type_id);
                    intent.putExtra("Music_Source_Prior", Integer.parseInt(songIndex.getText().toString()) - 1 <= 0 ?
                                            null : songList.get(Integer.parseInt(songIndex.getText().toString()) - 2).getSource());
                    intent.putExtra("Music_Source_After", Integer.parseInt(songIndex.getText().toString()) >= songList.size() ?
                                            null : songList.get(Integer.parseInt(songIndex.getText().toString()) - 1).getSource());
                    intent.putExtra("ID", Integer.parseInt(songIndex.getText().toString()) - 1);
                    intent.putExtra("Musics", listToStrings(songList));
                    context.startActivity(intent);
                }
            });
        }
    }

    public String[] listToStrings(List<Music> musics) {
        String[] array = new String[musics.size()];
        for(int i=0; i<musics.size(); i++) {
            array[i] = musics.get(i).getName() + "khoanamhet" + musics.get(i).getSource();
        }
        return array;
    }
}
