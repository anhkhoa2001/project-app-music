package com.example.appmusic.API;

import android.util.Log;

import com.example.appmusic.Model.Music;
import com.example.appmusic.Model.MusicGenre;
import com.example.appmusic.Model.Singer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DonationApi {

	public static List<MusicGenre> getAllMusicGenre(String call) {
		String json = Rest.get(call);
		Log.v("Music", "JSON RESULT : " + json);
		Type collectionType = new TypeToken<List<MusicGenre>>(){}.getType();
		List<MusicGenre> list = new Gson().fromJson(json, collectionType);
		return list;
	}

	public static List<Singer> getAllAlbum(String call, String count) {
		String json = Rest.get(call + "?count=" + count);
		Log.v("Music", "JSON RESULT : " + json);
		Type collectionType = new TypeToken<List<Singer>>(){}.getType();
		List<Singer> list = new Gson().fromJson(json, collectionType);
		return list;
	}

	public static List<Music> getAllMusicById(String call, String id) {
		String json = Rest.get(call + "?id=" + id);
		Log.v("Music", "JSON RESULT : " + json);
		Type collectionType = new TypeToken<List<Music>>(){}.getType();
		List<Music> list = new Gson().fromJson(json, collectionType);
		return list;
	}


/*
	public static Donation getOne(String call, String id) {
		String json = Rest.get(call + "?id=" + id);
		Log.v("donate", "JSON RESULT : " + json);
		Type objType = new TypeToken<Donation>(){}.getType();

		return new Gson().fromJson(json, objType);
	}

	public static String deleteAll(String call) {
		return Rest.delete(call);
	}

	public static String delete(String call, String id) {
		return Rest.delete(call + "?id=" + id);
	}

	public static String insert(String call, Donation donation) {
		Type objType = new TypeToken<Donation>(){}.getType();
		String json = new Gson().toJson(donation, objType);
  
		return Rest.post(call, json);
	}*/
}
