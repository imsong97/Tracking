package com.yunho.tracking.data.remote

import android.content.Context
import com.yunho.tracking.data.local.LocalDataSource
import com.yunho.tracking.data.api.TrackingAPI
import com.yunho.tracking.data.model.TrackingDataEntity
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GetRemoteRepository: RemoteDataSource {

    override fun getTrackingData(): Single<TrackingDataEntity>? {
        return TrackingAPI.instance?.api?.getTrackingData()
            ?.map {
                it.body()
            }
    }
}