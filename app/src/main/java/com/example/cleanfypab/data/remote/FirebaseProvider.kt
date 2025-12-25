package com.example.cleanfypab.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage

object FirebaseProvider {
    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance().apply {
            firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true) // offline cache
                .build()
        }
    }

    val storage: FirebaseStorage by lazy { FirebaseStorage.getInstance() }
}
