package uet.mobile.music.api;

import android.util.Log;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import uet.mobile.music.models.Music;


public class MusicApi {

	public static List<Music> getAll(String call) {
		String json = Rest.get(call);
		Type collectionType = new TypeToken<List<Music>>(){}.getType();
		
		return new Gson().fromJson(json, collectionType);
	}

	public static Music getOne(String call, String id) {
		String json = Rest.get(call + "?id=" + id);
		Type objType = new TypeToken<Music>(){}.getType();

		return new Gson().fromJson(json, objType);
	}

	public static String deleteAll(String call) {
		return Rest.delete(call);
	}

	public static String delete(String call, String id) {
		return Rest.delete(call + "?id=" + id);
	}

	public static String insert(String call, Music donation) {
		Type objType = new TypeToken<Music>(){}.getType();
		String json = new Gson().toJson(donation, objType);
  
		return Rest.post(call, json);
	}
}
