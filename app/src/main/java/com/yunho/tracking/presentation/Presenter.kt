package com.yunho.tracking.presentation

import android.content.Context
import com.yunho.tracking.data.TrackingRepository
import com.yunho.tracking.domain.Repository
import com.yunho.tracking.domain.UseCase
import com.yunho.tracking.domain.model.TrackingData
import io.reactivex.Single

class Presenter(private val context: Context, private val repo: Repository = TrackingRepository(context)): Contract.Presenter{

    override fun getTrackingData(): Single<TrackingData>? {
        return UseCase(repo).getTrackingData()
    }
}