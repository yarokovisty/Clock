package com.example.clock.data.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.clock.R
import com.example.clock.databinding.ItemTimerBinding

class TimerAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<TimerAdapter.TimerHolder>() {
    private val listTimerModel = mutableListOf<TimerModel>()
    var selectedTimer: Int? = null

    class TimerHolder(view: View) : ViewHolder(view) {
        val binding = ItemTimerBinding.bind(view)

        fun bind(timerModel: TimerModel) = with(binding) {
            tvNameTimer.text = timerModel.name
            tvTimeTimer.text = timerModel.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_timer, parent, false)

        return TimerHolder(view)
    }

    override fun getItemCount(): Int {
        return listTimerModel.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: TimerHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bind(listTimerModel[position])

        if (selectedTimer == position) {
            holder.binding.cvTimer.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.secondary))
        }
        else {
            holder.binding.cvTimer.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.cardView))
        }

        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
            selectedTimer = position
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addTimer(timerModel: TimerModel) {
        listTimerModel.add(timerModel)
        notifyDataSetChanged()
    }

    fun getTimer(position: Int): TimerModel {
        return listTimerModel[position]
    }
}