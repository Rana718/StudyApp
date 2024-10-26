package com.example.studyapp.ui.subject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.studyapp.ui.components.CountCard
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.studyapp.domain.model.Subject
import com.example.studyapp.sessionList
import com.example.studyapp.ui.components.AddSubjectPopup
import com.example.studyapp.ui.components.DeletePopup
import com.example.studyapp.ui.components.studySessionList
import com.example.studyapp.ui.components.taskList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreenRoute(){

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val listState = rememberLazyListState()
    val isFABExpanded by remember{
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }

    var isAddSubjectPopupOpen by rememberSaveable { mutableStateOf(false) }
    var isDeletePopupOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSessionOpen by rememberSaveable { mutableStateOf(false) }
    var subjectName by rememberSaveable { mutableStateOf("") }
    var goalHours by rememberSaveable { mutableStateOf("") }
    var selectedColor by rememberSaveable { mutableStateOf(Subject.subjectCardColors.random()) }

    AddSubjectPopup(
        isOpen = isAddSubjectPopupOpen,
        title = "Update",
        subjectName = subjectName,
        goalHours = goalHours,
        onSubjectNameChange = { subjectName = it},
        onGoalHoursChange = { goalHours = it},
        selectedColor = selectedColor,
        onColorChange = { selectedColor = it},
        onDismiss = { isAddSubjectPopupOpen = false},
        onSave = {
            isAddSubjectPopupOpen = false
        }
    )

    DeletePopup(
        isOpen = isDeletePopupOpen,
        title = "Delete Subject",
        bodyText = "Are you sure you want to delete this subject?",
        onDismiss = { isDeletePopupOpen = false },
        onConfirm = { isDeletePopupOpen = false }
    )

    DeletePopup(
        isOpen = isDeleteSessionOpen,
        title = "Delete Session",
        bodyText = "Are you sure you want to delete this Session?",
        onDismiss = { isDeletePopupOpen = false },
        onConfirm = { isDeletePopupOpen = false }
    )

    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SubjectScreenTopBa(
                title = "English",
                onBackButtonClick = {},
                onDeleteButtonClick = { isDeletePopupOpen = true },
                onEditButtonClick = { isAddSubjectPopupOpen = true},
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {},
                icon = {Icon(imageVector = Icons.Default.Add, contentDescription = "Add")},
                text = {Text(text = "Add Task")},
                expanded = isFABExpanded
            )
        }
    ){ paddingValues ->
        LazyColumn (
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            state = listState
        ){
            item{
                SubjectOverviewSection(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    studiedHours = "100",
                    goalHours = "200",
                    progress = 0.5f
                )
            }
            taskList(
                title = "Upcoming Tasks",
                textList = "You don't have any upcoming tasks",
                tasks = com.example.studyapp.taskList,
                onCheckBoxClick = {},
                onTaskCardClick = {}
            )
            item{
                Spacer(modifier = Modifier.height(12.dp))
            }
            taskList(
                title = "Completed Tasks",
                textList = "You don't have any Completed tasks",
                tasks = com.example.studyapp.taskList,
                onCheckBoxClick = {},
                onTaskCardClick = {}
            )
            item{
                Spacer(modifier = Modifier.height(12.dp))
            }
            studySessionList(
                title = "Recent Study Sessions",
                textList = "You Don't have any recent Study Sessions",
                sessions = sessionList,
                onDeleteIconClick = {isDeleteSessionOpen=true}
            )
        }
        
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectScreenTopBa(
    title: String,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
){
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            IconButton(onClick = onDeleteButtonClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
            IconButton(onClick = onEditButtonClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }
        }
    )
}

@Composable
private fun SubjectOverviewSection(
    modifier: Modifier,
    studiedHours: String,
    goalHours: String,
    progress: Float
){
    val percentageProgress = remember(progress) {
        (progress * 100).toInt().coerceIn(0, 100)
    }
   Row (
       modifier = modifier,
       horizontalArrangement = Arrangement.SpaceBetween,
       verticalAlignment = Alignment.CenterVertically
   ){
       CountCard(
           modifier = Modifier.weight(1f),
           ItemName = "Goal Study Hours",
           count = goalHours.toInt()
       )
       Spacer(modifier = Modifier.width(10.dp))
       CountCard(
           modifier = Modifier.weight(1f),
           ItemName = "Studied Hours",
           count = studiedHours.toInt()
       )
       Spacer(modifier = Modifier.width(10.dp))
       Box(
           modifier = Modifier.size(75.dp),
           contentAlignment = Alignment.Center
       ){
           CircularProgressIndicator(
               modifier = Modifier.fillMaxWidth(),
               progress = 1f,
               strokeWidth = 4.dp,
               strokeCap = StrokeCap.Round,
               color = MaterialTheme.colorScheme.surfaceVariant
           )
           CircularProgressIndicator(
               modifier = Modifier.fillMaxWidth(),
               progress = progress,
               strokeWidth = 4.dp,
               strokeCap = StrokeCap.Round,
           )
           Text(text = "$percentageProgress%")
       }
   }
}