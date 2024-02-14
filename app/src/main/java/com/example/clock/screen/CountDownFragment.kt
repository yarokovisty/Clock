package com.example.clock.screen

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.NumberPicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.example.clock.R
import com.example.clock.data.ClockViewModel
import com.example.clock.data.Constants
import com.example.clock.data.rv.OnItemClickListener
import com.example.clock.data.rv.OnItemLongClickListener
import com.example.clock.data.rv.TimerModel
import com.example.clock.data.rv.TimerAdapter
import com.example.clock.databinding.DialogTimerBinding
import com.example.clock.databinding.FragmentCountDownBinding
import com.example.clock.navigation.App
import com.example.clock.ui.Screens
import java.sql.Time
import java.util.Timer
import java.util.TimerTask

class CountDownFragment : Fragment(), OnItemClickListener, OnItemLongClickListener {
    private var _binding: FragmentCountDownBinding? = null
    private val binding get() = _binding!!
    private val viewModel = ClockViewModel(App.INSTANCE.clockRouter)
    private var adapter: TimerAdapter? = null
    private var timer: CountDownTimer? = null
    private var millis = 0L
    private var pause: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCountDownBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TimerAdapter(this@CountDownFragment, this@CountDownFragment)

         binding.apply {
             val menuItem = bnvTimer.menu.findItem(R.id.timer_item)
             menuItem.isChecked = true

             tbTimer.navigationIcon?.setTint(ContextCompat.getColor(requireContext(), R.color.primary_num_picker))

             btnClose.alpha = 0f

             bnvTimer.setOnItemSelectedListener {
                 when(it.itemId) {
                     R.id.alarm_item -> viewModel.replaceScreen(Screens.alarmScreen())
                     R.id.stop_watcher_item -> viewModel.replaceScreen(Screens.stopWatchScreen())
                 }

                 true
             }

             btnAddTimer.setOnClickListener {
                 showDialogTimer()
             }

             npHour.apply {
                 minValue = Constants.MIN_VALUE
                 maxValue = Constants.MAX_VALUE
                 displayedValues = Constants.RANGE
                 setOnScrollListener { _, _ ->
                     if (adapter?.selectedTimer != null) {
                         adapter?.selectedTimer = null
                         tvNamedTimer.setText(R.string.timer)
                         rvTimers.adapter = adapter
                     }

                 }
                 descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

             }


             npMinute.apply {
                 minValue = Constants.MIN_VALUE
                 maxValue = Constants.MAX_VALUE
                 displayedValues = Constants.RANGE
                 setOnScrollListener { _, _ ->
                     if (adapter?.selectedTimer != null) {
                         adapter?.selectedTimer = null
                         tvNamedTimer.setText(R.string.timer)
                         rvTimers.adapter = adapter
                     }
                 }
                 descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

             }

             npSecond.apply {
                 minValue = Constants.MIN_VALUE
                 maxValue = Constants.MAX_VALUE
                 displayedValues = Constants.RANGE
                 setOnScrollListener { _, _ ->
                     if (adapter?.selectedTimer != null) {
                         adapter?.selectedTimer = null
                         tvNamedTimer.setText(R.string.timer)
                         rvTimers.adapter = adapter
                     }

                 }
                 descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

             }

             btnLaunch.setOnClickListener {
                 when (pause) {
                     true -> startTimer(millis)
                     false -> pauseCountDownTimer()
                     else -> startCountDownTimer()
                 }

             }
             btnClose.setOnClickListener {
                 closeCountDownTimer()
             }
            
             

             binding.rvTimers.adapter = adapter
         }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean = with(binding) {
        when(item.itemId) {
            android.R.id.home -> {
                tbTimer.isVisible = false
                adapter?.selectedTimerDel = null
                rvTimers.adapter = adapter
            }
            R.id.item_del -> {}
        }

        return true
    }


    private fun startCountDownTimer() = with(binding) {
        contentTimer.isVisible = false
        contentLaunchTimer.isVisible = true

        val h = npHour.value
        val m = npMinute.value
        val s = npSecond.value

        animateStartTimer()

        if (h != 0 || m != 0 || s != 0) {
            val timeMillis = ((h * 60 + m) * 60 + s) * 1000L
            pbTimer.max = (timeMillis / 1000).toInt()
            startTimer(timeMillis)
        }

    }

