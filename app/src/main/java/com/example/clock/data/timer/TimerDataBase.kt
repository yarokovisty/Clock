package com.example.clock.data.timer

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TimerModel::class], version = 1, exportSchema = false)
abstract class TimerDataBase: RoomDatabase() {

    abstract fun timerDao(): TimerDao

    companion object {
        @Volatile
        private var INSTANCE: TimerDataBase? = null

        fun getDatabase(context: Context): TimerDataBase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TimerDataBase::class.java,
                    "timer_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }


}