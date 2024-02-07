package com.example.clock.ui

import com.example.clock.screen.AlarmFragment
import com.example.clock.screen.CountDownFragment
import com.example.clock.screen.StopWatchFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun alarmScreen() = FragmentScreen {AlarmFragment()}
    fun stopWatchScreen() = FragmentScreen {StopWatchFragment()}
    fun timerScreen() = FragmentScreen {CountDownFragment()}
}