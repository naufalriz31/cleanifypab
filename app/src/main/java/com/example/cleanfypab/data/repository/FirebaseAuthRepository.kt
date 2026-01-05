package com.example.cleanfypab.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    /** User yang sedang login (null jika belum login) */
    fun currentUser(): FirebaseUser? = auth.currentUser

    /** UID user yang sedang login */
    fun currentUid(): String? = auth.currentUser?.uid

    /** Login dengan Email/Password */
    suspend fun signIn(email: String, password: String): FirebaseUser {
        val result = auth.signInWithEmailAndPassword(email.trim(), password).await()
        return result.user ?: throw IllegalStateException("Login berhasil, tapi user null.")
    }

    /** Logout */
    fun signOut() {
        auth.signOut()
    }

    /**
     * Register (opsional).
     * Kamu bilang petugas tidak register sendiri, tapi ini berguna untuk testing/seed awal.
     */
    suspend fun register(email: String, password: String): FirebaseUser {
        val result = auth.createUserWithEmailAndPassword(email.trim(), password).await()
        return result.user ?: throw IllegalStateException("Register berhasil, tapi user null.")
    }

    /** Helper: ubah exception Firebase jadi pesan yang lebih manusia */
    fun readableError(e: Throwable): String {
        return when (e) {
            is FirebaseAuthInvalidUserException -> "Akun tidak ditemukan."
            is FirebaseAuthInvalidCredentialsException -> "Email atau password salah."
            is FirebaseAuthUserCollisionException -> "Email sudah terdaftar."
            else -> e.message ?: "Terjadi kesalahan. Coba lagi."
        }
    }
}
