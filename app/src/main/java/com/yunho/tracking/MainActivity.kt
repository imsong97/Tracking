package com.yunho.tracking

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yunho.tracking.databinding.ActivityMainBinding
import com.yunho.tracking.data.model.TrackingDataEntity
import com.yunho.tracking.presentation.Contract
import com.yunho.tracking.presentation.Presenter
import com.yunho.tracking.presentation.ViewModel
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        presenter = Presenter(this)

        recyclerView = detail
        recyclerView.layoutManager = LinearLayoutManager(this)

        disposable = presenter.getDataFromRemote()
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
                    showErrorDialog()
                }
            )
    }

    private fun getDate(): String{
        val form = SimpleDateFormat("yyyy-MM-dd")
        return form.format(System.currentTimeMillis())
    }

    private fun getState(data: List<TrackingDataEntity.Detail>?): String{
        return data?.get(data.size -1)?.status.toString()
    }

    private fun setAdapter(data: List<TrackingDataEntity.Detail>?){
        val adapter = TrackingAdapter(data!!)
        recyclerView.adapter = adapter
    }

    private fun showErrorDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("No Data in Local !!!")
            .setPositiveButton("확인") { _: DialogInterface?, _: Int -> finish() }
            .create()
            .show()
    }

    override fun getContext(): Context = this

    override fun onDestroy() {
        super.onDestroy()

        if (disposable!= null && !disposable!!.isDisposed){
            disposable!!.dispose()
        }
    }
}