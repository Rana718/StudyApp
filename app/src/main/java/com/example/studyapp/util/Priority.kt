package com.example.studyapp.util

import androidx.compose.ui.graphics.Color


enum class Priority(val title: String, val color: Color, val value: Int) {
    LOW(title = "Low", color = Color.Green, value = 0),
    MEDIUM(title = "Medium", color = Color.Yellow, value = 1),
    HIGH(title = "High", color = Color.Red, value = 2);

    companion object{
        fun fromInt(value: Int) = values().firstOrNull { it.value == value } ?: MEDIUM
    }
}
