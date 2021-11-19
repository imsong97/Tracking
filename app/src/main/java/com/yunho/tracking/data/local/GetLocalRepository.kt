package com.yunho.tracking.data.local

import android.content.Context
import com.yunho.tracking.data.local.db.AppDatabase
import com.yunho.tracking.data.model.TrackingDataEntity
import io.reactivex.Single

class GetLocalRepository: LocalDataSource {

    override fun getTrackingData(context: Context): Single<TrackingDataEntity>? {
        return Single.create { // RoomDB는 UI 스레드에서 접근 불가능 -> io 스레드에서 동작하도록
            val db = AppDatabase.getInstance(context)?.userDao()
            val data = db?.getAll() ?: TrackingDataEntity(-1) // 로컬 데이터 null일 경우 처리

            it.onSuccess(data)
        }
    }
}