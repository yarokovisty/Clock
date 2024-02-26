package com.example.clock.data.timer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("timer_table")
data class TimerModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val time: String,
    val h: Int,
    val m: Int,
    val s: Int
)
