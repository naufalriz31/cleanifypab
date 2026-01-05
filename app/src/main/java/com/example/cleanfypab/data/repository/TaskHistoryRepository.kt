package com.example.cleanfypab.data.repository

import com.example.cleanfypab.data.model.TaskHistoryItem
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale

class TaskHistoryRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    private fun formatTs(ts: Timestamp?): String {
        if (ts == null) return "-"
        val sdf = SimpleDateFormat("dd MMM yyyy • HH:mm", Locale("id", "ID"))
        return sdf.format(ts.toDate())
    }

    suspend fun getMyTasksHistory(): List<TaskHistoryItem> {
        val uid = auth.currentUser?.uid ?: return emptyList()

        // ✅ TANPA orderBy() -> TIDAK butuh index
        val snap = db.collection("tasks")
            .whereEqualTo("petugasId", uid)
            .get()
            .await()

        // ambil createdAt aman
        val itemsWithTime = snap.documents.map { doc ->
            val createdAt = doc.getTimestamp("createdAt") // ✅ Timestamp (kalau null tetap aman)

            val item = TaskHistoryItem(
                id = doc.id,
                roomId = doc.getString("roomId") ?: "",
                roomName = doc.getString("roomName") ?: "",
                note = doc.getString("note") ?: "",
                status = doc.getString("status") ?: "ASSIGNED",
                timeLabel = formatTs(createdAt)
            )

            // simpan timestamp untuk sorting lokal
            Pair(item, createdAt?.toDate()?.time ?: 0L)
        }

        // ✅ sorting lokal terbaru dulu
        return itemsWithTime
            .sortedByDescending { it.second }
            .map { it.first }
    }
}
