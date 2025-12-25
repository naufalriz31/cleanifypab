package com.example.cleanfypab.data.repository

import com.example.cleanfypab.data.model.RoomDoc
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

    fun streamRooms(): Flow<List<RoomDoc>> = callbackFlow {
        var reg: ListenerRegistration? = null
        reg = col.addSnapshotListener { snap, err ->
            if (err != null) {
                trySend(emptyList())
                return@addSnapshotListener
            }
            val list = snap?.documents
                ?.mapNotNull { it.toObject(RoomDoc::class.java) }
                ?.sortedBy { it.id }
                ?: emptyList()

            trySend(list)
        }
        awaitClose { reg?.remove() }
    }

    suspend fun seedDefaultRoomsIfEmpty() {
        val snap = col.limit(1).get().await()
        if (!snap.isEmpty) return

        val defaults = listOf(
            RoomDoc(1, "Ruang Meeting A101", "Menunggu", "09:00"),
            RoomDoc(2, "Lobi Utama", "Selesai", "08:30"),
            RoomDoc(3, "Toilet Lt. 2", "Menunggu", "09:15")
        )

        defaults.forEach { room ->
            col.document(room.id.toString()).set(room).await()
        }
    }

    suspend fun updateRoomStatus(roomId: Int, newStatus: String) {
        val nowTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        col.document(roomId.toString())
            .update(
                mapOf(
                    "status" to newStatus,
                    "time" to nowTime,
                    "updatedAt" to System.currentTimeMillis()
                )
            )
            .await()
    }

    suspend fun getRoomById(roomId: Int): RoomDoc? {
        val snap = col.document(roomId.toString()).get().await()
        return snap.toObject(RoomDoc::class.java)
    }
}
