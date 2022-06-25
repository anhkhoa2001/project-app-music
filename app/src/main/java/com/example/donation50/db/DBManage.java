package com.example.donation50.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.donation50.activity.Base;
import com.example.donation50.model.Donation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBManage {
    private SQLiteDatabase database;
    private DBDesigner dbHelper;
    private String nameDB = "donations";

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

    public void add(Donation d) {
        ContentValues values = new ContentValues();
        values.put("amount", d.amount);
        values.put("method", d.method);
        values.put("upvotes", d.upvotes);

        database.insert(nameDB, null, values);
    }

    public boolean deleteDonation(int id) {
        return database.delete(nameDB,  "id =" + id, null) > 0;
    }

    public List<Donation> getAll() {
        List<Donation> donations = new ArrayList<Donation>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + nameDB, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Donation d = toDonation(cursor);
            donations.add(d);
            cursor.moveToNext();
        }
        cursor.close();
        return donations;
    }

    public Donation getDonation(String id) {
        Donation donation = null;
        Cursor cursor = database.rawQuery("SELECT * FROM " + nameDB + " where id = " + id, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            donation = toDonation(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return donation;
    }

    private Donation toDonation(Cursor cursor) {
        Donation pojo = new Donation();
        pojo.setId(cursor.getInt(0));
        pojo.setAmount(cursor.getInt(1));
        pojo.setMethod(cursor.getString(2));
        pojo.setUpvotes(cursor.getInt(3));
        return pojo;
    }

    public void reset() {
        database.delete(nameDB, null, null);
    }
}
