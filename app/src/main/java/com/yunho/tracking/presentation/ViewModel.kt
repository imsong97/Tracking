package com.yunho.tracking.presentation

import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ViewModel(private val view: Contract.View): ViewModel() {
    private val presenter = Presenter(view)
    private var disposable: Disposable? = null

    fun getTrackingData(){
    }
}