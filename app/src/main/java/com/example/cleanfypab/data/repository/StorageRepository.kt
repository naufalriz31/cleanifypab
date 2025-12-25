package com.example.cleanfypab.data.repository

import android.net.Uri
import com.example.cleanfypab.data.remote.FirebaseProvider
import kotlinx.coroutines.tasks.await

class StorageRepository {

    private val root = FirebaseProvider.storage.reference.child("reports")

    suspend fun uploadReportPhoto(uri: Uri): String {
        val ref = root.child("${System.currentTimeMillis()}.jpg")
        ref.putFile(uri).await()
        return ref.downloadUrl.await().toString()
    }
}
