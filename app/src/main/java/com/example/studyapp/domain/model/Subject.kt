package com.example.studyapp.domain.model

import androidx.compose.ui.graphics.Color
import com.example.studyapp.ui.theme.*

data class Subject(
        val name: String,
        val goalHours: Float,
        val colors: List<Color>,
        val subjectId: Int? = null
) {
    companion object {
        val subjectCardColors = listOf(gradient1, gradient2, gradient3, gradient4, gradient5)
    }
}
