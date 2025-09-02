package com.paulomelila.moviehub;

import retrofit.Callback;
import retrofit.http.GET;

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
