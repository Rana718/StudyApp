package com.example.studyapp.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.studyapp.R
import com.example.studyapp.domain.model.Subject
import com.example.studyapp.ui.components.CountCard
import com.example.studyapp.ui.components.SubCard
import com.example.studyapp.ui.components.taskList


@Composable
fun DashboardScreen(){

    val subjectList = listOf(
        Subject(name = "Math", goalHours = 10f, colors = Subject.subjectCardColors[0]),
        Subject(name = "English", goalHours = 10f, colors = Subject.subjectCardColors[1]),
        Subject(name = "Science", goalHours = 10f, colors = Subject.subjectCardColors[2]),
        Subject(name = "History", goalHours = 10f, colors = Subject.subjectCardColors[3]),
        Subject(name = "Geography", goalHours = 10f, colors = Subject.subjectCardColors[4]),
    )

    Scaffold (
        topBar = { DashboardScreenTopBar() }
    ){ paddingValues ->
        LazyColumn (
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ){
            item{
                TopCardSection(
                    modifier = Modifier.fillMaxSize().padding(12.dp),
                    Count = 5,
                    studiedHours = 10,
                    goalHours = 20
                )
            }
            item {
                SubjectCard(
                    modifier = Modifier.fillMaxSize(),
                    subjectList = subjectList
                )
            }
            item{
                Button(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 48.dp, vertical = 20.dp),
                    onClick = {},
                ) {
                    Text(text = "Start Study Session")
                }
            }
            taskList(
                title = "Upcoming Tasks",
                textList = "You don't have any upcoming tasks",
                tasks = emptyList()
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
    studiedHours: Int,
    goalHours: Int
){
    Row (
        modifier = modifier
    ){
        CountCard(
            modifier = Modifier.weight(1f),
            ItemName = "Subjects",
            count = Count
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
    subjectList: List<Subject>
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
            IconButton(onClick = {}) {
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
            items(subjectList){ it ->
                SubCard(
                    subjectName = it.name,
                    gradientColors = it.colors,
                    onClick = {}
                )
            }
        }
    }
}