package com.sergey.weatherforecast.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sergey.weatherforecast.data.database.dao.ForecastDao
import com.sergey.weatherforecast.data.database.dao.LocationsDao
import com.sergey.weatherforecast.data.database.model.ForecastDayEntity
import com.sergey.weatherforecast.data.database.model.LocationEntity

@Database(
    entities = [
        LocationEntity::class,
        ForecastDayEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun locationsDao(): LocationsDao
    abstract fun forecastDao(): ForecastDao

    companion object {
        fun build(applicationContext: Context) = Room.databaseBuilder(
            applicationContext,
            WeatherDatabase::class.java, "weather-database"
        ).fallbackToDestructiveMigration().build()
    }

}
