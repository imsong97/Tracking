package com.yunho.tracking.data

import android.content.Context
import com.yunho.tracking.data.model.TrackingDataEntity
import io.reactivex.Single

interface GetTrackingDataSource {

    fun getTrackingData(context: Context): Single<TrackingDataEntity>?

}