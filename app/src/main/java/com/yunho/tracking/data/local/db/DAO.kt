package com.yunho.tracking.data.local.db

import androidx.room.*
import com.yunho.tracking.data.model.TrackingDataEntity

@Dao
interface DAO {

    @Query("SELECT * FROM Tracking")
    fun getAll(): TrackingDataEntity

    @Insert
    fun insertAll(data: TrackingDataEntity)

    @Update
    fun update(data: TrackingDataEntity)

    @Delete
    fun delete(data: TrackingDataEntity)
}