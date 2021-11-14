package com.yunho.tracking.domain

import com.yunho.tracking.data.model.TrackingDataEntity
import io.reactivex.Single

class UseCase(private val repository: Repository) {

    fun getTrackingData(): Single<TrackingDataEntity>?{
        return repository.getDataFromRemote()
    }
}