package com.yunho.tracking.domain

import com.yunho.tracking.domain.model.TrackingData
import io.reactivex.Single
import org.json.JSONObject
import retrofit2.Response

class UseCase(private val repository: Repository) {

    fun getTrackingData(): Single<TrackingData>?{
        return repository.getDataFromRemote()
    }
}