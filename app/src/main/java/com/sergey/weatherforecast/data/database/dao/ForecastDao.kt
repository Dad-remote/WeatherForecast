package com.sergey.weatherforecast.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.sergey.weatherforecast.data.database.model.ForecastDayEntity
import com.sergey.weatherforecast.domain.model.ForecastItem
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ForecastDao {

    @Query("SELECT * FROM forecast_days")
    abstract suspend fun getAll() : List<ForecastDayEntity>

    @Query("DELETE FROM forecast_days")
    abstract suspend fun deleteAll()

    @Insert
    abstract suspend fun insertAll(items: List<ForecastDayEntity>)

    @Transaction
    open suspend fun saveAll(items: List<ForecastDayEntity>) {
        val oldItems = getAll()
        val dayToId = HashMap<Long, Long>().apply {
            oldItems.forEach { put(it.date, it.id) }
        }
        val itemsToSave = items.mapNotNull { newItem ->
            dayToId[newItem.date]?.let {
                newItem.copy(id = it)
            } ?: newItem
        }
        deleteAll()
        insertAll(itemsToSave)
    }

    @Query("SELECT * FROM forecast_days WHERE locationId = :locationId")
    abstract fun loadByLocationId(locationId: Long): Flow<List<ForecastItem>>

    @Query("SELECT * FROM forecast_days WHERE locationId = :locationId AND date = :day")
    abstract fun loadDetails(locationId: Long, day: Long): Flow<ForecastItem>

}
