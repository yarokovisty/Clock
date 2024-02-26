package com.example.clock.data

import androidx.lifecycle.LiveData
import com.example.clock.data.timer.TimerDao
import com.example.clock.data.timer.TimerModel

class TimerRepository(private val timerDao: TimerDao) {

    val readAllData: LiveData<List<TimerModel>> = timerDao.readAllData()

    suspend fun addTimer(timer: TimerModel) {
        timerDao.addTimer(timer)
    }

    suspend fun deleteSelectedTimer(timerIds: List<TimerModel>) {
        timerDao.deleteSelectedTimers(timerIds)
    }
}