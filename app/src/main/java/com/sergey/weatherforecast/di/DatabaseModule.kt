package com.sergey.weatherforecast.di

import android.app.Application
import com.sergey.weatherforecast.data.database.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): WeatherDatabase = WeatherDatabase.build(application)

}
