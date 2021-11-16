package com.yunho.tracking.data

import android.content.Context
import com.yunho.tracking.data.local.GetLocalRepository
import com.yunho.tracking.data.local.db.AppDatabase
import com.yunho.tracking.data.local.db.DAO
import com.yunho.tracking.data.mapper.TrackingDataMapper
import com.yunho.tracking.data.remote.GetRemoteRepository
import com.yunho.tracking.domain.Repository
import com.yunho.tracking.data.model.TrackingDataEntity
import com.yunho.tracking.domain.model.TrackingData
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class TrackingRepository(private val context: Context): Repository { // context ???
    private lateinit var db: DAO
    private val local = GetLocalRepository()
    private val remote = GetRemoteRepository()

    // 메서드 하나로 -> 내부에서 1.local vs 2.remote
    override fun getTrackingDataFromRemote(): Single<TrackingData>?{
        return remote.getTrackingData()
            ?.flatMap {
                update(it)
                Single.just(TrackingDataMapper.mapToTrackingData(it)) // domain 모델로 변환 -> 의존성 제거
            }
            ?.onErrorResumeNext { // onErrorResumeNext -> 에러 발생 시 원하는 Observable로 대체
                getTrackingDataFromLocal() // Remote 실패 시 Local 에서 데이터 가져옴
            }
    }

    override fun getTrackingDataFromLocal(): Single<TrackingData>?{
        return local.getTrackingData(context)
            ?.flatMap {
                Single.just(TrackingDataMapper.mapToTrackingData(it))
            }
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