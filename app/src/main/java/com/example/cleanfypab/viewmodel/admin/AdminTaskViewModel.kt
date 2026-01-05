package com.example.cleanfypab.viewmodel.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanfypab.data.model.PetugasFirestore
import com.example.cleanfypab.data.model.RoomFirestore
import com.example.cleanfypab.data.repository.AdminTaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AdminCreateTaskState(
    val loading: Boolean = false,
    val petugas: List<PetugasFirestore> = emptyList(),
    val rooms: List<RoomFirestore> = emptyList(),
    val error: String? = null,
    val success: Boolean = false
)

class AdminTaskViewModel(
    private val repo: AdminTaskRepository = AdminTaskRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(AdminCreateTaskState())
    val state: StateFlow<AdminCreateTaskState> = _state

    fun loadData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null, success = false)
            try {
                val petugas = repo.getPetugas()
                val rooms = repo.getRooms()
                _state.value = _state.value.copy(loading = false, petugas = petugas, rooms = rooms)
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false, error = e.message ?: "Gagal memuat data")
            }
        }
    }

    fun createTask(
        selectedPetugas: PetugasFirestore?,
        selectedRoom: RoomFirestore?,
        note: String
    ) {
        if (selectedPetugas == null) {
            _state.value = _state.value.copy(error = "Pilih petugas dulu")
            return
        }
        if (selectedRoom == null) {
            _state.value = _state.value.copy(error = "Pilih ruangan dulu")
            return
        }
        if (note.isBlank()) {
            _state.value = _state.value.copy(error = "Catatan tugas wajib diisi")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null, success = false)
            try {
                repo.createTask(
                    petugasId = selectedPetugas.id,
                    petugasName = selectedPetugas.name,
                    roomId = selectedRoom.id,
                    roomName = selectedRoom.name,
                    note = note
                )
                _state.value = _state.value.copy(loading = false, success = true)
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false, error = e.message ?: "Gagal membuat task")
            }
        }
    }

    fun clearFlags() {
        _state.value = _state.value.copy(error = null, success = false)
    }
}
