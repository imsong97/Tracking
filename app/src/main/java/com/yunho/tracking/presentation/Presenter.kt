package com.yunho.tracking.presentation

import com.yunho.tracking.data.TrackingRepository
import com.yunho.tracking.domain.Repository
import com.yunho.tracking.domain.UseCase
import com.yunho.tracking.domain.model.TrackingData
import com.yunho.tracking.domain.ViewModel
import io.reactivex.Single

class Presenter(private val view: Contract.View, private val repo: Repository = TrackingRepository(view.getContext())): Contract.Presenter{

    override fun getTrackingData(): Single<TrackingData>? {
        return UseCase(repo).getTrackingData()
    }
}