package com.example.studyapp.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyapp.domain.repo.SessionRepo
import com.example.studyapp.domain.repo.SubjectRepo
import com.example.studyapp.util.toHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val subjectRepo: SubjectRepo,
    private val sessionRepo: SessionRepo
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

}