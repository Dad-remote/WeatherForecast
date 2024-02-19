package com.sergey.weatherforecast.di

import com.sergey.weatherforecast.data.database.WeatherDatabase
import com.sergey.weatherforecast.data.net.OpenWeatherMapApi
import com.sergey.weatherforecast.data.net.OpenWeatherMapService
import com.sergey.weatherforecast.data.repository.ForecastRepositoryImpl
import com.sergey.weatherforecast.data.repository.LocationsRepositoryImpl
import com.sergey.weatherforecast.di.coroutines.IoDispatcher
import com.sergey.weatherforecast.domain.repository.ForecastRepository
import com.sergey.weatherforecast.domain.repository.LocationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideOpenWeatherMapService(
        openWeatherMapApi: OpenWeatherMapApi,
    ) : OpenWeatherMapService {
        return OpenWeatherMapService(openWeatherMapApi)
    }

    @Provides
    @Singleton
    fun provideLocationsRepository(
        database: WeatherDatabase,
        service: OpenWeatherMapService,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocationsRepository {
        return LocationsRepositoryImpl(database, service, dispatcher)
    }

    @Provides
    @Singleton
    fun provideForecastRepository(
        database: WeatherDatabase,
        service: OpenWeatherMapService,
        scope: CoroutineScope
    ): ForecastRepository {
        return ForecastRepositoryImpl(database, service, scope)
    }

}
