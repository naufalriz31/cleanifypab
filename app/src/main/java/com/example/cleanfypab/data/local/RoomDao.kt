package com.example.cleanfypab.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cleanfypab.data.model.RoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Query("SELECT * FROM rooms ORDER BY id ASC")
    fun getRooms(): Flow<List<RoomEntity>>

    @Query("SELECT * FROM rooms WHERE id = :id LIMIT 1")
    suspend fun getRoomByIdOnce(id: Int): RoomEntity?

    @Query("SELECT COUNT(*) FROM rooms")
    suspend fun countRooms(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRooms(rooms: List<RoomEntity>)

    @Query("UPDATE rooms SET status = :status, time = :time WHERE id = :id")
    suspend fun updateRoomStatus(id: Int, status: String, time: String)
}
