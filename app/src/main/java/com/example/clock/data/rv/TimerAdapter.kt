package com.example.clock.data.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.clock.R
import com.example.clock.databinding.ItemTimerBinding

class TimerAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<TimerAdapter.TimerHolder>() {
    private val listTimer = mutableListOf<Timer>()

    class TimerHolder(view: View) : ViewHolder(view) {
        private val binding = ItemTimerBinding.bind(view)

        fun bind(timer: Timer) = with(binding) {
            tvNameTimer.text = timer.name
            tvTimeTimer.text = timer.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_timer, parent, false)

        return TimerHolder(view)
    }

    override fun getItemCount(): Int {
        return listTimer.size
    }

    override fun onBindViewHolder(holder: TimerHolder, position: Int) {
        holder.bind(listTimer[position])

        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addTimer(timer: Timer) {
        listTimer.add(timer)
        notifyDataSetChanged()
    }
}