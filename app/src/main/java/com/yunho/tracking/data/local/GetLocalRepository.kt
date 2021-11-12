package com.yunho.tracking.data.local

import com.yunho.tracking.data.GetTrackingDataSource
import com.yunho.tracking.domain.model.TrackingData
import io.reactivex.Single
import retrofit2.Response

object GetLocalRepository: GetTrackingDataSource {

    override fun getTrackingData(): Single<Response<TrackingData>>? {
        TODO("Not yet implemented")
    }
}