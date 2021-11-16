package com.yunho.tracking.data.remote

import com.yunho.tracking.data.api.TrackingAPI
import com.yunho.tracking.data.model.TrackingDataEntity
import io.reactivex.Single

class GetRemoteRepository: RemoteDataSource {

    override fun getTrackingData(): Single<TrackingDataEntity>? {
        return TrackingAPI.instance?.api?.getTrackingData()
            ?.map {
                it.body()
            }
    }
}