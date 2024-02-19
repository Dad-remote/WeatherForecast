package com.sergey.weatherforecast.data.net

import com.sergey.weatherforecast.data.net.response.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApi {

    companion object {
        const val ROOT = "https://api.openweathermap.org"
    }

    @GET("/data/2.5/forecast/daily?cnt=7&appid=4aff0d93fc6fb6fd2fd195632dc9bbc1")
    suspend fun getForecast(@Query("lat") lat: Float, @Query("lon") lng: Float) : ForecastResponse
}
