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

    /**
     * Stream real-time daftar rooms.
     * - Kalau permission ditolak / offline → emit emptyList, tidak crash.
     */
    fun streamRooms(): Flow<List<RoomDoc>> = callbackFlow {
        var reg: ListenerRegistration? = null

        reg = col.addSnapshotListener { snap, err ->
            if (err != null) {
                // jangan crash
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

    /**
     * Seed data default jika koleksi kosong.
     * - Aman: return Result, jadi pemanggil tidak crash kalau permission ditolak/offline.
     */
    suspend fun seedDefaultRoomsIfEmpty(): Result<Unit> = runCatching {
        val snap = col.limit(1).get().await()
        if (!snap.isEmpty) return@runCatching

        val defaults = listOf(
            RoomDoc(1, "Ruang Meeting A101", "Menunggu", "09:00"),
            RoomDoc(2, "Lobi Utama", "Selesai", "08:30"),
            RoomDoc(3, "Toilet Lt. 2", "Menunggu", "09:15")
        )

        defaults.forEach { room ->
            col.document(room.id.toString()).set(room).await()
        }
    }

    /**
     * Update status room + update time.
     * - Aman: return Result.
     */
    suspend fun updateRoomStatus(roomId: Int, newStatus: String): Result<Unit> = runCatching {
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

    /**
     * Get room by id.
     * - Aman: return Result<RoomDoc?>
     */
    suspend fun getRoomById(roomId: Int): Result<RoomDoc?> = runCatching {
        val snap = col.document(roomId.toString()).get().await()
        snap.toObject(RoomDoc::class.java)
    }
}
