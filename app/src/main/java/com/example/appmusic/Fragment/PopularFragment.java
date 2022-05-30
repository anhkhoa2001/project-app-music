package com.example.appmusic.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appmusic.API.MusicApi;
import com.example.appmusic.Activity.SongListActivity;
import com.example.appmusic.Model.MusicGenre;
import com.example.appmusic.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PopularFragment extends Fragment {
    View view;
    HorizontalScrollView horizontalScrollView;
    TextView popular_more;
    List<MusicGenre> populars = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_popular, container, false);
        horizontalScrollView = view.findViewById(R.id.horizontal_Scroll_View);
        popular_more = view.findViewById(R.id.popular_more);
        new GetAllTask().execute("/music-genre/bot");

        return view;
    }

    private void getData(List<MusicGenre> populars){

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(650,450);
        layout.setMargins(10,20,10,30);
        for (int i = 0; i < populars.size(); ++i){
            CardView cardView = new CardView(getActivity());
            cardView.setRadius(10);

            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            new LoadImageURL(imageView)
                    .execute(populars.get(i).getSource());
            TextView textView = new TextView(getContext());
            textView.setText(populars.get(i).getName());
            textView.setTextSize(30);
            textView.setTypeface(Typeface.SERIF, Typeface.BOLD);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(R.color.popular_text));


            cardView.setLayoutParams(layout);

            cardView.addView(imageView);
            cardView.addView(textView);

            int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                Toast.makeText(context,"da click vao banner " + arrayListBanner.get(position).getNameSong(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), SongListActivity.class);
                    intent.putExtra("ID", populars.get(finalI).getId());
                    intent.putExtra("Source", populars.get(finalI).getSource());
                    startActivity(intent);
                }
            });

            linearLayout.addView(cardView);
        }
        horizontalScrollView.addView(linearLayout);

    }

    private class GetAllTask extends AsyncTask<String, Void, List<MusicGenre>> {
        public GetAllTask(){}

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override

        protected List<MusicGenre> doInBackground(String... params) {
            try {
                return (List<MusicGenre>) MusicApi.getAllMusicGenre((String) params[0]);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(List<MusicGenre> result) {
            super.onPostExecute(result);
            Log.v("Music","Size in post" + result.size());

            getData(result);
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