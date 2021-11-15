package com.yunho.tracking.data.remote

import com.yunho.tracking.data.model.TrackingDataEntity
import io.reactivex.Single

interface RemoteDataSource {

    fun getTrackingData(): Single<TrackingDataEntity>?
}