package com.sergey.weatherforecast.domain.repository

import com.sergey.weatherforecast.domain.model.ForecastItem
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {

    fun loadDetails(locationId: Long, day: Long) : Flow<ForecastItem>
    fun loadForecastForLocation(locationId: Long) : Flow<List<ForecastItem>>

    suspend fun syncForecastForLocation(locationId: Long) : Result<List<ForecastItem>>

}
