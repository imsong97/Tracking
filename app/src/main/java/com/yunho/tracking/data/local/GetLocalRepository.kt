package com.yunho.tracking.data.local

import android.content.Context
import com.yunho.tracking.data.local.db.AppDatabase
import com.yunho.tracking.data.model.TrackingDataEntity
import io.reactivex.Single

class GetLocalRepository: LocalDataSource {

    override fun getTrackingData(context: Context): Single<TrackingDataEntity>? {
        return Single.create { // RoomDB는 UI 스레드에서 접근 불가능 -> io 스레드에서 동작하도록
            val db = AppDatabase.getInstance(context)?.userDao()
            val data = db?.getAll()
            if (data != null){ // ???
                it.onSuccess(data)
            }
            else{
                it.onError(Throwable("No Data in Local!!!")) // Room에 데이터가 없을 경우 error 처리
            }
        }
    }
}