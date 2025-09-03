package com.paulomelila.moviehub;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "movie";

    private Movie mMovie;
    ImageView backdrop;
    ImageView poster;
    TextView title;
    TextView description;
    FloatingActionButton add_to_watchlist;
    private static final String TAG = "MovieDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // For Android 13 (API 33) and above
                mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE, Movie.class);
            } else {
                mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            }

            if (mMovie != null) {
                Log.d(TAG, "onCreate: " + mMovie.getTitle());
            } else {
                // Handle the case where the movie is null, perhaps throw an error or finish activity
                Log.e(TAG, "Movie data is null after retrieving from intent.");
                // You might want to throw an exception or finish the activity here if mMovie is critical
                throw new IllegalArgumentException("Detail activity received null movie parcelable");
            }
        } else {
            throw new IllegalArgumentException("Detail activity must receive a movie parcelable");
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        backdrop = findViewById(R.id.backdrop);
        title = findViewById(R.id.movie_title);
        description = findViewById(R.id.movie_description);
        poster = findViewById(R.id.movie_poster);
        add_to_watchlist = findViewById(R.id.add_to_watchlist_btn);

        title.setText(mMovie.getTitle());
        description.setText(mMovie.getDescription());
        Picasso.get()
                .load(mMovie.getPoster())
                .into(poster);
        Picasso.get()
                .load(mMovie.getBackdrop())
                .into(backdrop);

        watchlistBtnOnClickListener();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        watchlistBtnOnClickListener();
    }

    public void watchlistBtnOnClickListener() {
        MovieLab movieLab = MovieLab.get(getApplicationContext());
        Movie m = movieLab.getMovie(mMovie.getTitle());

        if (m == null) {
            add_to_watchlist.setImageResource(R.mipmap.ic_add_to_watchlist);
            add_to_watchlist.setOnClickListener(v -> {
                // visual part
                add_to_watchlist.setImageResource(R.mipmap.ic_added_to_watchlist);
                addToast();

                // send intent to WatchlistActivity
                MovieLab.get(getApplicationContext()).addMovie(mMovie);

                // go to watchlist
                Intent intent = new Intent(getApplicationContext(), WatchlistActivity.class);
                startActivity(intent);
            });
        } else {
                add_to_watchlist.setImageResource(R.mipmap.ic_added_to_watchlist);
                add_to_watchlist.setOnClickListener(v -> {
                    // visual part
                    add_to_watchlist.setImageResource(R.mipmap.ic_add_to_watchlist);
                    removeToast();

                    // send intent to WatchlistActivity
                    MovieLab.get(getApplicationContext()).deleteMovie(mMovie);

                    // go to watchlist
                    Intent intent = new Intent(getApplicationContext(), WatchlistActivity.class);
                    startActivity(intent);
            });
        }
    }

    public void addToast() {
        Toast added_to_watchlist = Toast.makeText(MovieDetailActivity.this, mMovie.getTitle() + " " + getString(R.string.added_to_watchlist), Toast.LENGTH_LONG);
        TextView message = added_to_watchlist.getView().findViewById(android.R.id.message);
        if (message != null) message.setGravity(Gravity.CENTER);
        added_to_watchlist.show();
    }

    public void removeToast() {
        Toast deleted_from_watchlist = Toast.makeText(MovieDetailActivity.this, mMovie.getTitle() + " " + getString(R.string.removed_from_watchlist), Toast.LENGTH_LONG);
        TextView message = deleted_from_watchlist.getView().findViewById(android.R.id.message);
        if (message != null) message.setGravity(Gravity.CENTER);
        deleted_from_watchlist.show();
    }
}