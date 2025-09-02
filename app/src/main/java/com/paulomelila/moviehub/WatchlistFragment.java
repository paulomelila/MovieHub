package com.paulomelila.moviehub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.util.List;

public class WatchlistFragment extends Fragment {
    private RecyclerView mMovieRecyclerView;
    private MovieAdapter mAdapter;
    private int mLastAdapterClickedPosition = -1;

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
        mMovieRecyclerView = view.findViewById(R.id.watchlist_recycler_view);
        mMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        updateUI();

        return view;
    }

    private static class MovieHolder extends RecyclerView.ViewHolder {
        private final ImageView mPosterImageView;
        private final TextView mTitleTextView;
        private final ImageView mDeleteButton;

        public MovieHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_movie, parent, false));

            mPosterImageView = itemView.findViewById(R.id.poster_movie);
            mTitleTextView = itemView.findViewById(R.id.title_movie);
            mDeleteButton = itemView.findViewById(R.id.delete_button);
        }
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
        private List<Movie> mMovies;

        public MovieAdapter(List<Movie> movies) {
            mMovies = movies;
        }

        @NonNull
        @Override
        public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new MovieHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder (MovieHolder holder, int position) {
            final Movie movie = mMovies.get(position);
            TextView title = holder.mTitleTextView;
            ImageView poster = holder.mPosterImageView;
            ImageView delete = holder.mDeleteButton;

            // setting the title
            title.setText(movie.getTitle());

            // setting the poster
            Uri uri = Uri.parse(movie.getURL());
            Context context = poster.getContext();
            Picasso.get().load(uri)
                    .placeholder(R.drawable.poster_placeholder)
                    .into(holder.mPosterImageView);

            // deleting movie from watchlist
            delete.setOnClickListener(v -> {
                MovieLab.get(getContext()).deleteMovie(movie);
                removeToast(movie);
                notifyDataSetChanged();
                updateUI();
            });
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
        final MovieLab movieLab = MovieLab.get(getActivity());
        final List<Movie> movies = movieLab.getMovies();

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

    public void removeToast(Movie movie) {
        Toast deleted_from_watchlist = Toast.makeText(getContext(), movie.getTitle() + " " + getString(R.string.removed_from_watchlist), Toast.LENGTH_SHORT);
        TextView message = deleted_from_watchlist.getView().findViewById(android.R.id.message);
        if (message != null) message.setGravity(Gravity.CENTER);
        deleted_from_watchlist.show();
    }
}
