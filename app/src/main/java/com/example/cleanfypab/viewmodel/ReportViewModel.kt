package com.example.cleanfypab.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanfypab.data.local.AppDatabase
import com.example.cleanfypab.data.model.CleaningReportEntity
import com.example.cleanfypab.data.repository.CleaningReportRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReportViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = CleaningReportRepository(
        AppDatabase.getInstance(application).cleaningReportDao()
    )

    val reports: StateFlow<List<CleaningReportEntity>> =
        repo.allReports()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun addReport(report: CleaningReportEntity) {
        viewModelScope.launch { repo.addReport(report) }
    }

    fun updateReport(report: CleaningReportEntity) {
        viewModelScope.launch { repo.updateReport(report) }
    }

    fun deleteReport(report: CleaningReportEntity) {
        viewModelScope.launch { repo.deleteReport(report) }
    }
}
