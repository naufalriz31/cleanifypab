package com.example.cleanfypab.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanfypab.data.local.AppDatabase
import com.example.cleanfypab.data.model.RoomModel
import com.example.cleanfypab.data.repository.RoomRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RoomViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = RoomRepository(
        AppDatabase.getInstance(application).roomDao()
    )

    val roomList: StateFlow<List<RoomModel>> =
        repo.roomsFlow()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        viewModelScope.launch {
            repo.seedDefaultRoomsIfEmpty()
        }
    }

    fun getRoomById(id: Int): RoomModel? =
        roomList.value.firstOrNull { it.id == id }

    fun markRoomClean(id: Int) {
        viewModelScope.launch {
            repo.updateStatus(id, "Selesai")
        }
    }

    fun updateRoomStatus(id: Int, status: String) {
        viewModelScope.launch {
            repo.updateStatus(id, status)
        }
    }
}
