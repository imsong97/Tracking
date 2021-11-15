package com.yunho.tracking.domain

import com.yunho.tracking.data.model.TrackingDataEntity
import com.yunho.tracking.domain.model.TrackingData
import io.reactivex.Single

class UseCase(private val repository: Repository) {

    fun getDataFromRemote(): Single<TrackingData>?{
        return repository.getTrackingDataFromRemote()
    }

    fun getDataFromLocal(): Single<TrackingData>?{
        return repository.getTrackingDataFromLocal()
    }
}