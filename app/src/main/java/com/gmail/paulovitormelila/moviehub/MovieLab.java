package com.gmail.paulovitormelila.moviehub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gmail.paulovitormelila.moviehub.database.MovieBaseHelper;
import com.gmail.paulovitormelila.moviehub.database.MovieCursorWrapper;
import com.gmail.paulovitormelila.moviehub.database.MovieDbSchema.MovieTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paulo on 19/07/2017.
 */

public class MovieLab {
    private static MovieLab sMovieLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

//    private LinkedHashMap<UUID, Crime> mCrimes;

    public static MovieLab get(Context context) {
        if (sMovieLab == null) {
            sMovieLab = new MovieLab(context);
        }
        return sMovieLab;
    }

    private MovieLab(Context context) {
        // database
        mContext = context.getApplicationContext();
        mDatabase = new MovieBaseHelper(mContext).getWritableDatabase();
    }

    private static ContentValues getContentValues(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MovieTable.Cols.UUID, movie.getId().toString());
        values.put(MovieTable.Cols.TITLE, movie.getTitle());
        values.put(MovieTable.Cols.POSTER, movie.getPoster());

        return values;
    }

    public void addMovie(Movie m) {
        ContentValues values = getContentValues(m);
        mDatabase.insert(MovieTable.NAME, null, values);
    }

    public void updateMovie(Movie movie) {
        String uuidString = movie.getId().toString();
        ContentValues values = getContentValues(movie);

        mDatabase.update(MovieTable.NAME, values, MovieTable.Cols.UUID + " = ?",
                new String[] {uuidString});
    }

    private MovieCursorWrapper queryMovies (String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                MovieTable.NAME,
                null,         // columns - null selects all columns
                whereClause,
                whereArgs,
                null,         // group by
                null,         // having
                null          // orderBy
        );

        return new MovieCursorWrapper(cursor);
    }

    public void deleteMovie(Movie movie) {
        String title = movie.getTitle();
        mDatabase.delete(MovieTable.NAME,
                MovieTable.Cols.TITLE + " = ?",
                new String[] {title});
    }

    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();

        MovieCursorWrapper cursor = queryMovies(null, null);

        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                movies.add(cursor.getMovie());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return movies;
    }

    public Movie getMovie(String title){
        MovieCursorWrapper cursor = queryMovies(
                MovieTable.Cols.TITLE + " = ?",
                new String[] {title}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getMovie();

        } finally {
            cursor.close();
        }
    }

}
