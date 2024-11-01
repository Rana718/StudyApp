package com.example.studyapp.ui.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDatePicker(
    state: DatePickerState,
    isOpen: Boolean,
    confirmButtonText: String = "OK",
    dismissButtonText: String = "Cancel",
    onDismissRequest: () -> Unit,
    onConfirmButtonClicked: (Long?) -> Unit
) {

    val todayMillis = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val tomorrowMillis = LocalDate.now().plusDays(1)
        .atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val state = rememberDatePickerState(
        initialSelectedDateMillis = tomorrowMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(dateMillis: Long): Boolean {
                return dateMillis >= todayMillis
            }
        }
    )


    if (isOpen) {
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = {
                    onConfirmButtonClicked(state.selectedDateMillis)
                    onDismissRequest()
                }) {
                    Text(text = confirmButtonText)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = dismissButtonText)
                }
            },
            content = {
                DatePicker(
                    state = state
                )
            }
        )
    }
}
