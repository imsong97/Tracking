package com.yunho.tracking.data

import android.content.Context
import com.yunho.tracking.data.local.GetLocalRepository
import com.yunho.tracking.data.local.db.AppDatabase
import com.yunho.tracking.data.local.db.DAO
import com.yunho.tracking.data.remote.GetRemoteRepository
import com.yunho.tracking.domain.Repository
import com.yunho.tracking.data.model.TrackingDataEntity
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class TrackingRepository(private val context: Context): Repository {
    private lateinit var db: DAO

    override fun getDataFromRemote(): Single<TrackingDataEntity>?{
        return GetRemoteRepository.getTrackingData(context)
            ?.subscribeOn(Schedulers.io())
            ?.map {
                update(it)
                it
            }
            ?.onErrorResumeNext{ // 에러 발생 시 원하는 Observable로 대체
                getDataFromLocal() // Remote 실패 시 Local 에서 데이터 가져옴
            }
    }

    override fun getDataFromLocal(): Single<TrackingDataEntity>? {
        return GetLocalRepository.getTrackingData(context)
            ?.subscribeOn(Schedulers.io())
    }

    private fun update(data: TrackingDataEntity){
        db = AppDatabase.getInstance(context)!!.userDao()

        if (db.getAll() == null){
            db.insertAll(data)
        }
        else{
            db.update(data)
        }
    }
}