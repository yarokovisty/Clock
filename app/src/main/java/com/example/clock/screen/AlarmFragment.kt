package com.example.clock.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clock.R
import com.example.clock.data.ClockViewModel
import com.example.clock.databinding.FragmentAlarmBinding
import com.example.clock.databinding.FragmentCountDownBinding
import com.example.clock.navigation.App
import com.example.clock.ui.Screens


class AlarmFragment : Fragment() {
    private var _binding: FragmentAlarmBinding? = null
    private val binding get() = _binding!!
    private val viewModel = ClockViewModel(App.INSTANCE.clockRouter)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAlarmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuItem = binding.bnvAlarm.menu.findItem(R.id.alarm_item)
        menuItem.isChecked = true
        binding.bnvAlarm.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.stop_watcher_item -> viewModel.replaceScreen(Screens.stopWatchScreen())
                R.id.timer_item -> viewModel.replaceScreen(Screens.timerScreen())
            }

            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}