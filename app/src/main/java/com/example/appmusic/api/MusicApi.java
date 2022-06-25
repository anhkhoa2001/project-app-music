package com.example.appmusic.api;

import android.util.Log;

import com.example.appmusic.models.Music;
import com.example.appmusic.models.MusicGenre;
import com.example.appmusic.models.Singer;
import com.example.appmusic.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MusicApi {
/*
	public static List<MusicGenre> getAllMusicGenre(String call) {
		return new AppApi<MusicGenre>().getAllEntity(call);
	}

	public static List<Singer> getAllAlbum(String call, String count) {
		return new AppApi<Singer>().getAllEntity(call + "?count=" + count);
	}

	public static List<Music> getAllMusicById(String call, String id) {
		return new AppApi<Music>().getAllEntity(call + "?id=" + id);
	}

	public static List<Music> getAllMusic(String call) {
		return new AppApi<Music>().getAllEntity(call);
	}

	public static String handlerLikeMusic(String call, String id, String token, int status) {
		String json = Rest.get(call + "?id=" + id + "&token=" + token + "&numberStatus=" + status);
		Log.v("Music", "JSON RESULT : " + json);

		return json;
	}


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
	public static String login(String call, User user) {
		return new AppApi<User>().postEntity(call, user);
	}

	public static String register(String call, User user) {
		return new AppApi<User>().postEntity(call, user);
	}

	public static String checkAuthentication(String call, String token) {
		Type objType = new TypeToken<User>(){}.getType();
		String json = new Gson().toJson(token, objType);

		return Rest.post(call, json);
	}

	public static User getUserByToken(String call, String token) {
		Type objType = new TypeToken<User>(){}.getType();
		String json = Rest.post(call, token);
		User userJson = new Gson().fromJson(json, objType);

		return userJson;
	}*/

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
		setTypeToMusic(list);
		return list;
	}

	public static void setTypeToMusic(List<Music> musics) {
		for(Music music : musics) {
			music.setType(true);
		}
	}

	public static List<Music> getAllMusicByToken(String call, String token) {
		String json = Rest.get(call + "?token=" + token);
		Log.v("Music", "JSON RESULT : " + json);
		Type collectionType = new TypeToken<List<Music>>(){}.getType();
		List<Music> list = new Gson().fromJson(json, collectionType);
		setTypeToMusic(list);
		return list;
	}

	public static List<Music> getAllMusic(String call) {
		String json = Rest.get(call);
		Log.v("Music", "JSON RESULT : " + json);
		Type collectionType = new TypeToken<List<Music>>(){}.getType();
		List<Music> list = new Gson().fromJson(json, collectionType);
		setTypeToMusic(list);
		return list;
	}

	public static String handlerLikeMusic(String call, String id, String token, int status) {
		String json = Rest.get(call + "?id=" + id + "&token=" + token + "&numberStatus=" + status);
		Log.v("Music", "JSON RESULT : " + json);

		return json;
	}

	public static String updateListen(String call, Music music) {
		Type objType = new TypeToken<Music>(){}.getType();
		String json = new Gson().toJson(music, objType);

		Log.v("Music", "JSON UPDATE LISTEN : " + json);

		return Rest.put(call, json);
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
    */
	public static String login(String call, User user) {
		Type objType = new TypeToken<User>(){}.getType();
		String json = new Gson().toJson(user, objType);

		Log.v("Music", "JSON LOGIN : " + json);

		return Rest.post(call, json);
	}

	public static String register(String call, User user) {
		Type objType = new TypeToken<User>(){}.getType();
		String json = new Gson().toJson(user, objType);

		Log.v("Music", "JSON REGISTER : " + json);

		return Rest.post(call, json);
	}

	public static String checkAuthentication(String call, String token) {
		Type objType = new TypeToken<User>(){}.getType();
		String json = new Gson().toJson(token, objType);

		return Rest.post(call, json);
	}

	public static User getUserByToken(String call, String token) {
		Type objType = new TypeToken<User>(){}.getType();
		String json = Rest.post(call, token);
		User userJson = new Gson().fromJson(json, objType);

		return userJson;
	}
}
