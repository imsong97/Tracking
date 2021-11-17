package com.yunho.tracking.domain

import com.yunho.tracking.data.model.TrackingDataEntity
import com.yunho.tracking.domain.model.TrackingData
import io.reactivex.Single

interface Repository {

    fun getTrackingData(): Single<TrackingData>?
}