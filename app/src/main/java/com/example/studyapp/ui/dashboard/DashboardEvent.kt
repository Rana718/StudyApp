package com.example.studyapp.ui.dashboard

import androidx.compose.ui.graphics.Color
import com.example.studyapp.domain.model.Session
import com.example.studyapp.domain.model.Task


sealed class DashboardEvent {
    data object SaveSubject: DashboardEvent()
    data object DeleteSession: DashboardEvent()
    data class OnDeleteSessionButtonClick(val session: Session): DashboardEvent()
    data class OnTaskIsCompleteChange(val task: Task): DashboardEvent()
    data class OnSubjectCardColorChange(val color: List<Color>): DashboardEvent()
    data class OnSubjectNameChange(val name: String): DashboardEvent()
    data class OnGoalStudyHoursChange(val hours: String): DashboardEvent()
}