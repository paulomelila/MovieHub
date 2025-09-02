package com.paulomelila.moviehub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NowPlayingMoviesFragment extends Fragment {
    private MoviesAdapter mAdapter;
    public static final String API_KEY = "ee1daeae6030eca8cd780ff70236c15d";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing_movies, container, false);
        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new MoviesAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        getNowPlayingMovies();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public MovieViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
    public static class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {
        private List<Movie> mMovieList;
        private final LayoutInflater mInflater;
        private final Context mContext;

        public MoviesAdapter(Context context) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
            View view = mInflater.inflate(R.layout.row_movie, parent, false);
            final MovieViewHolder viewHolder = new MovieViewHolder(view);
            view.setOnClickListener(view1 -> {
                int position = viewHolder.getAdapterPosition();
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, mMovieList.get(position));
                mContext.startActivity(intent);
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MovieViewHolder holder, int position) {
            Movie movie = mMovieList.get(position);
            Picasso.get()
                    .load(movie.getPoster())
                    .placeholder(R.drawable.poster_placeholder)
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return (mMovieList == null) ? 0 : mMovieList.size();
        }

        public void setMovieList(List<Movie> movieList) {
            this.mMovieList = new ArrayList<>();
            this.mMovieList.addAll(movieList);
            notifyDataSetChanged();
        }
    }

    private void getNowPlayingMovies() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", API_KEY);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MoviesApiService service = restAdapter.create(MoviesApiService.class);
        service.getNowPlayingMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}
