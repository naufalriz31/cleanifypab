package com.example.cleanfypab.data.repository

import com.example.cleanfypab.data.local.RoomDao
import com.example.cleanfypab.data.model.RoomEntity
import com.example.cleanfypab.data.model.RoomModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RoomRepository(private val dao: RoomDao) {

    fun roomsFlow(): Flow<List<RoomModel>> =
        dao.getRooms().map { list -> list.map { it.toModel() } }

    suspend fun seedDefaultRoomsIfEmpty() {
        if (dao.countRooms() == 0) {
            dao.upsertRooms(
                listOf(
                    RoomEntity(1, "Ruang Meeting A101", "Menunggu", "09:00"),
                    RoomEntity(2, "Lobi Utama", "Selesai", "08:30"),
                    RoomEntity(3, "Toilet Lt. 2", "Menunggu", "09:15")
                )
            )
        }
    }

    suspend fun updateStatus(roomId: Int, status: String) {
        dao.updateRoomStatus(
            id = roomId,
            status = status,
            time = nowHHmm()
        )
    }

    suspend fun getRoomByIdOnce(id: Int): RoomModel? =
        dao.getRoomByIdOnce(id)?.toModel()

    private fun nowHHmm(): String =
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

    private fun RoomEntity.toModel() = RoomModel(
        id = id,
        name = name,
        status = status,
        time = time
    )
}
