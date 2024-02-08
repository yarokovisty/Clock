package com.example.clock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clock.databinding.ActivityMainBinding
import com.example.clock.navigation.App
import com.example.clock.ui.Screens
import com.github.terrakok.cicerone.androidx.AppNavigator


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val router = App.INSTANCE.router
    private val navigatorHolder = App.INSTANCE.navigatorHolder
    private val navigator = AppNavigator(this, R.id.fragmentScreen)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            router.replaceScreen(Screens.timerScreen())
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}