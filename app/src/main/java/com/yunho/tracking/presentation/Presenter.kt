package com.yunho.tracking.presentation

import com.yunho.tracking.data.TrackingRepository
import com.yunho.tracking.domain.UseCase
import com.yunho.tracking.data.model.TrackingDataEntity
import io.reactivex.Single

class Presenter(private val view: Contract.View, private val repo: TrackingRepository = TrackingRepository(view.getContext())): Contract.Presenter {
    override fun getTrackingData(): Single<TrackingDataEntity>? {
        return UseCase(repo).getTrackingData()
    }
}