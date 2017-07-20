package com.gmail.paulovitormelila.moviehub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Paulo on 18/07/2017.
 */

public class WatchlistFragment extends Fragment {
    private RecyclerView mMovieRecyclerView;
    private MovieAdapter mAdapter;
    private int mLastAdapterClickedPosition = -1;

//    Intent intent = getActivity().getIntent();
//    String uuid = intent.getStringExtra("uuid");
//    String title = intent.getStringExtra("title");
//    String poster = intent.getStringExtra("poster");
//    Toast.makeText(getActivity(), uuid, Toast.LENGTH_SHORT).show();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);
        mMovieRecyclerView = (RecyclerView) view.findViewById(R.id.watchlist_recycler_view);
        mMovieRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private class MovieHolder extends RecyclerView.ViewHolder {
        private ImageView mPosterImageView;
        private TextView mTitleTextView;

        private Movie mMovie;

        public MovieHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_movie, parent, false));

            mPosterImageView = (ImageView) itemView.findViewById(R.id.poster_movie);
            mTitleTextView = (TextView) itemView.findViewById(R.id.title_movie);
        }

        public void bind(Movie movie) {
            mMovie = movie;
            mTitleTextView.setText(movie.getTitle());
        }
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
        private List<Movie> mMovies;

        public MovieAdapter(List<Movie> movies) {
            mMovies = movies;
        }

        @Override
        public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new MovieHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(MovieHolder holder, int position) {
//            Intent intent = getActivity().getIntent();
//
//            String uuid = intent.getStringExtra("uuid");
//            String title = intent.getStringExtra("title");
//            String poster = intent.getStringExtra("poster");

            Movie movie = mMovies.get(position);
            holder.bind(movie);
            Picasso.with(getActivity())
                    .load(movie.getPoster())
                    .placeholder(R.drawable.poster_placeholder)
                    .into(holder.mPosterImageView);
        }

        @Override
        public int getItemCount() {
            return mMovies.size();
        }

        public void setMovies(List<Movie> movies) {
            mMovies = movies;
        }
    }

    public void updateUI() {
        MovieLab movieLab = MovieLab.get(getActivity());
        List<Movie> movies = movieLab.getMovies();

        // display text saying the list is empty
        if (movies.size()==0) {
            //Toast.makeText(getActivity(), R.string.watchlist_empty, Toast.LENGTH_LONG).show();
        }

        if (mAdapter == null) {
            mAdapter = new MovieAdapter(movies);
            mMovieRecyclerView.setAdapter(mAdapter);
        } else {
            if (mLastAdapterClickedPosition < 0) {
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter.notifyItemChanged(mLastAdapterClickedPosition);
                mLastAdapterClickedPosition = -1;
            }
            mAdapter.setMovies(movies);
            mAdapter.notifyDataSetChanged();
        }
    }
}
