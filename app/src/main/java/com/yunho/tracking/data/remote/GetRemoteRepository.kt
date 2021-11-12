package com.yunho.tracking.data.remote

import com.yunho.tracking.data.GetTrackingDataSource
import com.yunho.tracking.data.api.TrackingAPI
import com.yunho.tracking.data.local.GetLocalRepository
import com.yunho.tracking.domain.model.TrackingData
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

object GetRemoteRepository: GetTrackingDataSource {

    override fun getTrackingData(): Single<Response<TrackingData>>? {
        return TrackingAPI.instance?.api?.getTrackingData()
            ?.subscribeOn(Schedulers.io())
            ?.flatMap {
                if (it.isSuccessful){
                    Single.just(it)
                }
                else{
                    GetLocalRepository.getTrackingData()
                }
            }
//            ?.onErrorReturn {
//                GetLocalRepository.getTrackingData()
//            }
    }
}