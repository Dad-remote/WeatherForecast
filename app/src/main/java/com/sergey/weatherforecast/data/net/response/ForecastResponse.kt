package com.sergey.weatherforecast.data.net.response

import kotlinx.serialization.SerialName
import java.io.Serializable

@kotlinx.serialization.Serializable
data class ForecastResponse(
    val city: City,
    val name: String? = null,
    val country: String? = null,
    val cnt: Int,
    val list: List<ForecastItem>
) : Serializable

@kotlinx.serialization.Serializable
data class City(val name: String) : Serializable

@kotlinx.serialization.Serializable
data class ForecastItem(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temperature,
    @SerialName("feels_like")
    val feelsLike: Temperature,
    val pressure: Int,
    val humidity: Int,
    val weather: List<Weather>,
) : Serializable

@kotlinx.serialization.Serializable
data class Temperature(
    val day: Float,
    val night: Float,
) : Serializable

@kotlinx.serialization.Serializable
data class Weather(
    val id: Long,
    val icon: String,
) : Serializable
