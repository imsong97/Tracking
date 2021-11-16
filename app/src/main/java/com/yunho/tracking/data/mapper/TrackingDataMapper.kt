package com.yunho.tracking.data.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yunho.tracking.data.model.TrackingDataEntity
import com.yunho.tracking.domain.model.TrackingData

object TrackingDataMapper {

    fun mapToTrackingData(data: TrackingDataEntity): TrackingData{
        return TrackingData(
            data.parcelCompanyCode,
            data.parcelCompanyName,
            data.parcelInvoice,
            data.parcelLevel!!.toInt(),
            data.parcelDeliverTime,
            data.purchaseItemImg,
            data.purchaseItemName,
            data.purchaseItemDate,
            mapToDetail(data.trackingDetail!!)
        )
    }

    private fun mapToDetail(data: List<TrackingDataEntity.Detail>): List<TrackingData.Detail>{
        val gson = Gson()

        return gson.fromJson(gson.toJson(data), Array<TrackingData.Detail>::class.java).toList() // list 형태로 변환
    }
}
/*
* object vs companion object
* object: thread-safe / 접근 시 초기화 / 생성자, 부생성자 사용 불가
* companion object: 클래스가 로드될 때 초기화 / 클래스의 모든 인스턴스가 공유하는 객체를 만들고 싶을때 사용 / 클래스당 한 개 / 인터페이스 내에도 정의 가능 / != static
* */