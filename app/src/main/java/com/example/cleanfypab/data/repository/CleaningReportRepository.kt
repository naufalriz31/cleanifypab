package com.example.cleanfypab.data.repository

import com.example.cleanfypab.data.local.CleaningReportDao
import com.example.cleanfypab.data.model.CleaningReportEntity
import kotlinx.coroutines.flow.Flow

class CleaningReportRepository(
    private val dao: CleaningReportDao
) {

    fun allReports(): Flow<List<CleaningReportEntity>> =
        dao.getAllReports()

    fun reportsForRoom(roomId: Int): Flow<List<CleaningReportEntity>> =
        dao.getReportsForRoom(roomId)

    suspend fun addReport(report: CleaningReportEntity): Long =
        dao.insertReport(report)

    suspend fun updateReport(report: CleaningReportEntity) =
        dao.updateReport(report)

    suspend fun deleteReport(report: CleaningReportEntity) =
        dao.deleteReport(report)
}
