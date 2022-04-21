package uet.mobile.music;

import android.app.Application;
import android.util.Log;

public class MusicApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("Music Message", "App Started ........");
    }

}
