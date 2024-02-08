package com.example.clock.screen

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.clock.R
import com.example.clock.data.Constants
import com.example.clock.databinding.FragmentCountDownBinding

class CountDownFragment : Fragment() {
    private var _binding: FragmentCountDownBinding? = null
    private val binding get() = _binding!!
    private var timer: CountDownTimer? = null
    private var pause = false

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
             npHour.minValue = Constants.MIN_VALUE
             npHour.maxValue = Constants.MAX_VALUE

             npMinute.minValue = Constants.MIN_VALUE
             npMinute.maxValue = Constants.MAX_VALUE

             npSecond.minValue = Constants.MIN_VALUE
             npSecond.maxValue = Constants.MAX_VALUE

             btnStart.setOnClickListener{
                startCountDownTimer()
             }
             btnClose.setOnClickListener {
                 closeCountDownTimer()
             }
             btnStop.setOnClickListener {
                 pauseCountDownTimer()
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

    }

    private fun closeCountDownTimer() = with(binding) {
        contentTimer.isVisible = true
        contentLaunchTimer.isVisible = false
        pause = false
        btnStop.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.icon_pause))
    }

    private fun pauseCountDownTimer() = with(binding) {
        pause = if (!pause) {
            btnStop.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.icon_arrow_play))
            true
        } else {
            btnStop.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.icon_pause))
            false
        }
    }

}