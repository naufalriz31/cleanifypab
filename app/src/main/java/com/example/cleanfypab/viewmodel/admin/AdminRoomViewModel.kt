package com.example.cleanfypab.viewmodel.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanfypab.data.model.RoomModel
import com.example.cleanfypab.data.repository.RoomFirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AdminRoomUiState(
    val rooms: List<RoomModel> = emptyList(),
    val loading: Boolean = true,
    val error: String? = null
)

class AdminRoomViewModel(
    private val repo: RoomFirebaseRepository = RoomFirebaseRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminRoomUiState())
    val uiState: StateFlow<AdminRoomUiState> = _uiState

    init {
        viewModelScope.launch {
            repo.streamRooms().collect { list ->
                _uiState.update {
                    it.copy(
                        rooms = list.sortedBy { r -> r.name },
                        loading = false
                    )
                }
            }
        }
    }

    fun deleteRoom(id: String) {
        viewModelScope.launch {
            repo.deleteRoom(id)
        }
    }
}
