package com.yunho.tracking

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yunho.tracking.databinding.TrackingDetailBinding
import com.yunho.tracking.domain.model.TrackingData

class TrackingAdapter(private val detail: List<TrackingData.Detail>): RecyclerView.Adapter<TrackingAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding: TrackingDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.tracking_detail, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var i = detail.size - 1 - position
        var b = true

        if (position != 0){
            if (detail[i+1].time == detail[i].time){
                b = false
            }
        }
        
        val data = detail[i]
        holder.bind(data, b)
    }

    override fun getItemCount(): Int  = detail.size

    inner class ViewHolder(private val binding: TrackingDetailBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: TrackingData.Detail, b: Boolean){
//            binding.date.text = format("date", data.time!!)
            if (!b){
                binding.date.visibility = View.INVISIBLE
            }
            binding.state.text = data.status
            binding.where.text = data.where
        }

        private fun format(s: String, data: String): String{

//            when(s){
//                "date" ->
//                "time" ->
//            }
            return ""
        }
    }
}