    private fun startTimer(timeMillis: Long) = with(binding) {
        pause = false
        btnLaunch.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.icon_pause))
        pbTimer.max = timeMillis.toInt() * 100

        timer?.cancel()
        timer = object : CountDownTimer(timeMillis, 1) {
            override fun onTick(millisUntilFinished: Long) {
                millis = millisUntilFinished
                pbTimer.progress = (millis / timeMillis * 100).toInt()
                val seconds = millisUntilFinished / 1000
                val minutes = (seconds % 3600) / 60
                val hours = seconds / 3600
                val remainingSeconds = seconds % 60

                tvTimer.text = Constants.templeString(hours.toInt(), minutes.toInt(), remainingSeconds.toInt())
            }

            override fun onFinish() {
                Toast.makeText(context, getString(R.string.finish_time), Toast.LENGTH_LONG).show()
                contentTimer.isVisible = true
                contentLaunchTimer.isVisible = false
                pause = null

                animateFinishTimer()
            }

        }.start()
    }

    private fun closeCountDownTimer() = with(binding) {
        timer?.cancel()
        contentTimer.isVisible = true
        contentLaunchTimer.isVisible = false
        pause = null
        btnLaunch.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.icon_arrow_play))

        animateFinishTimer()
    }

    private fun pauseCountDownTimer() = with(binding) {
        pause = true
        btnLaunch.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.icon_arrow_play))
        timer?.cancel()

    }


    private fun showDialogTimer(){
        val builder = AlertDialog.Builder(context)
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_timer, null)
        builder.setView(dialogView)
        val dialogBinding = DialogTimerBinding.bind(dialogView)

        val alertDialog = builder.create()

        dialogBinding.apply {
            dialogHourNumPicker.minValue = Constants.MIN_VALUE
            dialogHourNumPicker.maxValue = Constants.MAX_VALUE
            dialogHourNumPicker.displayedValues = Constants.RANGE

            dialogMinuteNumPicker.minValue = Constants.MIN_VALUE
            dialogMinuteNumPicker.maxValue = Constants.MAX_VALUE
            dialogMinuteNumPicker.displayedValues = Constants.RANGE

            dialogSecondNumPicker.minValue = Constants.MIN_VALUE
            dialogSecondNumPicker.maxValue = Constants.MAX_VALUE
            dialogSecondNumPicker.displayedValues = Constants.RANGE

            btnCancel.setOnClickListener { alertDialog.dismiss() }

            btnReady.setOnClickListener {
                val hours = dialogHourNumPicker.value
                val minutes = dialogMinuteNumPicker.value
                val seconds = dialogSecondNumPicker.value
                val name = etNameTimer.text.toString()

                if ((hours != 0 || minutes != 0 || seconds != 0) && name.isNotEmpty()) {
                    adapter?.addTimer(TimerModel(
                        etNameTimer.text.toString(),
                        Constants.templeString(hours, minutes, seconds),
                        hours,
                        minutes,
                        seconds
                    ))



                    alertDialog.dismiss()
                }

            }

        }

        alertDialog.show()

    }

    private fun animateStartTimer() = with(binding) {
        ViewCompat.animate(btnClose)
            .alpha(1f)
            .setDuration(150).interpolator = AccelerateDecelerateInterpolator()
        ViewCompat.animate(btnLaunch)
            .translationX(120f)
            .setDuration(300).interpolator = AccelerateDecelerateInterpolator()
    }

    private fun animateFinishTimer() {
        binding.apply {
            ViewCompat.animate(btnLaunch)
                .translationX(0f)
                .setDuration(300).interpolator = AccelerateDecelerateInterpolator()
            ViewCompat.animate(btnClose)
                .alpha(0f)
                .setDuration(300).interpolator = AccelerateDecelerateInterpolator()

            btnLaunch.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.icon_arrow_play))
        }

    }

    override fun onItemClick(position: Int) = with(binding) {
        val timer = adapter?.getTimer(position)!!
        npHour.value = timer.h
        npMinute.value = timer.m
        npSecond.value = timer.s
        tvNamedTimer.text = timer.name
    }

    override fun onItemLongClick(position: Int) {
        binding.tbTimer.isVisible = true
    }

}