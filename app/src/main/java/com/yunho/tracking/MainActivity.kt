package com.yunho.tracking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yunho.tracking.databinding.ActivityMainBinding
import com.yunho.tracking.domain.model.TrackingData
import com.yunho.tracking.presentation.Contract
import com.yunho.tracking.presentation.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity(), Contract.View {

    private lateinit var presenter: Presenter
    private lateinit var binding: ActivityMainBinding
    private var disposable: Disposable? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        presenter = Presenter(this)

        recyclerView = detail
        recyclerView.layoutManager = LinearLayoutManager(this)

        disposable = presenter.getTrackingData()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe (
                {
                    tv_status.text = getState(it.trackingDetail)
                    tv_parcelDeliverTime.text = "도착 예정 시간 : ${it.parcelDeliverTime}"

                    Glide.with(this).load(it.purchaseItemImg).into(parcelImg) // Glide -> 이미지 표시
                    itemName.text = it.purchaseItemName
                    regDate.text = "등록일 : ${getDate()}"

                    companyName.text = it?.parcelCompanyName
                    invoice.text = it.parcelInvoice

                    setAdapter(it.trackingDetail)
                },
                {
                    it.printStackTrace()
                    println("Error")
                }
            )
    }

    private fun getDate(): String{
        val form = SimpleDateFormat("yyyy-MM-dd")
        return form.format(System.currentTimeMillis())
    }

    private fun getState(data: List<TrackingData.Detail>?): String{
        return data?.get(data.size -1)?.status.toString()
    }

    private fun setAdapter(data: List<TrackingData.Detail>?){
        val adapter = TrackingAdapter(data!!)
        recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}