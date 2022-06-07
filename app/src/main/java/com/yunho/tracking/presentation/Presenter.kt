package com.yunho.tracking.presentation

import android.content.Context
import com.yunho.tracking.MainActivity
import com.yunho.tracking.R
import com.yunho.tracking.data.TrackingRepository
import com.yunho.tracking.domain.Repository
import com.yunho.tracking.domain.UseCase
import com.yunho.tracking.domain.model.TrackingData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class Presenter(
    private val view: Contract.View,
    private val context: Context,
    private val repo: Repository = TrackingRepository(context)
) : Contract.Presenter {

    private var disposable: Disposable? = null

    override fun getTrackingData() {
        disposable = UseCase(repo).getTrackingData()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ data ->
                if (data == null) {
                    view.initDialog(MainActivity.DialogType.ERROR)
                    return@subscribe
                }

                view.setViewModel(data)
                view.setImg(data.purchaseItemImg ?: "")
                data.trackingDetail?.let {
                    view.setAdapter(it)
                }
            }, {
                it.printStackTrace()
                view.initDialog(MainActivity.DialogType.ERROR)
            })
    }

    override fun dispose() {
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }
}