package com.sergey.weatherforecast.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast_days")
data class ForecastDayEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
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
)
