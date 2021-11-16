package com.yunho.tracking.presentation


import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import com.yunho.tracking.MainActivity
import com.yunho.tracking.domain.model.TrackingData
import java.text.SimpleDateFormat

class ViewModel(private val view: MainActivity, private val trackingData: BindingData){

    fun getStatusText(): String = getState(trackingData.trackingDetail!!)

    fun getTimeText(): String = "도착예정시간 : ${trackingData.parcelDeliverTime}"

    fun setProgress(): Int = trackingData.parcelLevel!!

    fun getRegDateText(): String = "등록일 : ${getDate()}"

    // android 종속성 제거
    fun copyText(){
        val clipboard = view.getContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("Invoice", trackingData.parcelInvoice)
        clipboard.setPrimaryClip(clip)

        val dialog = AlertDialog.Builder(view.getContext())
        dialog.setTitle("클립보드 복사 완료")
            .setMessage("\n${clip.getItemAt(0).text}")
            .setPositiveButton("확인") { _: DialogInterface?, _: Int ->  }
            .create()
            .show()
    }

    // android 종속성 제거
    fun arrowClick(){
        val dialog = AlertDialog.Builder(view.getContext())
        dialog.setTitle("앱 종료")
            .setPositiveButton("확인") { _: DialogInterface?, _: Int -> view.finish() }
            .setNegativeButton("취소") { _: DialogInterface?, _: Int ->  }
            .create()
            .show()
    }

    private fun getState(data: List<TrackingData.Detail>): String = data[data.size -1].status.toString()

    private fun getDate(): String{
        val form = SimpleDateFormat("yyyy-MM-dd")
        return form.format(System.currentTimeMillis())
    }

    data class BindingData(
        var parcelCompanyName: String? = null,
        var parcelInvoice: String? = null,
        var parcelLevel: Int? = null,
        var parcelDeliverTime: String? = null,
        var purchaseItemName: String? = null,
        var trackingDetail: List<TrackingData.Detail>?
    )
}