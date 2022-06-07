package com.yunho.tracking.presentation

import com.yunho.tracking.MainActivity
import com.yunho.tracking.domain.model.TrackingData
import io.reactivex.Single

interface Contract {

    interface View{
        fun setAdapter(data: List<TrackingData.Detail>)
        fun setImg(url: String)
        fun setViewModel(data: TrackingData)
        fun initDialog(type: MainActivity.DialogType)
    }

    interface Presenter{
        fun getTrackingData()
        fun dispose()
    }
}