package com.example.clock.data.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.clock.R
import com.example.clock.data.timer.TimerModel
import com.example.clock.databinding.ItemTimerBinding

class TimerAdapter(private val listener: OnItemClickListener, private val longClickListener: OnItemLongClickListener) : RecyclerView.Adapter<TimerAdapter.TimerHolder>() {
    private val listTimerModel = mutableListOf<TimerModel>()
    private val indexesToRemove = mutableListOf<Int>()
    var selectedTimer: Int? = null
    var selectedTimerDel = false

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

        holder.binding.apply {
            if (selectedTimer == position && !selectedTimerDel) {
                cvTimer.setCardBackgroundColor(
                    ContextCompat.getColor(holder.itemView.context, R.color.secondary))
            }
            else if (selectedTimerDel) {
                selectedTimer = null
                cbDelete.isVisible = true
                cvTimer.setCardBackgroundColor(
                    ContextCompat.getColor(holder.itemView.context, R.color.cardView))
            }
            else {
                cvTimer.setCardBackgroundColor(
                    ContextCompat.getColor(holder.itemView.context, R.color.cardView))
            }

            cbDelete.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    indexesToRemove.add(position)
                }
                else {
                    indexesToRemove.remove(position)
                }
            }
        }

        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
            selectedTimer = position
            notifyDataSetChanged()
        }

        holder.itemView.setOnLongClickListener {
            longClickListener.onItemLongClick(position)
            selectedTimerDel = true
            notifyDataSetChanged()
            true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addTimer(timerModel: TimerModel) {
        listTimerModel.add(timerModel)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addTimers(timers: List<TimerModel>) {
        listTimerModel.addAll(timers)
        notifyDataSetChanged()
    }

    fun getTimer(position: Int): TimerModel {
        return listTimerModel[position]
    }


    @SuppressLint("NotifyDataSetChanged")
    fun deleteTimers() {
        val indexesToRemoveSorted = indexesToRemove.sortedDescending()

        indexesToRemoveSorted.forEach {
            listTimerModel.removeAt(it)
        }

        clearIndexRemove()
        notifyDataSetChanged()
    }

    fun clearIndexRemove() {
        indexesToRemove.clear()
    }

    fun clearTimers() = listTimerModel.clear()

    fun getSelectedToDeleteTimers(): MutableList<TimerModel> {
        val timersDelete = mutableListOf<TimerModel>()

        for (i in indexesToRemove) {
            timersDelete.add(listTimerModel[i])
        }

        return timersDelete
    }



}