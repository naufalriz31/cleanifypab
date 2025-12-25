package com.example.cleanfypab.data.model

data class UserProfileDoc(
    val uid: String = "",
    val email: String = "",
    val role: String = "petugas", // admin / petugas
    val name: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
