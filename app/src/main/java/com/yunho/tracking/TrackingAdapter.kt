package com.yunho.tracking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yunho.tracking.databinding.TrackingDetailBinding
import com.yunho.tracking.domain.model.TrackingData

class TrackingAdapter(private val detail: List<TrackingData.Detail>) : RecyclerView.Adapter<TrackingAdapter.ViewHolder>() {

    private lateinit var context: Context
    private val TYPE_DATE = 1
    private val TYPE_TIME = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding: TrackingDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.tracking_detail, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val i = itemCount - 1 - position

        if (position == 0){
            holder.bind(detail[i], true)
        } else {
            holder.bind(detail[i], isVisible(i))
        }
    }

    override fun getItemCount(): Int = detail.size

    inner class ViewHolder(private val binding: TrackingDetailBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TrackingData.Detail, visible: Boolean) {
            if (!visible){
                binding.date.visibility = View.INVISIBLE
            } else {
                binding.date.text = format(TYPE_DATE, data.time ?: "")
            }
            binding.during.visibility = View.GONE

            binding.time.text = format(TYPE_TIME, data.time ?: "")
            binding.state.text = data.status ?: ""
            binding.where.text = data.where ?: ""
        }
    }

    private fun isVisible(i: Int): Boolean = format(TYPE_DATE, detail[i+1].time ?: "") != format(TYPE_DATE, detail[i].time ?: "")

    private fun format(type: Int, data: String): String {
        return if (data.isEmpty()) {
            "00:00:00"
        } else {
            data.split(" ").run {
                if (type == TYPE_DATE) {
                    this[0]
                } else {
                    this[1]
                }
            }
        }
    }
}