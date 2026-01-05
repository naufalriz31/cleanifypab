package com.example.cleanfypab.data.repository

import com.example.cleanfypab.data.model.PetugasFirestore
import com.example.cleanfypab.data.model.RoomFirestore
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AdminTaskRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun getPetugas(): List<PetugasFirestore> {
        val snap = db.collection("users")
            .whereEqualTo("role", "petugas")
            .get()
            .await()

        return snap.documents.map { doc ->
            PetugasFirestore(
                id = doc.id,
                name = doc.getString("name") ?: "",
                active = doc.getBoolean("active") ?: true
            )
        }.sortedBy { it.name }
    }

    suspend fun getRooms(): List<RoomFirestore> {
        val snap = db.collection("rooms")
            .orderBy("name")
            .get()
            .await()

        return snap.documents.map { doc ->
            RoomFirestore(
                id = doc.id,
                name = doc.getString("name") ?: "",
                qrValue = doc.getString("qrValue") ?: ""
            )
        }
    }

    suspend fun createTask(
        petugasId: String,
        petugasName: String,
        roomId: String,
        roomName: String,
        note: String
    ) {
        val data = hashMapOf(
            "petugasId" to petugasId,
            "petugasName" to petugasName,
            "roomId" to roomId,
            "roomName" to roomName,
            "note" to note.trim(),
            "status" to "ASSIGNED",
            "createdAt" to FieldValue.serverTimestamp()
        )

        db.collection("tasks").add(data).await()
    }
}
