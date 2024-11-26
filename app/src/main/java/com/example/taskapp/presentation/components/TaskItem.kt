package com.example.taskapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskapp.domain.models.Task
import com.example.taskapp.presentation.ui.theme.TaskAppTheme

@Composable
fun TaskItem(task: Task){
    var isChecked by remember {
        mutableStateOf(task.isDone)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Vence en 2 horas",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
        Checkbox(checked = isChecked, onCheckedChange = {
            isChecked = !isChecked
        } )
    }
}

@Preview
@Composable
fun TaskItemPreview(){
    val task = Task(
        id = 1,
        title = "Tarea de prueba",
        description = "Prueba",
        isDone = false,
        creationDate = "2024-11-20",
        dueDate = "2024-11-22",
        userId = 1
    )
    TaskAppTheme {
        TaskItem(task = task)
    }
}