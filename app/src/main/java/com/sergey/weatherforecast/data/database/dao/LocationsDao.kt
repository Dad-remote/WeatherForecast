package com.sergey.weatherforecast.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sergey.weatherforecast.data.database.model.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationsDao {

    @Query("SELECT * FROM locations")
    fun loadAll(): Flow<List<LocationEntity>>

    @Upsert
    fun upsert(location: LocationEntity)

    @Query("SELECT * FROM locations WHERE id = :locationId")
    suspend fun getById(locationId: Long): LocationEntity

}
