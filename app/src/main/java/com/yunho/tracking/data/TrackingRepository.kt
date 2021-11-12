package com.yunho.tracking.data

import com.yunho.tracking.data.local.GetLocalRepository
import com.yunho.tracking.data.remote.GetRemoteRepository
import com.yunho.tracking.domain.Repository
import com.yunho.tracking.domain.model.TrackingData
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class TrackingRepository: Repository {

    override fun getDataFromRemote(): Single<TrackingData>?{
        return GetRemoteRepository.getTrackingData()
            ?.subscribeOn(Schedulers.io())
            ?.map {
                it.body()
            }
    }

    override fun getDataFromLocal(): Single<TrackingData>? {
        return GetLocalRepository.getTrackingData()
            ?.subscribeOn(Schedulers.io())
            ?.map {
                it.body()
            }
    }
}