package com.example.cleanfypab.data.repository

import com.example.cleanfypab.data.model.RoomModel
import com.example.cleanfypab.data.remote.FirebaseProvider
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RoomFirebaseRepository {

    private val col = FirebaseProvider.db.collection("rooms")

    /** ================= STREAM ROOMS ================= */
    fun streamRooms(): Flow<List<RoomModel>> = callbackFlow {
        var reg: ListenerRegistration? = null

        reg = col.addSnapshotListener { snap, err ->
            if (err != null || snap == null) {
                trySend(emptyList())
                return@addSnapshotListener
            }

            val rooms = snap.documents.mapNotNull { doc ->
                val name = doc.getString("name") ?: return@mapNotNull null

                RoomModel(
                    id = doc.id, // ✅ AMAN
                    name = name,
                    type = doc.getString("type") ?: "",
                    qrValue = doc.getString("qrValue") ?: "",
                    status = doc.getString("status") ?: "AVAILABLE",
                    time = doc.getString("time") ?: ""
                )
            }

            trySend(rooms)
        }

        awaitClose { reg?.remove() }
    }

    /** ================= ADD ROOM ================= */
    suspend fun addRoom(room: RoomModel): Result<String> = runCatching {
        val doc = col.document() // auto id

        doc.set(
            mapOf(
                "name" to room.name,
                "type" to room.type,
                "qrValue" to room.qrValue,
                "status" to room.status,
                "time" to room.time,
                "createdAt" to System.currentTimeMillis()
            )
        ).await()

        doc.id
    }

    /** ================= DELETE ROOM ================= */
    suspend fun deleteRoom(roomId: String): Result<Unit> = runCatching {
        col.document(roomId).delete().await()
    }

    /** ================= UPDATE STATUS ================= */
    suspend fun updateRoomStatus(roomId: String, status: String): Result<Unit> = runCatching {
        val timeNow = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

        col.document(roomId)
            .update(
                mapOf(
                    "status" to status,
                    "time" to timeNow,
                    "updatedAt" to System.currentTimeMillis()
                )
            )
            .await()
    }
    suspend fun findRoomByQrValue(qrValue: String): Result<RoomModel?> = runCatching {
        val snap = col
            .whereEqualTo("qrValue", qrValue)
            .limit(1)
            .get()
            .await()

        val doc = snap.documents.firstOrNull() ?: return@runCatching null
        // pakai doc.id sebagai id utama
        doc.toObject(RoomModel::class.java)?.copy(id = doc.id)
    }

    /** ================= CEK QR DUPLIKAT ================= */
    suspend fun existsQrValue(qrValue: String): Result<Boolean> = runCatching {
        val snap = col.whereEqualTo("qrValue", qrValue).limit(1).get().await()
        !snap.isEmpty
    }
}
