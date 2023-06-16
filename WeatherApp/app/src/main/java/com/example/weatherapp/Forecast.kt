package com.example.weatherapp

data class Forecast(
    val current: Current,
    val forecast: ForecastX,
    val location: Location
)