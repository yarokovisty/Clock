package com.example.clock.data

import androidx.lifecycle.ViewModel
import com.example.clock.navigation.ClockRouter
import com.github.terrakok.cicerone.androidx.FragmentScreen

class ClockViewModel(private val router: ClockRouter) : ViewModel() {

    fun replaceScreen(fragmentScreen: FragmentScreen) {
        router.replaceItemClock(fragmentScreen)
    }

}