package com.pardess.directions.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DirectionAlertDialog(errorMessage: String, errorCode: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "오류 발생")
        },
        text = {
            Column {
                Text(text = errorMessage)
                Text(text = errorCode)
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("확인")
            }
        }
    )
}