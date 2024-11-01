package com.example.studyapp.ui.session

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyapp.domain.model.Session
import com.example.studyapp.domain.repo.SessionRepo
import com.example.studyapp.domain.repo.SubjectRepo
import com.example.studyapp.util.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject


@HiltViewModel
class SessionViewModel @Inject constructor(
    subjectRepo: SubjectRepo,
    private val sessionRepo: SessionRepo
): ViewModel(){
    private val _state = MutableStateFlow(SessionState())
    private val _snackbarEventFlow = MutableSharedFlow<SnackbarEvent>()
    val snackbarEventFlow = _snackbarEventFlow.asSharedFlow()


    val state = combine(
        _state,
        subjectRepo.getAllSubjects(),
        sessionRepo.getAllSessions()
    ){ state, subjects, sessions ->
        state.copy(
            subjects = subjects,
            sessions = sessions
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SessionState()
    )

    fun onEvent(event: SessionEvent){
        when(event) {
            SessionEvent.DeleteSession -> deleteSession()
            SessionEvent.NotifyToUpdateSubject -> notifyToUpdateSubject()
            is SessionEvent.OnDeleteSessionButtonClick -> {
                _state.update {
                    it.copy(session = event.session)
                }
            }
            is SessionEvent.OnRelatedSubjectChange -> {
                _state.update {
                    it.copy(relatedToSubject = event.subject.name, subjectId = event.subject.subjectId)
                }
            }
            is SessionEvent.SaveSession -> insertSession(event.duration)
            is SessionEvent.UpdateSubjectIdAndRelatedSubject -> {
                _state.update {
                    it.copy(
                        relatedToSubject = event.relatedToSubject,
                        subjectId = event.subjectId
                    )
                }
            }
        }
    }

    private fun notifyToUpdateSubject(){
        viewModelScope.launch {
            if (state.value.subjectId == null || state.value.relatedToSubject == null) {
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        message = "Please select subject"
                    )
                )
            }
        }
    }

    private fun deleteSession(){
        viewModelScope.launch {
            try {
                state.value.session?.let {
                    sessionRepo.deleteSession(it)
                    _snackbarEventFlow.emit(
                        SnackbarEvent.ShowSnackbar(message = "deleted successfully")
                    )
                }
            } catch (e: Exception) {
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        message = "Couldn't delete session. ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun insertSession(duration: Long){
        viewModelScope.launch {
            if(duration < 36){
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        message = "session can not be less than 36 seconds"
                    )
                )
                return@launch
            }
            try{
                sessionRepo.insertSession(
                    session = Session(
                        sessionSubjectId = state.value.subjectId ?: -1,
                        relatedToSubject = state.value.relatedToSubject ?: "",
                        date = Instant.now().toEpochMilli(),
                        duration = duration
                    )
                )
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(message = "Session saved successfully")
                )
            }catch (e: Exception){
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        message = "Couldn't save session. ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }




}
