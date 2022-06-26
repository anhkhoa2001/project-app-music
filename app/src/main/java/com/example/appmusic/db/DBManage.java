package com.example.appmusic.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appmusic.models.MusicOnDB;
import com.example.appmusic.models.AMusic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBManage {
    private SQLiteDatabase database;
    private DBDesigner dbHelper;
    private String nameDB = "music";

    //C:\Users\ADMIN\AppData\Local\Google\AndroidStudio2021.1\device-explorer\emulator-5554\data\data\com.example.donation50\databases

    public DBManage(Context context) {
        dbHelper = new DBDesigner(context);
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public void add(MusicOnDB music) {
        ContentValues values = new ContentValues();
        values.put("name", music.getName());
        values.put("source", music.getSource());
        values.put("image", music.getImage());
        values.put("singer", music.getSinger());
        values.put("type", music.isType());
        values.put("status", music.isStatus());

        database.insert(nameDB, null, values);
    }

    public boolean deleteDonation(int id) {
        return database.delete(nameDB,  "id =" + id, null) > 0;
    }

    public void deleteAll() {
        if(getAll().size() > 0) {
            for(int i=0; i<getAll().size(); i++) {
                deleteDonation(getAll().get(i).getId());
            }
        }
    }

    public List<AMusic> getAll() {
        List<AMusic> aMusics = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + nameDB, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MusicOnDB d = toMusicOnDB(cursor);
            aMusics.add(d);
            cursor.moveToNext();
        }
        cursor.close();
        return aMusics;
    }

    public AMusic getMusic(String id) {
        MusicOnDB musicOnDB = null;
        Cursor cursor = database.rawQuery("SELECT * FROM " + nameDB + " where id = " + id, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            musicOnDB = toMusicOnDB(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return musicOnDB;
    }

    private MusicOnDB toMusicOnDB(Cursor cursor) {
        MusicOnDB pojo = new MusicOnDB();
        pojo.setId(cursor.getInt(0));
        pojo.setName(cursor.getString(1));
        pojo.setSinger(cursor.getString(2));
        pojo.setSource(cursor.getString(3));
        pojo.setImage(cursor.getString(4));
        pojo.setType(cursor.getInt(5) > 0);
        pojo.setStatus(cursor.getInt(6) > 0);
        return pojo;
    }

    public void reset() {
        database.delete(nameDB, null, null);
    }
}
