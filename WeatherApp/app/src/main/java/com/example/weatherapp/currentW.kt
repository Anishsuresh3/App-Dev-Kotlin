package com.example.weatherapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface currentW {

    @GET("/v1/forecast.json")
    suspend fun getCurrentWeather(@Query("key") apiKey: String,
                                  @Query("q") location: String,
                                  @Query("days") days: Int,
                                  @Query("aqi") aqi: String,
                                  @Query("alerts") alerts: String):Response<Forecast>
}