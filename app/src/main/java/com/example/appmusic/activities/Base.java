package com.example.appmusic.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmusic.models.MusicOnDB;
import com.example.appmusic.db.DBManage;
import com.example.appmusic.models.AMusic;

import java.util.List;


public class Base extends AppCompatActivity {
    public static String token = null;
    public static String username = null;
    public static DBManage dbManage;
    public static AMusic musicStatic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManage = new DBManage(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManage.close();
    }

    public static boolean isLogged() {
        return token != null;
        //true la da dang nhap
    }

   /* private class CheckTask extends AsyncTask<Object, Void, String> {
        public CheckTask() {
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Object... params) {
            String res = null;
            try {
                res = DonationApi.checkAuthentication((String) params[0], (String) params[1]);
            }
            catch(Exception e) {
                Log.v("Music","ERROR : " + e);
                e.printStackTrace();
            }
            return res;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            *//*Toast.makeText(context, result, Toast.LENGTH_LONG).show();*//*
            Log.v("Music", result);
            rs = result;
        }
    }*/

    public static List<AMusic> getAll() {
        return dbManage.getAll();
    }

    public static void getMusic() {
        Log.v("Music", "size in db " + getAll().size());
        for(AMusic music:getAll()) {
            MusicOnDB musicOnDB = (MusicOnDB) music;
            Log.v("Music", "size in db " + musicOnDB.isStatus());
            if(musicOnDB.isStatus()) {
                musicStatic = musicOnDB;
            }
        }
    }

    public static void setMusicStatic(AMusic musicStatic) {
        Base.musicStatic = musicStatic;
    }
}
