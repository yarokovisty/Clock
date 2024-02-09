package com.example.clock.screen

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.example.clock.R
import com.example.clock.data.ClockViewModel
import com.example.clock.data.Constants
import com.example.clock.databinding.FragmentCountDownBinding
import com.example.clock.navigation.App
import com.example.clock.ui.Screens

class CountDownFragment : Fragment() {
    private var _binding: FragmentCountDownBinding? = null
    private val binding get() = _binding!!
    private val viewModel = ClockViewModel(App.INSTANCE.clockRouter)
    private var timer: CountDownTimer? = null
    private var pause = true

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

         binding.apply {
             val menuItem = bnvTimer.menu.findItem(R.id.timer_item)
             menuItem.isChecked = true

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

             npHour.minValue = Constants.MIN_VALUE
             npHour.maxValue = Constants.MAX_VALUE

             npMinute.minValue = Constants.MIN_VALUE
             npMinute.maxValue = Constants.MAX_VALUE

             npSecond.minValue = Constants.MIN_VALUE
             npSecond.maxValue = Constants.MAX_VALUE

             btnLaunch.setOnClickListener {
                 if (pause) startCountDownTimer()
                 else pauseCountDownTimer()
             }
             btnClose.setOnClickListener {
                 closeCountDownTimer()
             }

         }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun startCountDownTimer() = with(binding) {
        contentTimer.isVisible = false
        contentLaunchTimer.isVisible = true
        pause = false

        btnLaunch.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.icon_pause))

        ViewCompat.animate(btnClose)
            .alpha(1f)
            .setDuration(150).interpolator = AccelerateDecelerateInterpolator()
        ViewCompat.animate(btnLaunch)
            .translationX(120f)
            .setDuration(300).interpolator = AccelerateDecelerateInterpolator()


    }

    private fun closeCountDownTimer() = with(binding) {
        contentTimer.isVisible = true
        contentLaunchTimer.isVisible = false
        pause = true
        btnLaunch.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.icon_arrow_play))

        ViewCompat.animate(btnLaunch)
            .translationX(0f)
            .setDuration(300).interpolator = AccelerateDecelerateInterpolator()
        ViewCompat.animate(btnClose)
            .alpha(0f)
            .setDuration(300).interpolator = AccelerateDecelerateInterpolator()


    }

    private fun pauseCountDownTimer() = with(binding) {
        pause = if (pause) {
            btnLaunch.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.icon_pause))
            false
        } else {
            btnLaunch.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.icon_arrow_play))
            true
        }

    }

    private fun showDialogTimer() {

    }

}