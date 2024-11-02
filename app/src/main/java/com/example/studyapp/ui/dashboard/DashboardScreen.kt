package com.example.studyapp.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.studyapp.R
import com.example.studyapp.domain.model.Session
import com.example.studyapp.domain.model.Subject
import com.example.studyapp.domain.model.Task
import com.example.studyapp.ui.components.AddSubjectPopup
import com.example.studyapp.ui.components.CountCard
import com.example.studyapp.ui.components.DeletePopup
import com.example.studyapp.ui.components.SubCard
import com.example.studyapp.ui.components.studySessionList
import com.example.studyapp.ui.components.taskList
import com.example.studyapp.ui.destinations.SessionScreenRouteDestination
import com.example.studyapp.ui.destinations.SubjectScreenRouteDestination
import com.example.studyapp.ui.destinations.TaskScreenRouteDestination
import com.example.studyapp.ui.subject.SubjectScreenNavArgs
import com.example.studyapp.ui.task.TaskScreenNavArgs
import com.example.studyapp.util.SnackbarEvent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@RootNavGraph(start = true)
@Destination
@Composable
fun DashboardScreenRoute(
    navigator: DestinationsNavigator
){

    val viewModel: DashboardViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val tasks  by viewModel.tasks.collectAsStateWithLifecycle()
    val recentSession by viewModel.recentSessions.collectAsStateWithLifecycle()

    DashboardScreen(
        state = state,
        tasks = tasks,
        recentSession = recentSession,
        snackbarEvent = viewModel.snackbarEventFlow,
        onEvent = viewModel::onEvent,
        onSubjectCardClick = { subjectId ->
            subjectId?.let {
                val navArg = SubjectScreenNavArgs(subjectId = subjectId)
                navigator.navigate(SubjectScreenRouteDestination(navArgs = navArg))
            }
        },
        onTaskCardClick = {
            val navArg = TaskScreenNavArgs(taskId = it, subjectId = null)
            navigator.navigate(TaskScreenRouteDestination(navArgs = navArg))
        },
        onSessionCardClick = {
            navigator.navigate(SessionScreenRouteDestination())
        }
    )
}


@Composable
fun DashboardScreen(
    state: DashboardState,
    tasks: List<Task>,
    recentSession: List<Session>,
    snackbarEvent: SharedFlow<SnackbarEvent>,
    onEvent: (DashboardEvent) -> Unit,
    onSubjectCardClick: (Int?) -> Unit,
    onTaskCardClick: (Int?) -> Unit,
    onSessionCardClick: () -> Unit
){
    var isAddSubjectPopupOpen by rememberSaveable { mutableStateOf(false) }
    var isDeletePopupOpen by rememberSaveable { mutableStateOf(false) }
    var snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true){
        snackbarEvent.collectLatest { event ->
            when(event){
                is SnackbarEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }
                SnackbarEvent.NavigateUp -> {}
            }
        }
    }

    AddSubjectPopup(
        isOpen = isAddSubjectPopupOpen,
        title = "Add Subject",
        subjectName = state.subjectName,
        goalHours = state.goalStudyHours,
        onSubjectNameChange = { onEvent(DashboardEvent.OnSubjectNameChange(it)) },
        onGoalHoursChange = { onEvent(DashboardEvent.OnGoalStudyHoursChange(it)) },
        selectedColor = state.subjectCardColors,
        onColorChange = { onEvent(DashboardEvent.OnSubjectCardColorChange(it))},
        onDismiss = { isAddSubjectPopupOpen = false},
        onSave = {
            onEvent(DashboardEvent.SaveSubject)
            isAddSubjectPopupOpen = false
        }
    )

    DeletePopup(
        isOpen = isDeletePopupOpen,
        title = "Delete Subject",
        bodyText = "Are you sure you want to delete this subject?",
        onDismiss = { isDeletePopupOpen = false },
        onConfirm = {
            onEvent(DashboardEvent.DeleteSession)
            isDeletePopupOpen = false
        }
    )

    Scaffold (
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = { DashboardScreenTopBar() }
    ){ paddingValues ->
        LazyColumn (
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ){
            item{
                TopCardSection(
                    modifier = Modifier.fillMaxSize().padding(12.dp),
                    Count = state.totalSubjectCount,
                    studiedHours = state.totalStudiedHours.toString(),
                    goalHours = state.totalGoalStudyHours.toString()
                )
            }
            item {
                SubjectCard(
                    modifier = Modifier.fillMaxSize(),
                    subjectList = state.subjects,
                    onAddIconClick = { isAddSubjectPopupOpen = true },
                    onSubjectCardClick = onSubjectCardClick
                )
            }
            item{
                Button(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 48.dp, vertical = 20.dp),
                    onClick = onSessionCardClick,
                ) {
                    Text(text = "Start Study Session")
                }
            }
            taskList(
                title = "Upcoming Tasks",
                textList = "You don't have any upcoming tasks",
                tasks = tasks,
                onCheckBoxClick = { onEvent(DashboardEvent.OnTaskIsCompleteChange(it))},
                onTaskCardClick = onTaskCardClick
            )
            item{
                Spacer(modifier = Modifier.height(12.dp))
            }
            studySessionList(
                title = "Recent Study Sessions",
                textList = "You Don't have any recent Study Sessions",
                sessions = recentSession,
                onDeleteIconClick = {
                    onEvent(DashboardEvent.OnDeleteSessionButtonClick(it))
                    isDeletePopupOpen=true
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenTopBar(){
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Study Smart",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    )
}

@Composable
private fun TopCardSection(
    modifier: Modifier,
    Count: Int,
    studiedHours: String,
    goalHours: String
){
    Row (
        modifier = modifier
    ){
        CountCard(
            modifier = Modifier.weight(1f),
            ItemName = "Subjects",
            count = Count.toString()
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            ItemName = "Studied Hours",
            count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            ItemName = "Goal Study Hours",
            count = goalHours
        )

    }
}

@Composable
private fun SubjectCard(
    modifier: Modifier,
    subjectList: List<Subject>,
    onAddIconClick: () -> Unit,
    onSubjectCardClick: (Int?) -> Unit,
){
    Column(modifier = modifier) {
        Row (
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Subject Name",
                modifier = Modifier.padding(start = 12.dp),
                style = MaterialTheme.typography.bodySmall,
            )
            IconButton(onClick = onAddIconClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Delete"
                )
            }
        }
        if(subjectList.isEmpty()){
            Image(
                modifier = Modifier.size(120.dp).align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.img_books),
                contentDescription = "Image of books"
            )
            Text(
                modifier = Modifier.fillMaxSize(),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                text = "You don't have any subject",
                textAlign = TextAlign.Center
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ){
            items(subjectList){ subject ->
                SubCard(
                    subjectName = subject.name,
                    gradientColors = subject.colors.map { Color(it) },
                    onClick = {onSubjectCardClick(subject.subjectId)}
                )
            }
        }
    }
}