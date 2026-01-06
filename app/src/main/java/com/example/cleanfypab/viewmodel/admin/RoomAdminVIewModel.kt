package com.example.cleanfypab.viewmodel.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanfypab.data.model.RoomFirestore
import com.example.cleanfypab.data.repository.RoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RoomAdminState(
    val loading: Boolean = false,
    val rooms: List<RoomFirestore> = emptyList(),
    val error: String? = null,
    val successMessage: String? = null
)

class RoomAdminViewModel(
    private val repo: RoomRepository = RoomRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(RoomAdminState())
    val state: StateFlow<RoomAdminState> = _state

    fun loadRooms() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                val rooms = repo.getRooms()
                _state.value = _state.value.copy(loading = false, rooms = rooms)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = e.message ?: "Gagal memuat ruangan"
                )
            }
        }
    }

    fun addRoom(name: String, qrValue: String) {
        if (name.isBlank() || qrValue.isBlank()) {
            _state.value = _state.value.copy(error = "Nama ruangan & QR wajib diisi")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null, successMessage = null)
            try {
                repo.addRoom(name, qrValue)
                _state.value = _state.value.copy(loading = false, successMessage = "Ruangan berhasil ditambahkan")
                loadRooms()
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false, error = e.message ?: "Gagal menambah ruangan")
            }
        }
    }

    fun deleteRoom(roomId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null, successMessage = null)
            try {
                repo.deleteRoom(roomId)
                _state.value = _state.value.copy(loading = false, successMessage = "Ruangan berhasil dihapus")
                loadRooms()
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false, error = e.message ?: "Gagal menghapus ruangan")
            }
        }
    }

    fun clearMessage() {
        _state.value = _state.value.copy(successMessage = null, error = null)
    }
}
