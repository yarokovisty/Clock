package com.example.clock.data

object Constants {
    const val MIN_VALUE = 0
    const val MAX_VALUE = 59
    val RANGE = Array(60) { i ->
        String.format("%02d", i)
    }
    fun templeString(h: Int, m: Int, s: Int) = String.format("%02d:%02d:%02d", h, m, s)
}