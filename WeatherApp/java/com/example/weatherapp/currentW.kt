package com.example.weatherapp

import retrofit2.Response
import retrofit2.http.GET

interface currentW {

    @GET("/v1/forecast.json?key=4e7e70617e264e0eb40112916230305&q=Vijayapura&days=2&aqi=no&alerts=no")
    suspend fun getCurrentWeather():Response<Forecast>
}