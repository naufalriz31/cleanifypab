package com.example.cleanfypab.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class TaskDoc(
    val id: String = "",
    val petugasId: String = "",
    val roomId: String = "",
    val roomName: String = "",
    val status: String = "ASSIGNED",
    val note: String = "",
    val checkFloor: Boolean = false,
    val checkTable: Boolean = false,
    val checkTrash: Boolean = false
)

class TaskRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    suspend fun getTaskById(taskId: String): TaskDoc {
        val doc = db.collection("tasks").document(taskId).get().await()
        return TaskDoc(
            id = doc.id,
            petugasId = doc.getString("petugasId") ?: "",
            roomId = doc.getString("roomId") ?: "",
            roomName = doc.getString("roomName") ?: "",
            status = doc.getString("status") ?: "ASSIGNED",
            note = doc.getString("note") ?: "",
            checkFloor = doc.getBoolean("checkFloor") ?: false,
            checkTable = doc.getBoolean("checkTable") ?: false,
            checkTrash = doc.getBoolean("checkTrash") ?: false
        )
    }

    suspend fun updateTaskReport(
        taskId: String,
        status: String,
        note: String,
        checkFloor: Boolean,
        checkTable: Boolean,
        checkTrash: Boolean
    ) {
        val uid = auth.currentUser?.uid ?: error("User belum login")

        // optional: validasi sederhana biar petugas cuma update miliknya
        val existing = db.collection("tasks").document(taskId).get().await()
        val owner = existing.getString("petugasId") ?: ""
        if (owner.isNotBlank() && owner != uid) error("Bukan task milikmu")

        db.collection("tasks").document(taskId)
            .update(
                mapOf(
                    "status" to status,
                    "note" to note,
                    "checkFloor" to checkFloor,
                    "checkTable" to checkTable,
                    "checkTrash" to checkTrash,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            )
            .await()
    }
}
