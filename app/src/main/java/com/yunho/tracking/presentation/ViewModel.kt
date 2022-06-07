package com.yunho.tracking.presentation

import com.yunho.tracking.domain.model.TrackingData
import java.text.SimpleDateFormat
import java.util.*

class ViewModel(private val trackingData: TrackingData){

    fun getInvoice(): String = trackingData.parcelInvoice ?: ""

    fun getCompanyName(): String = trackingData.parcelCompanyName ?: ""

    fun getItemName(): String = trackingData.purchaseItemName ?: ""

    fun getStatusText(): String = getState(trackingData.trackingDetail!!)

    fun getTimeText(): String = "도착예정시간 : ${trackingData.parcelDeliverTime}"

    fun setProgress(): Int = trackingData.parcelLevel ?: 0

    fun getRegDateText(): String = "등록일 : ${getDate()}"

    private fun getState(data: List<TrackingData.Detail>): String = data[data.size -1].status.toString()

    private fun getDate(): String{
        val form = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return form.format(System.currentTimeMillis()) ?: "NONE"
    }
}