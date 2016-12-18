package com.example.emil.showsmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TVShowsDB extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TVShows";
    private static final String TABLE_SHOWS = "shows";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PREMIERED = "premiered";
    private static final String KEY_STATUS = "status";
    private static final String KEY_COUNTRY_CODE = "code";
    private static final String KEY_NEXT_EP_AIRDATE = "airdate";




    public TVShowsDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SHOWS_TABLE = "CREATE TABLE " + TABLE_SHOWS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PREMIERED + " TEXT," + KEY_STATUS + " TEXT," + KEY_COUNTRY_CODE + " TEXT," + KEY_NEXT_EP_AIRDATE + " TEXT" +  ")";
        db.execSQL(CREATE_SHOWS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOWS);
        onCreate(db);
    }


    public void addShow(TVShow show) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, show.getId());
        values.put(KEY_NAME, show.getName());
        values.put(KEY_PREMIERED, show.getPremiereYear());
        values.put(KEY_STATUS, show.getStatus());
        values.put(KEY_COUNTRY_CODE, show.getCountryCode());
        values.put(KEY_NEXT_EP_AIRDATE, show.getAirdate());
        db.insert(TABLE_SHOWS, null, values);
        db.close();
    }


    public List<TVShow> getAllShows() {
        List<TVShow> showList = new ArrayList<TVShow>();
        String selectQuery = "SELECT * FROM " + TABLE_SHOWS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TVShow show = new TVShow();
                show.setId(Integer.parseInt(cursor.getString(0)));
                show.setName(cursor.getString(1));
                show.setPremiereYear(cursor.getString(2));
                show.setStatus(cursor.getString(3));
                show.setCountryCode(cursor.getString(4));
                show.setAirdate(cursor.getString(5));

                showList.add(show);
            } while (cursor.moveToNext());
        } return showList;
    }

    public int updateShows(TVShow show) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, show.getId());
        values.put(KEY_NAME, show.getName());
        values.put(KEY_PREMIERED, show.getPremiereYear());
        values.put(KEY_STATUS, show.getStatus());
        values.put(KEY_COUNTRY_CODE, show.getCountryCode());
        values.put(KEY_NEXT_EP_AIRDATE, show.getAirdate());

        return db.update(TABLE_SHOWS, values, KEY_NAME + " = ?",
                new String[]{String.valueOf(show.getName())});
    }

    public void deleteShow(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHOWS, KEY_NAME + " = ?",
                new String[] { title });
        db.close();
    }
}