package com.yunho.tracking.presentation

import com.yunho.tracking.data.TrackingRepository
import com.yunho.tracking.domain.Repository
import com.yunho.tracking.domain.UseCase
import com.yunho.tracking.domain.model.TrackingData
import io.reactivex.Single
import org.json.JSONObject
import retrofit2.Response

class Presenter(private val view: Contract.View, private val repo: TrackingRepository = TrackingRepository()): Contract.Presenter {
    override fun getTrackingData(): Single<TrackingData>? {
        return UseCase(repo).getTrackingData()
    }
}