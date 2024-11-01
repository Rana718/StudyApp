package com.example.studyapp.ui.session

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.studyapp.ui.components.DeletePopup
import com.example.studyapp.ui.components.SubjectListBottom
import com.example.studyapp.ui.components.studySessionList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@Destination
@Composable
fun SessionScreenRoute(
    navigator: DestinationsNavigator
){

    val viewModel: SessionViewModel = hiltViewModel()

    SessionScreen(
        onBackButtonClick = { navigator.navigateUp() }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SessionScreen(
    onBackButtonClick: () -> Unit
){

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var isBottomSheetOpen by remember { mutableStateOf(false) }

    var isDeletePopupOpen by rememberSaveable { mutableStateOf(false) }

    SubjectListBottom (
        sheetState = sheetState,
        isOpen = isBottomSheetOpen,
        subjects = ,
        onDismiss = { isBottomSheetOpen = false},
        onSubjectClick = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) isBottomSheetOpen = false
            }
        }

    )

    DeletePopup(
        isOpen = isDeletePopupOpen,
        title = "Delete Subject",
        bodyText = "Are you sure you want to delete this subject?",
        onDismiss = { isDeletePopupOpen = false },
        onConfirm = { isDeletePopupOpen = false }
    )

    Scaffold (
        topBar = {
            SessionScreenTopBar(onBackButtonClick = onBackButtonClick)
        }
    ){ paddingValues ->
        LazyColumn (
            modifier = Modifier.fillMaxWidth().padding(paddingValues)
        ){
            item{
                TimerSection(
                    modifier = Modifier.fillMaxWidth().aspectRatio(1f)
                )
            }
            item{
                RelatedToSubjectSection(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                    relatedToSubject = "English",
                    selectSubjectButtonClick = { isBottomSheetOpen = true}
                )
            }
            item{
                ButtonSection(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    startButtonClick = {},
                    cancelButtonClick = {},
                    finishButtonClick = {}
                )
            }
            studySessionList(
                title = "Recent Study Sessions",
                textList = "You Don't have any recent Study Sessions",
                sessions = ,
                onDeleteIconClick = {isDeletePopupOpen=true}
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SessionScreenTopBar(
    onBackButtonClick: () -> Unit,
){
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigate to Back Screen"
                )
            }
        },
        title = {
            Text(text = "Study Sessions", style = MaterialTheme.typography.headlineSmall)
        }
    )
}

@Composable
private fun TimerSection(
    modifier: Modifier
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier.size(250.dp)
                .border(5.dp, MaterialTheme.colorScheme.surfaceVariant, CircleShape)
        )
        Text(
            text = "00:00:00",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 45.sp)
        )
    }
}


@Composable
private fun ButtonSection(
    modifier: Modifier,
    startButtonClick: () -> Unit,
    cancelButtonClick: () -> Unit,
    finishButtonClick: () -> Unit
){
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Button(onClick = cancelButtonClick) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text = "Cancel"
            )
        }
        Button(onClick = startButtonClick) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text = "Start"
            )
        }
        Button(onClick = finishButtonClick) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text = "Finish"
            )
        }
    }
}


@Composable
private fun RelatedToSubjectSection(
    modifier: Modifier,
    relatedToSubject: String,
    selectSubjectButtonClick: () -> Unit
){
    Column (modifier = modifier){
        Text(
            text = "Related to subject",
            style = MaterialTheme.typography.bodySmall
        )
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = relatedToSubject,
                style = MaterialTheme.typography.bodyLarge
            )
            IconButton(onClick =  selectSubjectButtonClick ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select Subject"
                )
            }
        }
    }
}