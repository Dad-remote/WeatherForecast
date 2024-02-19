package com.sergey.weatherforecast.data.repository

import com.sergey.weatherforecast.data.database.WeatherDatabase
import com.sergey.weatherforecast.data.database.model.LocationEntity
import com.sergey.weatherforecast.data.net.OpenWeatherMapService
import com.sergey.weatherforecast.domain.model.WeatherLocation
import com.sergey.weatherforecast.data.database.toWeatherLocation
import com.sergey.weatherforecast.domain.repository.LocationsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocationsRepositoryImpl(
    private val database: WeatherDatabase,
    private val service: OpenWeatherMapService,
    private val dispatcher: CoroutineDispatcher
) : LocationsRepository {

    override fun loadLocations(): Flow<List<WeatherLocation>> {
        return database.locationsDao().loadAll().map { list -> list.map { it.toWeatherLocation() } }
    }

    override suspend fun saveLocation(name: String, latitude: Float, longitude: Float) : Result<Boolean> {
        return withContext(dispatcher) {
            val newLocation = LocationEntity(
                name = name,
                lat = latitude,
                lng = longitude
            )
            database.locationsDao().upsert(newLocation)
            Result.success(true)
        }
    }

}
