package com.yunho.tracking

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Contract.View {

    /*
    * ?: null이 될 수 있음을 명시적으로 표현
    * lateinit: null로 선언할 필요 없을 경우 / var 사용 / Int, Boolean, Double 등의 기본적인 타입에 적용 불가능
    * by lazy{}: null로 선언할 필요 없을 경우 / val 사용 / 블록 안에서 필요한 코드 작성 -> 선언+초기화 코드
    * */
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

        leftArrow.setOnClickListener {
            showAlertDialog("arrow")
        }
        copy.setOnClickListener {
            copyText()
        }

        disposable = presenter.getTrackingData()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe (
                {
                    val bindingData = TrackingData(
                        it.parcelCompanyCode,
                        it.parcelCompanyName,
                        it.parcelInvoice,
                        it.parcelLevel,
                        it.parcelDeliverTime,
                        it.purchaseItemImg,
                        it.purchaseItemName,
                        it.purchaseItemDate,
                        it.trackingDetail
                    )

                    Glide.with(this).load(it.purchaseItemImg).into(parcelImg) // Glide -> 이미지 표시

                    viewModel = ViewModel(bindingData)
                    binding.data = bindingData
                    binding.viewModel = viewModel

                    setAdapter(it.trackingDetail!!)
                },
                {
                    it.printStackTrace()
                    showAlertDialog("network")// 네트워크 연결 에러
                }
            )
    }

    private fun setAdapter(data: List<TrackingData.Detail>){
        val adapter = TrackingAdapter(data)
        recyclerView.adapter = adapter
    }

    private fun showAlertDialog(s: String) {
        val dialog = AlertDialog.Builder(this)

        if (s == "arrow"){
            dialog.setTitle("앱 종료")
                .setPositiveButton("확인") { _: DialogInterface?, _: Int -> finish() }
                .setNegativeButton("취소") { _: DialogInterface?, _: Int ->  }
                .create()
                .show()
        }
        else{
            dialog.setTitle("네트워크 연결 실패")
                .setMessage("\n데이터를 불러올 수 없음")
                .setPositiveButton("확인") { _: DialogInterface?, _: Int -> finish() }
                .create()
                .show()
        }
    }

    private fun copyText(){
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("Invoice", binding.invoice.text)
        clipboard.setPrimaryClip(clip)

        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("클립보드 복사 완료")
            .setMessage("\n${clip.getItemAt(0).text}")
            .setPositiveButton("확인") { _: DialogInterface?, _: Int ->  }
            .create()
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (disposable!= null && !disposable!!.isDisposed){
            disposable!!.dispose()
        }
    }

//    private fun getData(): Single<TrackingData>?{
//        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val info = manager.activeNetworkInfo?.type
//
//        return if (info == ConnectivityManager.TYPE_WIFI || info == ConnectivityManager.TYPE_MOBILE){ // 네트워크 연결 체크
//            presenter.getDataFromRemote()
//        } else{
//            showAlertDialog("network")
//            presenter.getDataFromLocal()
//        }
//    }
}