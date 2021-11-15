package com.yunho.tracking.data.local

import android.content.Context
import com.yunho.tracking.data.local.db.AppDatabase
import com.yunho.tracking.data.model.TrackingDataEntity
import io.reactivex.Single

class GetLocalRepository: LocalDataSource {

    override fun getTrackingData(context: Context): Single<TrackingDataEntity>? {
        val db = AppDatabase.getInstance(context)?.userDao()

        return Single.just(db?.getAll())
    }
}