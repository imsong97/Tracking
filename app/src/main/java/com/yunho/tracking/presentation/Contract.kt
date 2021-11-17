package com.yunho.tracking.presentation

import android.content.Context
import com.yunho.tracking.domain.model.TrackingData
import com.yunho.tracking.domain.ViewModel
import io.reactivex.Single

interface Contract {

    interface View{
        fun getContext(): Context
    }

    interface Presenter{
        fun getTrackingData(): Single<TrackingData>?
    }
}