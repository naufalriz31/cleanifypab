package com.example.cleanfypab.data.model

data class RoomDoc(
    val id: Int = 0,
    val name: String = "",
    val status: String = "Menunggu",
    val time: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)
