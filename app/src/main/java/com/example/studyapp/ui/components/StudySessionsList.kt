package com.example.studyapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.studyapp.R
import com.example.studyapp.domain.model.Session


fun LazyListScope.studySessionList(
    title: String,
    textList: String,
    sessions: List<Session>,
    onDeleteIconClick: () -> Unit
){
    item{
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(12.dp),
        )
    }
    if(sessions.isEmpty()){
        item{
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Image(
                    modifier = Modifier.size(120.dp),
                    painter = painterResource(R.drawable.img_lamp),
                    contentDescription = textList
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    text = textList,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    items(sessions){session ->
        StudySessionCard (
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            session = session,
            onDeleteIconClick = { onDeleteIconClick()}
        )
    }
}


@Composable
private fun StudySessionCard(
    modifier: Modifier = Modifier,
    session: Session,
    onDeleteIconClick: () -> Unit
){
    Card (
        modifier = modifier
    ) {
        Row (
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier.padding(start = 12.dp)
            ) {
                Text(
                    text = session.relatedToSubject,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${session.date}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${session.duration} hr",
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(onClick = onDeleteIconClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Session"
                )
            }
        }
    }
}