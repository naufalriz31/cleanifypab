package com.example.cleanfypab.data.model

data class CleaningReportDoc(
    val id: String = "",

    val roomId: Int = 0,
    val roomName: String = "",
    val status: String = "Menunggu",

    val checklistFloor: Boolean = false,
    val checklistTable: Boolean = false,
    val checklistTrash: Boolean = false,

    val note: String = "",
    val photoUrl: String? = null,

    val createdAt: Long = System.currentTimeMillis(),
    val cleanerName: String? = null,
    val updatedByUid: String? = null
)
