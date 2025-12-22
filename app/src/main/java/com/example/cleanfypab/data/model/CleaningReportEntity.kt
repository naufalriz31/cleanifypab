package com.example.cleanfypab.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cleaning_reports")
data class CleaningReportEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val roomId: Int,
    val roomName: String,
    val status: String,
    val checklistFloor: Boolean,
    val checklistTable: Boolean,
    val checklistTrash: Boolean,
    val note: String? = null,
    val photoPath: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val cleanerName: String? = null
)
