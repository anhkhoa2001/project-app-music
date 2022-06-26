package com.example.appmusic.adapters;

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

import com.example.appmusic.activities.SongListActivity;
import com.example.appmusic.models.MusicGenre;
import com.example.appmusic.R;

import androidx.viewpager.widget.PagerAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class BannerAdapter extends PagerAdapter {
    Context context;
    List<MusicGenre> arrayListBanner;

    public BannerAdapter(Context context, List<MusicGenre> arrayListBanner) {
        this.context = context;
        this.arrayListBanner = arrayListBanner;
    }

    @Override
    public int getCount() {
        return arrayListBanner.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_banner,null);

        ImageView imageViewBackgroundBanner = view.findViewById(R.id.image_view_background_banner);
        ImageView imgViewBanner = view.findViewById(R.id.img_view_banner);
        TextView titleBannerSong = view.findViewById(R.id.title_banner_song);
        TextView textViewContent = view.findViewById(R.id.text_view_content);

//        Picasso.get().load(arrayListBanner.get(position).getImageAds()).error(R.drawable.ic_launcher_foreground).into(imageViewBackgroundBanner);
//        Picasso.get().load(arrayListBanner.get(position).getImageSong()).error(R.drawable.ic_launcher_foreground).into(imgViewBanner);

        new LoadImageURL(imageViewBackgroundBanner).execute(arrayListBanner.get(position).getSource());

//        imgViewBanner.setImageResource(R.drawable.suyt_nua_thi_banner);
        titleBannerSong.setText(arrayListBanner.get(position).getName());
        textViewContent.setText(arrayListBanner.get(position).getSlogan());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"da click vao banner " + arrayListBanner.get(position).getNameSong(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, SongListActivity.class);
                intent.putExtra("ID", arrayListBanner.get(position).getId());
                intent.putExtra("Source", arrayListBanner.get(position).getSource());
                intent.putExtra("Name", arrayListBanner.get(position).getName());
                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
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
