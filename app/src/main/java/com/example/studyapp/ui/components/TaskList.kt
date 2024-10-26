package com.example.studyapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.example.studyapp.domain.model.Task
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.studyapp.R
import androidx.compose.foundation.lazy.items
import com.example.studyapp.util.Common


fun LazyListScope.taskList(
    title: String,
    textList: String,
    tasks: List<Task>,
    onTaskCardClick: (Int?) -> Unit,
    onCheckBoxClick: (Task) -> Unit
){
    item{
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(12.dp),
        )
    }
    if(tasks.isEmpty()){
        item{
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Image(
                    modifier = Modifier.size(120.dp),
                    painter = painterResource(R.drawable.img_tasks),
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
    items(tasks){task ->
        TaskCard(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            task = task,
            onCheckBoxClick = { onCheckBoxClick(task)},
            onClick = {onTaskCardClick(task.taskId)}
        )
    }
}


@Composable
private fun TaskCard(
    modifier: Modifier = Modifier,
    task: Task,
    onCheckBoxClick: () -> Unit,
    onClick: () -> Unit
){
    ElevatedCard(
        modifier = modifier.clickable { onClick() }
    ) {
        Row (
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            TaskCheckBox(
                isComplete = task.isComplete,
                borderColor = Common.fromInt(task.priority).color,
                onClick = onCheckBoxClick
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    text = task.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (task.isComplete){
                        TextDecoration.LineThrough
                    }else TextDecoration.None
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${task.dueDate}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}