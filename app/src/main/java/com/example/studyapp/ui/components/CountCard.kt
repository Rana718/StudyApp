package com.example.studyapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.*


@Composable
fun CountCard(
    modifier: Modifier = Modifier,
    count: Int,
    ItemName: String
){
    ElevatedCard(modifier = modifier) {
        Column (
            modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = ItemName,
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 30.sp)
            )

        }
    }
}