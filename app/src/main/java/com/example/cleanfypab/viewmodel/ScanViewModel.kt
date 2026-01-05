package com.example.cleanfypab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanfypab.data.repository.RoomFirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScanViewModel(
    private val roomRepo: RoomFirebaseRepository = RoomFirebaseRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(ScanUiState())
    val state: StateFlow<ScanUiState> = _state

    // anti spam scan (cooldown)
    private var lastHandledAt: Long = 0L
    private val cooldownMs = 1200L

    fun onQrScanned(qr: String) {
        val cleaned = qr.trim()
        if (cleaned.isBlank()) return

        // kalau sama persis dengan scan terakhir dan belum selesai navigate -> skip
        if (_state.value.lastQr == cleaned && _state.value.loading) return

        val now = System.currentTimeMillis()
        if (now - lastHandledAt < cooldownMs) return
        lastHandledAt = now

        _state.update {
            it.copy(
                loading = true,
                lastQr = cleaned,
                error = null,
                navigateRoomId = null
            )
        }

        viewModelScope.launch {
            val result = roomRepo.findRoomByQrValue(cleaned)

            result.onSuccess { room ->
                if (room == null) {
                    _state.update {
                        it.copy(
                            loading = false,
                            error = "Ruangan dengan QR tersebut tidak ditemukan."
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            loading = false,
                            navigateRoomId = room.id
                        )
                    }
                }
            }.onFailure { e ->
                _state.update {
                    it.copy(
                        loading = false,
                        error = e.message ?: "Gagal membaca data ruangan."
                    )
                }
            }
        }
    }

    fun consumeNavigation() {
        _state.update { it.copy(navigateRoomId = null) }
    }

    fun resetError() {
        _state.update { it.copy(error = null) }
    }
}
