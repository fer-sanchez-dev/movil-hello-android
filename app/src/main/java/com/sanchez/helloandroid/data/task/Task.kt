package com.sanchez.helloandroid.data.task

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val reminderTime: String = "",
    val hasReminder: Boolean,
    val isDone: Boolean = false
)
