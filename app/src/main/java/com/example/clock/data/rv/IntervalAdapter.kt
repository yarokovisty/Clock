package com.example.clock.data.rv

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.clock.R
import com.example.clock.data.Interval
import com.example.clock.databinding.ItemIntervalBinding
import kotlin.math.min

class IntervalAdapter : RecyclerView.Adapter<IntervalAdapter.IntervalHolder>() {
    private val intervals = mutableListOf<Interval>()
    inner class IntervalHolder(view: View): ViewHolder(view) {
        val binding = ItemIntervalBinding.bind(view)

        fun bind(interval: Interval) = with(interval) {
            var milli = time % 100
            var s = time / 100 % 60
            var m = time / 6000
            val templateTime = String.format("%02d:%02d,%02d", m, s, milli)

            val templateTimeMargin = if (id == 1) {
                templateTime
            } else {
                val milliMargin = interval.time - intervals[id - 2].time
                milli = milliMargin % 100
                s = milliMargin / 100 % 60
                m = milliMargin / 6000
                val templateSegmentTime = String.format("%02d:%02d,%02d", m, s, milli)

                templateSegmentTime
            }


            binding.apply {
                tvIdInterval.text = String.format("%02d", id)
                tvSegmentTime.text = templateTimeMargin
                tvTime.text = templateTime
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntervalHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_interval, parent, false)

        return IntervalHolder(view)
    }

    override fun getItemCount(): Int {
        return intervals.size
    }

    override fun onBindViewHolder(holder: IntervalHolder, position: Int) {
        holder.bind(intervals[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addInterval(interval: Interval) {
        intervals.add(interval)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearIntervals() {
        intervals.clear()
        notifyDataSetChanged()
    }


}