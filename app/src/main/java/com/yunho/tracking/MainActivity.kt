package com.yunho.tracking

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
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
import com.yunho.tracking.presentation.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Contract.View {

    private lateinit var presenter: Presenter
    private lateinit var binding: ActivityMainBinding
    private var disposable: Disposable? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        presenter = Presenter(this)

        recyclerView = detail
        recyclerView.layoutManager = LinearLayoutManager(this)

        disposable = getData()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe (
                {
                    val bindingData = ViewModel.BindingData(
                        it.parcelCompanyName,
                        it.parcelInvoice,
                        it.parcelLevel,
                        it.parcelDeliverTime,
                        it.purchaseItemName,
                        it.trackingDetail
                    )

                    Glide.with(this).load(it.purchaseItemImg).into(parcelImg) // Glide -> 이미지 표시

                    viewModel = ViewModel(this, bindingData)
                    binding.data = bindingData
                    binding.viewModel = viewModel

                    setAdapter(it.trackingDetail!!)
                },
                {
                    it.printStackTrace()
                    showAlertDialog(it.message!!)
                }
            )
    }

    private fun getData(): Single<TrackingData>?{
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = manager.activeNetworkInfo?.type

        return if (info == ConnectivityManager.TYPE_WIFI || info == ConnectivityManager.TYPE_MOBILE){
            presenter.getDataFromRemote()
        } else{
            showAlertDialog("네트워크 연결 실패")
            presenter.getDataFromLocal()
        }
    }

    private fun setAdapter(data: List<TrackingData.Detail>){
        val adapter = TrackingAdapter(data)
        recyclerView.adapter = adapter
    }

    private fun showAlertDialog(s: String) {
        val dialog = AlertDialog.Builder(this)

        if (s == "네트워크 연결 실패"){
            dialog.setTitle(s)
                .setMessage("\n기존 데이터를 가져옵니다")
                .setPositiveButton("확인") { _: DialogInterface?, _: Int -> }
                .create()
                .show()
        }
        else{
            dialog.setMessage(s)
                .setPositiveButton("확인") { _: DialogInterface?, _: Int -> finish() }
                .create()
                .show()
        }
    }

    override fun getContext(): Context = this

    override fun onDestroy() {
        super.onDestroy()

        if (disposable!= null && !disposable!!.isDisposed){
            disposable!!.dispose()
        }
    }
}