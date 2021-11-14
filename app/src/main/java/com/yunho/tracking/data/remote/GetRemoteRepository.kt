package com.yunho.tracking.data.remote

import android.content.Context
import com.yunho.tracking.data.GetTrackingDataSource
import com.yunho.tracking.data.api.TrackingAPI
import com.yunho.tracking.data.model.TrackingDataEntity
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

object GetRemoteRepository: GetTrackingDataSource {

    override fun getTrackingData(context: Context): Single<TrackingDataEntity>? {
        return TrackingAPI.instance?.api?.getTrackingData()
            ?.subscribeOn(Schedulers.io())
            ?.map {
                it.body()
            }
    }
}