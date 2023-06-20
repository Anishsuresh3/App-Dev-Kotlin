package com.example.movie

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesGet {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<Movies>
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<Movies>
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<Movies>
    @GET("search/movie")
    suspend fun getMovie(
        @Query("query") query: String="Movie",
        @Query("include_adult") include_adult: Boolean=false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<Movies>
    @GET("find")
    suspend fun getMoviesById(
        @Query("") external_id : String = "",
        @Query("external_source") external_source: String = "tvdb_id"
    ): Response<Movies>
}