package com.yunho.tracking.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tracking")
data class TrackingDataEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    var parcelCompanyCode: String? = null,
    var parcelCompanyName: String? = null,
    var parcelInvoice: String? = null,
    var parcelLevel: String? = null,
    var parcelDeliverTime: String? = null,
    var purchaseItemImg: String? = null,
    var purchaseItemName: String? = null,
    var purchaseItemDate: String? = null,
    var trackingDetail: List<Detail>? = null
){
    data class Detail(
        var time: String? = null,
        var where: String? = null,
        var status: String? = null
    )
}
