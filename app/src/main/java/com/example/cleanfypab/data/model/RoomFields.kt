package com.example.cleanfypab.data.model

data class RoomFields(
    val name: String = "",
    val type: String = "",
    val qrValue: String = "",
    val status: String = "AVAILABLE",
    val time: String = "" // opsional: kalau kamu mau simpan jam update
)
