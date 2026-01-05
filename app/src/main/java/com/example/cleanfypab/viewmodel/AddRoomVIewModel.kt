package com.example.cleanfypab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanfypab.data.model.RoomModel
import com.example.cleanfypab.data.repository.RoomFirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

data class AddRoomUiState(
    val name: String = "",
    val type: String = "",
    val qrValue: String = generateQrValue("ROOM"),
    val status: String = "AVAILABLE",
    val time: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
) {
    val canSave: Boolean
        get() = name.isNotBlank() && qrValue.isNotBlank() && !loading
}

class AddRoomViewModel(
    private val repo: RoomFirebaseRepository = RoomFirebaseRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddRoomUiState())
    val uiState: StateFlow<AddRoomUiState> = _uiState

    fun setName(v: String) = _uiState.update { it.copy(name = v) }
    fun setType(v: String) = _uiState.update { it.copy(type = v) }
    fun setStatus(v: String) = _uiState.update { it.copy(status = v) }
    fun setQrValue(v: String) = _uiState.update { it.copy(qrValue = v) }
    fun generateQr() = _uiState.update { it.copy(qrValue = generateQrValue("ROOM")) }

    fun saveRoom() {
        val s = _uiState.value
        if (!s.canSave) return

        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null, success = false) }

            val existsResult = repo.existsQrValue(s.qrValue)

            if (existsResult.isFailure) {
                val err = existsResult.exceptionOrNull()
                _uiState.update {
                    it.copy(
                        loading = false,
                        error = err?.localizedMessage ?: err?.toString() ?: "Gagal cek QR"
                    )
                }
                return@launch
            }

            val exists = existsResult.getOrNull() == true
            if (exists) {
                _uiState.update {
                    it.copy(
                        loading = false,
                        error = "QR Value sudah digunakan"
                    )
                }
                return@launch
            }

            val room = RoomModel(
                name = s.name.trim(),
                type = s.type.trim(),
                qrValue = s.qrValue.trim(),
                status = s.status,
                time = s.time
            )

            val addResult = repo.addRoom(room)
            if (addResult.isSuccess) {
                _uiState.update { it.copy(loading = false, success = true) }
            } else {
                val err = addResult.exceptionOrNull()
                _uiState.update {
                    it.copy(
                        loading = false,
                        error = err?.localizedMessage ?: err?.toString() ?: "Gagal menyimpan ruangan"
                    )
                }
            }
        }
    }
}

private fun generateQrValue(prefix: String): String {
    val hex = Random.nextInt(0x100000, 0xFFFFFF).toString(16).uppercase()
    return "$prefix-$hex"
}
