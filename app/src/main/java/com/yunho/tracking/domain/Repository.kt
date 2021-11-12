package com.yunho.tracking.domain

import com.yunho.tracking.domain.model.TrackingData
import io.reactivex.Single
import retrofit2.Response

interface Repository {

    fun getDataFromRemote(): Single<TrackingData>?
    fun getDataFromLocal(): Single<TrackingData>?
}