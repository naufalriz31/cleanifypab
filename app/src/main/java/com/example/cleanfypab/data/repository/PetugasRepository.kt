package com.example.cleanfypab.data.repository

import com.example.cleanfypab.data.model.PetugasFirestore
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PetugasRepository(
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
                role = doc.getString("role") ?: "petugas",
                active = doc.getBoolean("active") ?: true
            )
        }.sortedBy { it.name.lowercase() }
    }
}
