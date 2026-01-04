package com.example.cleanfypab.data.repository

import com.example.cleanfypab.data.model.CleaningReportDoc
import com.example.cleanfypab.data.remote.FirebaseProvider
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CleaningReportFirebaseRepository {

    private val reportsCol = FirebaseProvider.db.collection("cleaning_reports")
    private val roomsCol = FirebaseProvider.db.collection("rooms")

    fun streamReports(): Flow<List<CleaningReportDoc>> = callbackFlow {
        var reg: ListenerRegistration? = null

        reg = reportsCol
            .orderBy("createdAt")
            .addSnapshotListener { snap, err ->
                if (err != null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }

                val list = snap?.documents?.map { d ->
                    val data = d.toObject(CleaningReportDoc::class.java) ?: CleaningReportDoc()
                    data.copy(id = d.id)
                }?.sortedByDescending { it.createdAt } ?: emptyList()

                trySend(list)
            }

        awaitClose { reg?.remove() }
    }

    /**
     * ✅ Paling sinkron:
     * - Buat 1 report history baru
     * - Update status + time room
     * Dalam 1 batch commit
     */
    suspend fun addReportAndUpdateRoom(
        report: CleaningReportDoc,
        newRoomStatus: String
    ) {
        val batch = FirebaseProvider.db.batch()

        val nowTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

        // report baru (id auto)
        val reportRef = reportsCol.document()
        batch.set(reportRef, report.copy(id = ""))

        // update room
        val roomRef = roomsCol.document(report.roomId.toString())
        batch.update(
            roomRef,
            mapOf(
                "status" to newRoomStatus,
                "time" to nowTime,
                "updatedAt" to System.currentTimeMillis()
            )
        )

        batch.commit().await()
    }

    suspend fun updateReport(reportId: String, fields: Map<String, Any?>) {
        reportsCol.document(reportId).update(fields).await()
    }

    suspend fun deleteReport(reportId: String) {
        reportsCol.document(reportId).delete().await()
    }
}
