package com.yunho.tracking

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
    private lateinit var presenter: Contract.Presenter
    private lateinit var binding: ActivityMainBinding

    enum class DialogType {
        ERROR, FINISH
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        presenter = Presenter(this, this)

        binding.leftArrow.setOnClickListener(listener)
        binding.copy.setOnClickListener(listener)

        presenter.getTrackingData()
    }

    private val listener = View.OnClickListener {
        when(it.id) {
            R.id.leftArrow -> initDialog(DialogType.FINISH)
            R.id.copy -> copyText()
        }
    }

    override fun setAdapter(data: List<TrackingData.Detail>){
        binding.detail.layoutManager = LinearLayoutManager(this)
        binding.detail.adapter = TrackingAdapter(data)
    }

    override fun setImg(url: String) {
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.parcelImg) // Glide -> 이미지 표시
    }

    override fun setViewModel(data: TrackingData) {
        binding.viewModel = ViewModel(data)
    }

    override fun initDialog(type: DialogType) {
        val dialog = AlertDialog.Builder(this).run {
            this.setPositiveButton(getString(R.string.commit)) { _, _ -> finish() }
            when(type) {
                DialogType.ERROR -> this.setMessage(getString(R.string.base_error))
                DialogType.FINISH -> {
                    this.setTitle(getString(R.string.close_app))
                        .setNegativeButton(getString(R.string.cancel)) { _, _ ->  }
                }
            }
            this
        }

        dialog.show()
    }

    private fun copyText(){
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("Invoice", binding.invoice.text)
        clipboard.setPrimaryClip(clip)

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.title_copy))
            .setMessage("\n${clip.getItemAt(0).text}")
            .setPositiveButton(getString(R.string.commit)) { _, _ -> }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
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