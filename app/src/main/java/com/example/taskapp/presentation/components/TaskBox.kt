package com.example.taskapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskapp.presentation.ui.theme.TaskAppTheme

@Composable
fun TaskBox(
    value: Int,
    title: String,
    color: Color
) {
    Box(
        modifier = Modifier
            .size(95.dp)
            .background(color, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value.toString(),
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(text = title, color = Color.White, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
fun TaskBoxPreview() {
    TaskAppTheme {
        TaskBox(value = 2, title = "Completadas", color = Color.Red)
    }
}