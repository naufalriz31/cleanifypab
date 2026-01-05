package com.example.cleanfypab.data.repository

import com.example.cleanfypab.data.model.RoomFirestore
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.example.cleanfypab.data.model.RoomDoc


class RoomRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val roomsRef = db.collection("rooms")

    suspend fun addRoom(name: String, qrValue: String) {
        val data = hashMapOf(
            "name" to name.trim(),
            "qrValue" to qrValue.trim(),
            "createdAt" to FieldValue.serverTimestamp()
        )
        roomsRef.add(data).await()
    }

    suspend fun getRooms(): List<RoomFirestore> {
        val snap = roomsRef
            .orderBy("createdAt")
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

    suspend fun deleteRoom(roomId: String) {
        roomsRef.document(roomId).delete().await()
    }
    suspend fun getRoomByQrValue(qrValue: String): RoomDoc? {
        val snap = db.collection("rooms")
            .whereEqualTo("qrValue", qrValue)
            .limit(1)
            .get()
            .await()

        val doc = snap.documents.firstOrNull() ?: return null

        return RoomDoc(
            name = doc.getString("name") ?: "",
            status = doc.getString("status") ?: ""
        )
    }
}
