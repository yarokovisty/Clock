package com.example.clock.data.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clock.R
import com.example.clock.data.Interval
import com.example.clock.databinding.ItemIntervalBinding
import kotlin.math.min

class IntervalAdapter : RecyclerView.Adapter<IntervalAdapter.IntervalHolder>() {
    private val intervals = mutableListOf<Interval>()

    class IntervalHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemIntervalBinding.bind(view)

        fun bind(interval: Interval) {
            val milliseconds = interval.time % 100
            val seconds = interval.time / 100 % 60
            val minutes = interval.time / 6000
            val templateTime = String.format("%02d:%02d,%02d", minutes, seconds, milliseconds)

            val segmentTimeMilliseconds = interval.time - interval.previousTime
            val segmentMilliseconds = segmentTimeMilliseconds % 100
            val segmentSeconds = segmentTimeMilliseconds / 100 % 60
            val segmentMinutes = segmentTimeMilliseconds / 6000
            val templateSegmentTime = String.format(
                "+%02d:%02d,%02d",
                segmentMilliseconds,
                segmentSeconds,
                segmentMinutes
            )

            binding.apply {
                tvIdInterval.text = String.format("%02d", interval.id)
                tvSegmentTime.text = templateSegmentTime
                tvSegmentTime.text = templateTime

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntervalHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_interval, parent, false)

        return IntervalHolder(view)
    }

    override fun getItemCount(): Int {
        return intervals.size
    }

    override fun onBindViewHolder(holder: IntervalHolder, position: Int) {
        holder.bind(intervals[position])
    }

    fun addInterval(interval: Interval) {
        intervals.add(interval)
    }

    fun clearIntervals() = intervals.clear()

    fun getPreviousIntervalTime(position: Int) = intervals[position - 1].time
}