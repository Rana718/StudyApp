package com.example.studyapp.ui.dashboard

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyapp.domain.model.Session
import com.example.studyapp.domain.model.Subject
import com.example.studyapp.domain.model.Task
import com.example.studyapp.domain.repo.SessionRepo
import com.example.studyapp.domain.repo.SubjectRepo
import com.example.studyapp.domain.repo.TaskRepo
import com.example.studyapp.util.toHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val subjectRepo: SubjectRepo,
    private val sessionRepo: SessionRepo,
    private val taskRepo: TaskRepo
): ViewModel(){

    private val _state = MutableStateFlow(DashboardState())

    val state = combine(
        _state,
        subjectRepo.getTotalSubjectCount(),
        subjectRepo.getTotalGoalHours(),
        subjectRepo.getAllSubjects(),
        sessionRepo.getTotalSessionsDuration()
    ){ state, subjectCount, totalGoalStudyHours, subjects, totalSessionsDuration ->
        state.copy(
            totalSubjectCount = subjectCount,
            totalGoalStudyHours = totalGoalStudyHours,
            subjects = subjects,
            totalStudiedHours = totalSessionsDuration.toHours()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardState()
    )

    val tasks: StateFlow<List<Task>> = taskRepo.getAllUpcomingTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = emptyList()
        )

    val recentSessions: StateFlow<List<Session>> = sessionRepo.getRecentFiveSessions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = emptyList()
        )

    fun onEvent(event: DashboardEvent){
        when(event){
            DashboardEvent.DeleteSession -> {}
            is DashboardEvent.OnDeleteSessionButtonClick -> {
                _state.update {
                    it.copy(session = event.session)
                }
            }
            is DashboardEvent.OnSubjectCardColorChange -> {
                _state.update {
                    it.copy(subjectCardColors = event.color)
                }
            }
            is DashboardEvent.OnSubjectNameChange -> {
                _state.update {
                    it.copy(subjectName = event.name)
                }
            }
            is DashboardEvent.OnGoalStudyHoursChange -> {
                _state.update {
                    it.copy(goalStudyHours = event.hours)
                }
            }
            is DashboardEvent.OnTaskIsCompleteChange -> {}
            DashboardEvent.SaveSubject -> saveSubject()
        }
    }

    private fun saveSubject(){
        viewModelScope.launch{
            subjectRepo.upsertSubject(
                subject = Subject(
                    name = state.value.subjectName,
                    goalHours = state.value.goalStudyHours.toFloatOrNull() ?: 1f,
                    colors = state.value.subjectCardColors.map { it.toArgb()}
                )
            )
        }
    }

}