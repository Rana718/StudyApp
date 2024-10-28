package com.example.studyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import com.example.studyapp.domain.model.Session
import com.example.studyapp.domain.model.Subject
import com.example.studyapp.domain.model.Task
import com.example.studyapp.ui.NavGraphs
import com.example.studyapp.ui.theme.StudyAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyAppTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}

val subjectList = listOf(
    Subject(subjectId = 1, name = "Math", goalHours = 10f, colors = Subject.subjectCardColors[0].map{it.toArgb()}),
    Subject(subjectId = 2, name = "English", goalHours = 10f, colors = Subject.subjectCardColors[1].map{it.toArgb()}),
    Subject(subjectId = 3, name = "Science", goalHours = 10f, colors = Subject.subjectCardColors[2].map{it.toArgb()}),
    Subject(subjectId = 4, name = "History", goalHours = 10f, colors = Subject.subjectCardColors[3].map{it.toArgb()}),
    Subject(subjectId = 5, name = "Geography", goalHours = 10f, colors = Subject.subjectCardColors[4].map{it.toArgb()})
)


val taskList = listOf(
    Task(title = "Programming", description = "Coding", dueDate = 0L, priority = 1, relatedToSubject = "Math", isComplete = false, taskSubjectId = 1),
    Task(title = "Reading", description = "Read Chapter 5", dueDate = 1L, priority = 2, relatedToSubject = "English", isComplete = false, taskSubjectId = 2),
    Task(title = "Experiment", description = "Complete Science Experiment", dueDate = 2L, priority = 3, relatedToSubject = "Science", isComplete = false, taskSubjectId = 3),
    Task(title = "Essay Writing", description = "Write History Essay", dueDate = 3L, priority = 0, relatedToSubject = "History", isComplete = true, taskSubjectId = 4),
    Task(title = "Map Practice", description = "Study World Map", dueDate = 4L, priority = 2, relatedToSubject = "Geography", isComplete = false, taskSubjectId = 5)
)

val sessionList = listOf(
    Session(relatedToSubject = "English", date = 0L, duration = 1L, sessionSubjectId = 1, sessionId = 0),
    Session(relatedToSubject = "Math", date = 1L, duration = 2L, sessionSubjectId = 2, sessionId = 1),
    Session(relatedToSubject = "Science", date = 2L, duration = 3L, sessionSubjectId = 3, sessionId = 2),
    Session(relatedToSubject = "History", date = 3L, duration = 4L, sessionSubjectId = 4, sessionId = 3),
    Session(relatedToSubject = "Geography", date = 4L, duration = 5L, sessionSubjectId = 5, sessionId = 4)
)

