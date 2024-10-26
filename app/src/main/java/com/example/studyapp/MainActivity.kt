package com.example.studyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.studyapp.domain.model.Session
import com.example.studyapp.domain.model.Subject
import com.example.studyapp.domain.model.Task
import com.example.studyapp.ui.dashboard.DashboardScreen
import com.example.studyapp.ui.subject.SubjectScreenRoute
import com.example.studyapp.ui.task.TaskScreenRoute
import com.example.studyapp.ui.theme.StudyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyAppTheme {
                TaskScreenRoute()
//               SubjectScreenRoute()
//                DashboardScreen()
            }
        }
    }
}

val subjectList = listOf(
    Subject(name = "Math", goalHours = 10f, colors = Subject.subjectCardColors[0]),
    Subject(name = "English", goalHours = 10f, colors = Subject.subjectCardColors[1]),
    Subject(name = "Science", goalHours = 10f, colors = Subject.subjectCardColors[2]),
    Subject(name = "History", goalHours = 10f, colors = Subject.subjectCardColors[3]),
    Subject(name = "Geography", goalHours = 10f, colors = Subject.subjectCardColors[4]),
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

