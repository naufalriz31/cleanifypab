package com.example.cleanfypab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanfypab.data.model.RoomDoc
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
            repo.seedDefaultRoomsIfEmpty()
            repo.streamRooms().collect { rooms ->
                _roomList.value = rooms.map { it.toRoomModel() }
            }
        }
    }

    fun getRoomById(id: Int): RoomModel? {
        return _roomList.value.firstOrNull { it.id == id }
    }

    /**
     * ✅ Fungsi utama (baru) yang dipakai backend Firebase
     */
    fun updateStatus(roomId: Int, status: String) {
        viewModelScope.launch {
            repo.updateRoomStatus(roomId, status)
        }
    }

    /**
     * ✅ Alias untuk UI lama (biar tidak error)
     */
    fun markRoomClean(id: Int) {
        updateStatus(id, "Selesai")
    }

    /**
     * ✅ Alias untuk UI lama (biar tidak error)
     * Kamu bisa panggil status apa saja: "Menunggu" / "Selesai"
     */
    fun updateRoomStatus(id: Int, status: String) {
        updateStatus(id, status)
    }

    private fun RoomDoc.toRoomModel(): RoomModel {
        return RoomModel(
            id = id,
            name = name,
            status = status,
            time = time
        )
    }
}
