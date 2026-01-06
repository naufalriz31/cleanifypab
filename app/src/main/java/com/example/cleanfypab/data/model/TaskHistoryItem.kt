package com.example.cleanfypab.data.model

data class TaskHistoryItem(
    val id: String = "",            // task doc id
    val roomId: String = "",
    val roomName: String = "",
    val note: String = "",
    val status: String = "ASSIGNED", // ASSIGNED / IN_PROGRESS / DONE
    val timeLabel: String = ""       // sudah diformat untuk UI
)
