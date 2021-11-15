package com.yunho.tracking.data.local

import android.content.Context
import com.yunho.tracking.data.model.TrackingDataEntity
import io.reactivex.Single

interface LocalDataSource {

    fun getTrackingData(context: Context): Single<TrackingDataEntity>?

}