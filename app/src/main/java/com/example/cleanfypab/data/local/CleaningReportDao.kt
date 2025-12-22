package com.example.cleanfypab.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cleanfypab.data.model.CleaningReportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CleaningReportDao {

    @Query("SELECT * FROM cleaning_reports ORDER BY createdAt DESC")
    fun getAllReports(): Flow<List<CleaningReportEntity>>

    @Query("SELECT * FROM cleaning_reports WHERE roomId = :roomId ORDER BY createdAt DESC")
    fun getReportsForRoom(roomId: Int): Flow<List<CleaningReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: CleaningReportEntity): Long

    @Update
    suspend fun updateReport(report: CleaningReportEntity)

    @Delete
    suspend fun deleteReport(report: CleaningReportEntity)
}
