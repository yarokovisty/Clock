package com.example.clock.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clock.R
import com.example.clock.data.ClockViewModel
import com.example.clock.databinding.FragmentCountDownBinding
import com.example.clock.databinding.FragmentStopWatchBinding
import com.example.clock.navigation.App
import com.example.clock.ui.Screens

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class StopWatchFragment : Fragment() {
    private var _binding: FragmentStopWatchBinding? = null
    private val binding get() = _binding!!
    private val viewModel = ClockViewModel(App.INSTANCE.clockRouter)

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

        binding.apply {
            val menuItem = bnvStopWatcher.menu.findItem(R.id.stop_watcher_item)
            menuItem.isChecked = true
            bnvStopWatcher.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.alarm_item -> viewModel.replaceScreen(Screens.alarmScreen())
                    R.id.timer_item -> viewModel.replaceScreen(Screens.timerScreen())
                }

                true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}