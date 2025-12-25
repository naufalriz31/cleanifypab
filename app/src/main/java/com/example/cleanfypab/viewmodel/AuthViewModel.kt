package com.example.cleanfypab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanfypab.data.repository.FirebaseAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val role: String? = null,  // "admin"/"petugas"
    val loggedIn: Boolean = false
)

class AuthViewModel(
    private val repo: FirebaseAuthRepository = FirebaseAuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun login(identifierOrEmail: String, password: String) {
        _uiState.update { it.copy(loading = true, error = null, role = null, loggedIn = false) }

        viewModelScope.launch {
            when (val res = repo.login(identifierOrEmail, password)) {
                is FirebaseAuthRepository.LoginResult.Success -> {
                    _uiState.update {
                        it.copy(
                            loading = false,
                            loggedIn = true,
                            role = res.role
                        )
                    }
                }

                is FirebaseAuthRepository.LoginResult.Error -> {
                    _uiState.update { it.copy(loading = false, error = res.message) }
                }
            }
        }
    }

    fun consumeLogin() {
        _uiState.update { it.copy(loggedIn = false) }
    }
}
