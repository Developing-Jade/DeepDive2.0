package org.example.kmmtaskpickerdeepdive

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kmmtaskpickerdeepdive.composeapp.generated.resources.Res
import kmmtaskpickerdeepdive.composeapp.generated.resources.compose_multiplatform
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
@Preview
fun App() {
    MaterialTheme {
        var task by remember { mutableStateOf("") }
        val tasks = remember { mutableStateListOf<String>() }
        var selectedTask by remember { mutableStateOf<String?>(null) }
        val completedTasks = remember { mutableStateListOf<String>() }

        Scaffold(
            bottomBar = {
                // Bottom button stays pinned
                Button(
                    onClick = {
                        if (selectedTask == null) {
                            val availableTasks = tasks.filterNot { completedTasks.contains(it) }
                            if ( availableTasks.isNotEmpty()) {
                                selectedTask = availableTasks.random()
                            }
                        } else {
                            // Mark complete
                            selectedTask?.let { completedTasks.add(it) }
                            selectedTask = null
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    enabled = tasks.any {it !in completedTasks}

                ) {
                    Text(if (selectedTask == null) "Pick Random Task" else "Mark Complete")
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Input field
                TextField(
                    value = task,
                    onValueChange = { task = it },
                    label = { Text("Enter a task") },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Add task button
                Button(
                    onClick = {
                        if (task.isNotBlank()) {
                            tasks.add(task)
                            task = "" //Clears input after adding
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add new task")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Scrollable task list
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(tasks) { item ->
                        val isSelected = item == selectedTask
                        val isCompleted = completedTasks.contains(item)

                        Text(
                            text = item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    when {
                                        isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                                        else -> Color.Transparent
                                    }
                                )
                                .padding(12.dp),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = when {
                                    isSelected -> MaterialTheme.colorScheme.primary
                                    isCompleted -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                    else -> MaterialTheme.colorScheme.onSurface
                                },
                                textDecoration = if (isCompleted) TextDecoration.LineThrough else TextDecoration.None
                            )
                        )
                        Divider()
                    }
                }
            }
        }
    }
}
