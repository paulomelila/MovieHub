package com.gmail.paulovitormelila.moviehub;

import android.os.Bundle;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        } else {
            throw new IllegalArgumentException("Detail activity must receive a movie parcelable");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(mMovie.getTitle());

        backdrop = (ImageView) findViewById(R.id.backdrop);
        title = (TextView) findViewById(R.id.movie_title);
        description = (TextView) findViewById(R.id.movie_description);
        poster = (ImageView) findViewById(R.id.movie_poster);

        title.setText(mMovie.getTitle());
        description.setText(mMovie.getDescription());
        Picasso.with(this)
                .load(mMovie.getPoster())
                .into(poster);
        Picasso.with(this)
                .load(mMovie.getBackdrop())
                .into(backdrop);

        addToWatchlist();
    }

    public void addToWatchlist() {
        add_to_watchlist = (FloatingActionButton) findViewById(R.id.add_to_watchlist);
        add_to_watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast added_to_watchlist = Toast.makeText(MovieDetailActivity.this, mMovie.getTitle() + " was added to your watchlist.", Toast.LENGTH_LONG);
                TextView message = (TextView) added_to_watchlist.getView().findViewById(android.R.id.message);
                if (message != null) message.setGravity(Gravity.CENTER);
                added_to_watchlist.show();

                add_to_watchlist.setImageResource(R.mipmap.ic_added_to_watchlist);
            }
        });
    }
}
