package com.example.clock

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.clock.data.TimerRepository
import com.example.clock.data.timer.TimerDataBase
import com.example.clock.data.timer.TimerModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TimerViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<TimerModel>>
    private val repository: TimerRepository

    init {
        val timerDao = TimerDataBase.getDatabase(application).timerDao()
        repository = TimerRepository(timerDao)
        readAllData = repository.readAllData
    }

    fun addTimer(timer: TimerModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTimer(timer)
        }
    }

    fun deleteTimers(timers: List<TimerModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSelectedTimer(timers)
        }
    }
}