package com.yunho.tracking.presentation

import com.yunho.tracking.domain.model.TrackingData
import java.text.SimpleDateFormat

class ViewModel(private val trackingData: TrackingData){

    fun getStatusText(): String = getState(trackingData.trackingDetail!!)

    fun getTimeText(): String = "도착예정시간 : ${trackingData.parcelDeliverTime}"

    fun setProgress(): Int = trackingData.parcelLevel!!

    fun getRegDateText(): String = "등록일 : ${getDate()}"

    private fun getState(data: List<TrackingData.Detail>): String = data[data.size -1].status.toString()

    private fun getDate(): String{
        val form = SimpleDateFormat("yyyy-MM-dd")
        return form.format(System.currentTimeMillis())
    }
}