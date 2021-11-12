package com.yunho.tracking.data

import com.yunho.tracking.domain.model.TrackingData
import io.reactivex.Single
import retrofit2.Response

interface GetTrackingDataSource {

    fun getTrackingData(): Single<Response<TrackingData>>?
}