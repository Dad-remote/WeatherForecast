package com.sergey.weatherforecast.domain.model

import java.io.Serializable

data class WeatherLocation(
    val id: Long,
    val name: String,
    val lat: Float,
    val lng: Float
) : Serializable
