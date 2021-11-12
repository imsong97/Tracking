package com.yunho.tracking.presentation

import com.yunho.tracking.domain.model.TrackingData
import io.reactivex.Single
import retrofit2.Response

interface Contract {

    interface View{

    }

    interface Presenter{
        fun getTrackingData(): Single<TrackingData>?
    }
}