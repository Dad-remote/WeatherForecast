package com.sergey.weatherforecast.domain.model

import java.io.Serializable

data class ForecastItem(
    val locationId: Long,
    val date: Long,
    val temperatureDay: Float,
    val temperatureNight: Float,
    val temperatureFeelsDay: Float,
    val temperatureFeelsNight: Float,
    val humidity: Int,
    val pressure: Int,
    val uvIndex: Float?,
    val sunrise: Long,
    val sunset: Long,
    val icon: String
) : Serializable
