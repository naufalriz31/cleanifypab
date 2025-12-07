package com.example.cleanfypab.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cleanfypab.data.model.RoomModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RoomViewModel : ViewModel() {

    private val _roomList = MutableStateFlow(
        listOf(
            RoomModel(1, "Ruang Meeting A101", "Menunggu", "09:00"),
            RoomModel(2, "Lobi Utama", "Selesai", "08:30"),
            RoomModel(3, "Toilet Lt. 2", "Menunggu", "09:15")
        )
    )
    val roomList: StateFlow<List<RoomModel>> = _roomList

    // ✔ fungsi get room by id
    fun getRoomById(id: Int): RoomModel? {
        return _roomList.value.firstOrNull { it.id == id }
    }

    // ✔ fungsi update status
    fun markRoomClean(id: Int) {
        _roomList.update { list ->
            list.map { room ->
                if (room.id == id)
                    room.copy(status = "Selesai")
                else room
            }
        }
    }
}
