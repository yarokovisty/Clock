package com.example.clock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.clock.data.ClockViewModel
import com.example.clock.databinding.ActivityMainBinding
import com.example.clock.navigation.App
import com.example.clock.ui.Screens
import com.github.terrakok.cicerone.androidx.AppNavigator




class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val navigatorHolder = App.INSTANCE.navigatorHolder
    private val navigator = AppNavigator(this, R.id.fragmentScreen)
    private val viewModel = ClockViewModel(App.INSTANCE.clockRouter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            viewModel.replaceScreen(Screens.alarmScreen())
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}