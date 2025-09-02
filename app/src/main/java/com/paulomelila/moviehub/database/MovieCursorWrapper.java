package com.paulomelila.moviehub.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.paulomelila.moviehub.Movie;
import com.paulomelila.moviehub.database.MovieDbSchema.MovieTable;
import java.util.UUID;

public class MovieCursorWrapper extends CursorWrapper {

    public MovieCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Movie getMovie () {
        String uuidString = getString(getColumnIndex(MovieTable.Cols.UUID));
        String title = getString(getColumnIndex(MovieTable.Cols.TITLE));
        String poster = getString(getColumnIndex(MovieTable.Cols.POSTER));

        Movie movie = new Movie(UUID.fromString(uuidString));
        movie.setTitle(title);
        movie.setPoster(poster);

        return movie;
    }
}
