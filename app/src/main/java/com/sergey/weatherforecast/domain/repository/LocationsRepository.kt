package com.sergey.weatherforecast.domain.repository

import com.sergey.weatherforecast.domain.model.WeatherLocation
import kotlinx.coroutines.flow.Flow

interface LocationsRepository {
    fun loadLocations(): Flow<List<WeatherLocation>>
    suspend fun saveLocation(name: String, latitude: Float, longitude: Float): Result<Boolean>
}
