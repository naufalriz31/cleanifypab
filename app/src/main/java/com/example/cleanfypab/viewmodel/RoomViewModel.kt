package com.example.cleanfypab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanfypab.data.model.RoomModel
import com.example.cleanfypab.data.repository.RoomFirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomViewModel(
    private val repo: RoomFirebaseRepository = RoomFirebaseRepository()
) : ViewModel() {

    private val _roomList = MutableStateFlow<List<RoomModel>>(emptyList())
    val roomList: StateFlow<List<RoomModel>> = _roomList

    init {
        viewModelScope.launch {
            repo.streamRooms().collect { rooms ->
                _roomList.value = rooms
            }
        }
    }

    fun getRoomById(id: String): RoomModel? {
        return _roomList.value.firstOrNull { it.id == id }
    }
    fun markRoomClean(id: String) {
        updateStatus(id, "Selesai")
    }


    fun deleteRoom(id: String) {
        viewModelScope.launch { repo.deleteRoom(id) }
    }
    fun updateRoomStatus(id: String, status: String) {
        updateStatus(id, status)
    }

    fun updateStatus(roomId: String, status: String) {
        viewModelScope.launch { repo.updateRoomStatus(roomId, status) }
    }
}
