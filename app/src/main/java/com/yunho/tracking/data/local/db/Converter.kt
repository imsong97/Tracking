package com.yunho.tracking.data.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.yunho.tracking.data.model.TrackingDataEntity

class Converter {
    @TypeConverter
    fun listToJson(value: List<TrackingDataEntity.Detail>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<TrackingDataEntity.Detail>::class.java).toList()
}