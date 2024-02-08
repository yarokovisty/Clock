package com.example.clock.navigation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface ClockRouter {
    fun replaceItemClock(fragment: FragmentScreen)
}

class ClockRouterImpl(private val router: Router) : ClockRouter {


    override fun replaceItemClock(fragment: FragmentScreen) {
        router.replaceScreen(fragment)
    }
}