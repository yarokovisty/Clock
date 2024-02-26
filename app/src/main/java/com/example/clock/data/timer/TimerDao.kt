package com.example.clock.data.timer

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TimerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTimer(timer: TimerModel)

    @Delete
    suspend fun deleteSelectedTimers(timers: List<TimerModel>)

    @Query("SELECT * FROM timer_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<TimerModel>>

}