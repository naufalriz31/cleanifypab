package com.example.cleanfypab.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rooms")
data class RoomEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: String,
    val time: String
)
