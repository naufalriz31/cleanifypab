package com.example.cleanfypab.data.model

data class TaskFirestore(
    val petugasId: String = "",
    val petugasName: String = "",
    val roomId: String = "",
    val roomName: String = "",
    val note: String = "",
    val status: String = "ASSIGNED"
)
