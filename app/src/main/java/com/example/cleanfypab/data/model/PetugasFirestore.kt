package com.example.cleanfypab.data.model

data class PetugasFirestore(
    val id: String = "",    // doc.id
    val name: String = "",
    val role: String = "petugas",
    val active: Boolean = true
)
