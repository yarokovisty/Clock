package com.example.clock.screen

import android.os.Bundle
import android.os.CountDownTimer
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
import com.example.clock.data.Interval
import com.example.clock.data.rv.IntervalAdapter
import com.example.clock.databinding.FragmentStopWatchBinding
import com.example.clock.navigation.App
import com.example.clock.ui.Screens
import java.util.concurrent.TimeUnit



class StopWatchFragment : Fragment() {
    private var _binding: FragmentStopWatchBinding? = null
    private val binding get() = _binding!!
    private val viewModel = ClockViewModel(App.INSTANCE.clockRouter)
    private var adapter: IntervalAdapter? = null
    private var stopWatch: CountDownTimer? = null
    private var milliseconds: Long = 0
    private var numInterval = 0
    private var pause = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStopWatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = IntervalAdapter()

        binding.apply {
            val menuItem = bnvStopWatcher.menu.findItem(R.id.stop_watcher_item)
            menuItem.isChecked = true

            btnReset.alpha = 0f
            btnInterval.alpha = 0f

            bnvStopWatcher.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.alarm_item -> viewModel.replaceScreen(Screens.alarmScreen())
                    R.id.timer_item -> viewModel.replaceScreen(Screens.timerScreen())
                }

                true
            }

            btnStart.setOnClickListener {
                if (pause) startStopWatch()
                else pauseStopWatch()
            }

            btnReset.setOnClickListener {
                resetStopWatch()
            }

            btnInterval.setOnClickListener {
                numInterval++

                if (numInterval == 1) {

                }

            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        adapter = null
        stopWatch?.cancel()
    }

    private fun startStopWatch() {
        pause = false
        animateStart()
        binding.btnStart.setImageDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.icon_pause
            )
        )

        stopWatch = object : CountDownTimer(Long.MAX_VALUE, 1) {
            override fun onTick(millisUntilFinished: Long) {
                milliseconds++

                updateStopWatch()

            }

            override fun onFinish() {

            }
        }
        stopWatch?.start()

    }

    private fun pauseStopWatch() {
        pause = true
        binding.btnStart.setImageDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.icon_arrow_play
            )
        )

        stopWatch?.cancel()
    }

    private fun resetStopWatch() {
        milliseconds = 0
        numInterval = 0
        adapter?.clearIntervals()
        binding.tvStopWatch.text = "00:00:00"
        pause = true
        stopWatch?.cancel()
        binding.btnStart.setImageDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.icon_arrow_play
            )
        )
        binding.pbTimer.progress = 0
        animateFinish()
    }

    private fun updateStopWatch() {
        val templateMilliseconds = milliseconds % 100
        val seconds = milliseconds / 100 % 60
        val minutes = milliseconds / 6000

        binding.apply {
            tvStopWatch.text = String.format("%02d:%02d,%02d", minutes, seconds, templateMilliseconds)
            pbTimer.progress = (milliseconds % 6000).toInt()
        }

    }


    private fun animateStart() {
        ViewCompat.animate(binding.btnReset)
            .alpha(1f)
            .translationX(240f).interpolator = AccelerateDecelerateInterpolator()
        ViewCompat.animate(binding.btnInterval)
            .alpha(1f)
            .translationX(-240f).interpolator = AccelerateDecelerateInterpolator()
    }

    private fun animateFinish() {
        ViewCompat.animate(binding.btnReset)
            .alpha(0f)
            .translationX(-120f).interpolator = AccelerateDecelerateInterpolator()
        ViewCompat.animate(binding.btnInterval)
            .alpha(0f)
            .translationX(120f).interpolator = AccelerateDecelerateInterpolator()
    }
}