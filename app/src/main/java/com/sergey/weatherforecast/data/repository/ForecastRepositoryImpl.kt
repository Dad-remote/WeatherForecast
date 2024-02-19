package com.sergey.weatherforecast.data.repository

import com.sergey.weatherforecast.data.database.WeatherDatabase
import com.sergey.weatherforecast.data.database.toForecastEntities
import com.sergey.weatherforecast.data.net.OpenWeatherMapService
import com.sergey.weatherforecast.domain.model.ForecastItem
import com.sergey.weatherforecast.domain.repository.ForecastRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class ForecastRepositoryImpl(
    private val database: WeatherDatabase,
    private val service: OpenWeatherMapService,
    private val coroutineScope: CoroutineScope
) : ForecastRepository {

    override fun loadDetails(locationId: Long, day: Long): Flow<ForecastItem> {
        return database.forecastDao().loadDetails(locationId, day)
    }

    override fun loadForecastForLocation(locationId: Long): Flow<List<ForecastItem>> {
        return database.forecastDao().loadByLocationId(locationId)
    }

    override suspend fun syncForecastForLocation(locationId: Long) : Result<List<ForecastItem>> {
        val location = database.locationsDao().getById(locationId)
        val result = service.getForecast(locationId, location.lat, location.lng)
        result.getOrNull()?.let {
            database.forecastDao().saveAll(it.toForecastEntities())
        }
        return result
    }
}
