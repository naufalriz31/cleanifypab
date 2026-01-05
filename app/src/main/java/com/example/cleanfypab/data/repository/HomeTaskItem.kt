package com.example.cleanfypab.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class HomeTaskItem(
    val id: String,
    val roomName: String,
    val status: String,
    val note: String,
    val timeLabel: String
)

class HomeRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    private fun formatTs(ts: Timestamp?): String {
        if (ts == null) return "-"
        val sdf = SimpleDateFormat("HH:mm", Locale("id", "ID"))
        return sdf.format(ts.toDate())
    }

    private fun dayRangeMillis(): Pair<Long, Long> {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        val start = cal.timeInMillis
        cal.add(Calendar.DAY_OF_MONTH, 1)
        val end = cal.timeInMillis
        return start to end
    }

    suspend fun loadHomeData(): HomeDataResult {
        val uid = auth.currentUser?.uid ?: return HomeDataResult(emptyList(), emptyList())

        val snap = db.collection("tasks")
            .whereEqualTo("petugasId", uid)
            .get()
            .await()

        val (startDay, endDay) = dayRangeMillis()

        val all = snap.documents.map { doc ->
            val createdAt = doc.getTimestamp("createdAt")
            val updatedAt = doc.getTimestamp("updatedAt")
            val tsForLabel = updatedAt ?: createdAt

            HomeTaskItem(
                id = doc.id,
                roomName = doc.getString("roomName") ?: (doc.getString("roomId") ?: "Ruangan"),
                status = doc.getString("status") ?: "ASSIGNED",
                note = doc.getString("note") ?: "",
                timeLabel = formatTs(tsForLabel)
            ) to (tsForLabel?.toDate()?.time ?: 0L) to (createdAt?.toDate()?.time ?: 0L)
        }
        // Struktur pair agak panjang -> kita rapikan ke list biasa
        val mapped = all.map { triple ->
            val item = triple.first.first
            val sortTime = triple.first.second
            val createdTime = triple.second
            Triple(item, sortTime, createdTime)
        }

        // Tugas hari ini: createdAt hari ini & belum DONE
        val todayTasks = mapped
            .filter { it.third in startDay until endDay }
            .filter { it.first.status != "DONE" }
            .sortedByDescending { it.second }
            .map { it.first }
            .take(2) // di Home tampil 2 aja

        // Laporan terbaru: ambil 3 terbaru dari updatedAt/createdAt (semua status)
        val recent = mapped
            .sortedByDescending { it.second }
            .map { it.first }
            .take(3)

        return HomeDataResult(todayTasks, recent)
    }
}

data class HomeDataResult(
    val todayTasks: List<HomeTaskItem>,
    val recentReports: List<HomeTaskItem>
)
