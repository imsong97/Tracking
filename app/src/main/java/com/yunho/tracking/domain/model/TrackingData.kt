package com.yunho.tracking.domain.model


data class TrackingData(
    var parcelCompanyCode: String? = null,
    var parcelCompanyName: String? = null,
    var parcelInvoice: String? = null,
    var parcelLevel: Int? = null,
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
