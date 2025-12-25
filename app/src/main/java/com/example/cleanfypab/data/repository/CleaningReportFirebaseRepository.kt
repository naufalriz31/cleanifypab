package com.example.cleanfypab.data.repository

import com.example.cleanfypab.data.model.CleaningReportDoc
import com.example.cleanfypab.data.remote.FirebaseProvider
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

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
     * "Paling sempurna": Update status room + buat report dalam 1 batch.
     * Jadi history & status pasti sinkron.
     */
    suspend fun addReportAndUpdateRoom(
        report: CleaningReportDoc,
        newRoomStatus: String
    ) {
        val batch = FirebaseProvider.db.batch()

        val reportRef = reportsCol.document() // auto id
        batch.set(reportRef, report.copy(id = ""))

        val roomRef = roomsCol.document(report.roomId.toString())
        batch.update(
            roomRef,
            mapOf(
                "status" to newRoomStatus,
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
