package com.yunho.tracking.presentation

import com.yunho.tracking.domain.model.TrackingData
import io.reactivex.Single

interface Contract {

    interface View{
//        fun getContext(): Context // 삭제
    }

    interface Presenter{
        fun getTrackingData(): Single<TrackingData>?
    }
}