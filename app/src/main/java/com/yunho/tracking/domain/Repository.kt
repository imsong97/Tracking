package com.yunho.tracking.domain

import com.yunho.tracking.data.model.TrackingDataEntity
import io.reactivex.Single

interface Repository {

    fun getDataFromRemote(): Single<TrackingDataEntity>?
    fun getDataFromLocal(): Single<TrackingDataEntity>?
}