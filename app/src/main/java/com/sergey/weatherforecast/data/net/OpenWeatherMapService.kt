package com.sergey.weatherforecast.data.net

import com.sergey.weatherforecast.data.database.toForecastItems
import com.sergey.weatherforecast.domain.model.ForecastItem
import kotlinx.coroutines.CancellationException
import retrofit2.http.Query

class OpenWeatherMapService(private val api: OpenWeatherMapApi) {

    suspend fun getForecast(locationId: Long, lat: Float, lng: Float) : Result<List<ForecastItem>> {
        return wrap { api.getForecast(lat, lng).toForecastItems(locationId) }
    }

    suspend fun <T> wrap(function: suspend () -> T): Result<T> {
        return try {
            Result.success(function.invoke())
        } catch (t: Throwable) {
            if (t is CancellationException) {
                throw t
            }
            t.printStackTrace()
            Result.failure(t)
        }
    }

}
