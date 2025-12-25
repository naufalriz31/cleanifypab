package com.example.cleanfypab.data.repository

import com.example.cleanfypab.data.model.UserProfileDoc
import com.example.cleanfypab.data.remote.FirebaseProvider
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository {

    sealed class LoginResult {
        data class Success(val uid: String, val role: String) : LoginResult()
        data class Error(val message: String) : LoginResult()
    }

    /**
     * user bisa mengetik:
     * - admin / admin@gmail.com
     * - user / user@gmail.com
     */
    private fun normalizeEmail(input: String): String {
        val raw = input.trim().lowercase()
        if (raw.contains("@")) return raw

        return when (raw) {
            "admin" -> "admin@gmail.com"
            "petugas", "user" -> "user@gmail.com"
            else -> raw // biarkan, nanti Firebase akan gagal login jika email invalid
        }
    }

    private fun roleFromEmail(email: String): String {
        return when (email.trim().lowercase()) {
            "admin@gmail.com" -> "admin"
            "user@gmail.com" -> "petugas"
            else -> "petugas"
        }
    }

    suspend fun login(identifierOrEmail: String, password: String): LoginResult {
        val email = normalizeEmail(identifierOrEmail)
        if (email.isBlank()) return LoginResult.Error("Email/username tidak boleh kosong")
        if (password.isBlank()) return LoginResult.Error("Password tidak boleh kosong")

        return try {
            val res = FirebaseProvider.auth
                .signInWithEmailAndPassword(email, password)
                .await()

            val user = res.user ?: return LoginResult.Error("Login gagal (user null)")
            val uid = user.uid
            val role = roleFromEmail(email)

            // Simpan/Update profil role di Firestore (biar rapi & bisa dipakai ke depan)
            upsertUserProfile(uid = uid, email = email, role = role)

            LoginResult.Success(uid = uid, role = role)
        } catch (e: Exception) {
            LoginResult.Error(e.message ?: "Login gagal")
        }
    }

    private suspend fun upsertUserProfile(uid: String, email: String, role: String) {
        val docRef = FirebaseProvider.db.collection("users").document(uid)
        val snap = docRef.get().await()

        if (!snap.exists()) {
            docRef.set(
                UserProfileDoc(
                    uid = uid,
                    email = email,
                    role = role
                )
            ).await()
        } else {
            // minimal update role/email saja
            docRef.update(mapOf("email" to email, "role" to role)).await()
        }
    }

    fun logout() {
        FirebaseProvider.auth.signOut()
    }
}
