package com.example.studyapp.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable


@Composable
fun DeletePopup(
    isOpen: Boolean,
    title: String,
    bodyText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){
    if(isOpen){
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = title) },
            text = { Text(text = bodyText) },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(text = "Delete")
                }
            }
        )
    }
}