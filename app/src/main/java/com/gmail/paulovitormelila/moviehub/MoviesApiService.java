package com.gmail.paulovitormelila.moviehub;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by jose on 10/6/15.
 */
public interface MoviesApiService {
    @GET("/discover/movie")
    void getPopularMovies(Callback<Movie.MovieResult> cb);

    @GET("/discover/movie")
    void getTopRatedMovies(Callback<Movie.MovieResult> cb);

    @GET("/discover/movie")
    void getUpcomingMovies(Callback<Movie.MovieResult> cb);

    @GET("/movie/now_playing")
    void getNowPlayingMovies(Callback<Movie.MovieResult> cb);

    @GET("/search/movie")
    void findMovie(Callback<Movie.MovieResult> cb);
}
