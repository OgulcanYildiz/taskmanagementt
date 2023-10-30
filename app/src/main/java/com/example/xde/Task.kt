package com.example.xde.ui.home

enum class TaskStatus {
    TO_DO, IN_PROGRESS, DONE
}

data class Task(
    val id: String,
    val title: String,
    val description: String,
    var status: TaskStatus,
    var isTimerRunning: Boolean = false
)