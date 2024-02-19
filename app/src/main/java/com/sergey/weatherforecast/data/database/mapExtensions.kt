package com.sergey.weatherforecast.data.database

import com.sergey.weatherforecast.data.database.model.ForecastDayEntity
import com.sergey.weatherforecast.data.database.model.LocationEntity
import com.sergey.weatherforecast.data.net.response.ForecastResponse
import com.sergey.weatherforecast.domain.model.ForecastItem
import com.sergey.weatherforecast.domain.model.WeatherLocation

private const val KELVIN_DELTA = 273.15f

fun LocationEntity.toWeatherLocation() : WeatherLocation {
    return WeatherLocation(
        id = this.id,
        name = this.name,
        lat = this.lat,
        lng = this.lng,
    )
}

fun ForecastResponse.toForecastItems(locationId: Long) : List<ForecastItem> {
    return this.list.map {
        ForecastItem(
            locationId = locationId,
            date = it.dt * 1000,
            temperatureDay = it.temp.day - KELVIN_DELTA,
            temperatureNight = it.temp.night - KELVIN_DELTA,
            temperatureFeelsDay = it.feelsLike.day - KELVIN_DELTA,
            temperatureFeelsNight = it.feelsLike.night - KELVIN_DELTA,
            humidity = it.humidity,
            pressure = it.pressure,
            sunrise = it.sunrise * 1000,
            sunset = it.sunset * 1000,
            uvIndex = null,
            icon = it.weather.firstOrNull()?.icon ?: ""
        )
    }
}

fun ForecastItem.toForecastEntity() : ForecastDayEntity {
    return ForecastDayEntity(
        locationId = this.locationId,
        date = this.date,
        sunrise = this.sunrise,
        sunset = this.sunset,
        humidity = this.humidity,
        pressure = this.pressure,
        temperatureDay = this.temperatureDay,
        temperatureNight = this.temperatureNight,
        temperatureFeelsDay = this.temperatureFeelsDay,
        temperatureFeelsNight = this.temperatureFeelsNight,
        icon = this.icon,
        uvIndex = null
    )
}

fun List<ForecastItem>.toForecastEntities() : List<ForecastDayEntity> {
    return this.map { it.toForecastEntity() }
}
