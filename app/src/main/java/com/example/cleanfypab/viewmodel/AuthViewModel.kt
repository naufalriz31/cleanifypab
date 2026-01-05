package com.example.cleanfypab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanfypab.data.repository.FirebaseAuthRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class AuthUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val role: String? = null
)

class AuthViewModel : ViewModel() {

    private val authRepo = FirebaseAuthRepository()
    private val firestore = FirebaseFirestore.getInstance()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(loading = true)

            try {
                val user = authRepo.signIn(email, password)
                val uid = user.uid

                val snap = firestore.collection("users").document(uid).get().await()
                val role = snap.getString("role")

                if (role.isNullOrBlank()) {
                    _uiState.value = AuthUiState(
                        loading = false,
                        error = "Role belum diset di Firestore untuk akun ini."
                    )
                    return@launch
                }

                _uiState.value = AuthUiState(loading = false, role = role)
            } catch (e: Exception) {
                _uiState.value = AuthUiState(
                    loading = false,
                    error = authRepo.readableError(e)
                )
            }
        }
    }

    fun logout() {
        authRepo.signOut()
        _uiState.value = AuthUiState()
    }
}
