package com.yunho.tracking.data.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.yunho.tracking.data.model.TrackingDataEntity

class Converter {
    @TypeConverter // Room에서 기본 자료형 이외의 형태를 저장하기 위해 사용
    fun listToJson(value: List<TrackingDataEntity.Detail>?) = Gson().toJson(value) // json 형태로 변환
    // Gson: java object를 JSON 표현식으로 변환할 수 있음

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<TrackingDataEntity.Detail>::class.java).toList() // json > list 형태로 변환
}