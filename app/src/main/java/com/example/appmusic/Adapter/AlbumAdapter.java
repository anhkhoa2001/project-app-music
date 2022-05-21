package com.example.appmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.Activity.SongListActivity;
import com.example.appmusic.Model.Singer;
import com.example.appmusic.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    Context context;
    List<Singer> albums;

    public AlbumAdapter (Context context, List<Singer> albums) {
        this.context = context;
        this.albums = albums;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.album_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Singer singer = albums.get(position);
        holder.albumSinger.setText(singer.getName());
        new LoadImageURL(holder.albumImage).execute(singer.getMusics().get(1).getImage());
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView albumImage;
        TextView albumName, albumSinger;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            albumImage = itemView.findViewById(R.id.album_image);
            albumSinger = itemView.findViewById(R.id.album_singer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SongListActivity.class);
                    intent.putExtra("Album_ID", albums.get(getLayoutPosition()).getId());
                    intent.putExtra("Source", albums.get(getLayoutPosition()).getMusics().get(0).getSource());
                    context.startActivity(intent);
                }
            });
        }
    }

    private class LoadImageURL extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public LoadImageURL(ImageView rs) {
            this.imageView = rs;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(strings[0]).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }

}
