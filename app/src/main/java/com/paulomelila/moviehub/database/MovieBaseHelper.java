package com.paulomelila.moviehub.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.paulomelila.moviehub.database.MovieDbSchema.MovieTable;

public class MovieBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "movieBase.db";

    public MovieBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MovieTable.NAME + "(" +
                " _id INTEGER primary key autoincrement, " +
                MovieTable.Cols.UUID + ", " + MovieTable.Cols.TITLE +
                ", " + MovieTable.Cols.POSTER + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
