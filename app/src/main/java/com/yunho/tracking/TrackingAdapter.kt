package com.yunho.tracking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yunho.tracking.databinding.TrackingDetailBinding
import com.yunho.tracking.data.model.TrackingDataEntity
import java.text.SimpleDateFormat
import java.time.LocalDate

class TrackingAdapter(private val detail: List<TrackingDataEntity.Detail>): RecyclerView.Adapter<TrackingAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding: TrackingDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.tracking_detail, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var i = itemCount - 1 - position
        var v = true

        if (position != 0){
            v = visible(i)
        }

        val data = detail[i]
        holder.bind(data, v)
    }

    override fun getItemCount(): Int  = detail.size

    inner class ViewHolder(private val binding: TrackingDetailBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(data: TrackingDataEntity.Detail, visible: Boolean){
            binding.date.text = format("date", data.time!!)
            binding.during.visibility = View.INVISIBLE
            if (!visible){
                binding.date.visibility = View.INVISIBLE
            }
            binding.time.text = format("time", data.time!!)
            binding.state.text = data.status
            binding.where.text = data.where
        }
    }

    private fun visible(i: Int): Boolean = format("date", detail[i+1].time!!) != format("date", detail[i].time!!)

    private fun format(s: String, data: String): String{
        val str = data.split(" ")

        return if (s == "date"){
            str[0]
        }
        else {
            str[1]
        }
    }
}