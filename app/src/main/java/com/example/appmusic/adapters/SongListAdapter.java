package com.example.appmusic.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.api.MusicApi;
import com.example.appmusic.activities.Base;
import com.example.appmusic.activities.PlayMusicActivity;
import com.example.appmusic.models.Music;
import com.example.appmusic.R;
import com.example.appmusic.result.EResponse;

import java.util.List;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder>{
    private Context context;
    private List<Music> songList;
    private int id;
    private String type_id;
    private int statusLike;
    public static Music musicStatic;

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
        statusLike = getStatusLikeByUsername(music);
        holder.songSinger.setText(music.singersToString());
        holder.songName.setText(music.getName());
        holder.songIndex.setText(i + 1 + "");
        holder.songLike.setImageResource(statusLike == 1
                ? R.drawable.iconloved : R.drawable.iconlove);
        holder.viewStatusLike = statusLike;
    }

    public int getStatusLikeByUsername(Music music) {
        return music.getUserByUsername(Base.username) != null ? 1 : 0;
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView songIndex, songSinger, songName;
        ImageView songLike, songMyPlaylist;
        int viewStatusLike;
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
                    if(Base.isLogged()) {
                        new EventHandlerLike(songLike, viewStatusLike).execute("/user/like", String.valueOf(songList
                                        .get(Integer.parseInt(songIndex.getText().toString()) - 1).getId()),
                                Base.token, String.valueOf(viewStatusLike));
                    } else {
                        Toast.makeText(context, EResponse.FAILED.toString(), Toast.LENGTH_LONG).show();
                    }

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
                    intent.putExtra("ID", Integer.parseInt(songIndex.getText().toString()) - 1);
                    intent.putExtra("Musics", listToStrings(songList));
                    musicStatic = songList.get(Integer.parseInt(songIndex.getText().toString()) - 1);
                    context.startActivity(intent);
                }
            });
        }
    }

    public String[] listToStrings(List<Music> music) {
        String[] array = new String[music.size()];
        for(int i = 0; i< music.size(); i++) {
            array[i] = music.get(i).convertToElementString();
        }
        return array;
    }

    private class EventHandlerLike extends AsyncTask<String, Void, String> {
        protected ProgressDialog dialog;
        protected ImageView songLike;
        protected int viewStatusLike;
        public EventHandlerLike(ImageView imageView, int viewStatusLike){
            this.songLike = imageView;
            this.viewStatusLike = viewStatusLike;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                return (String) MusicApi.handlerLikeMusic((String) params[0],(String) params[1], (String) params[2], Integer.parseInt(params[3]));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(viewStatusLike == 0) {
                songLike.setImageResource(R.drawable.iconloved);
                Toast.makeText(context, EResponse.SUCCESS.toString(), Toast.LENGTH_LONG).show();
            } else {
                songLike.setImageResource(R.drawable.iconlove);
                Toast.makeText(context, "UNLIKE" + EResponse.SUCCESS.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
