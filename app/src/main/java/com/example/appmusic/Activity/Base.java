package com.example.appmusic.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class Base extends AppCompatActivity {
    public static String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static boolean isLogged() {
        return token != null;
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
}
