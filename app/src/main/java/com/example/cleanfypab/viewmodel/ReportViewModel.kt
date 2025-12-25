package com.example.cleanfypab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanfypab.data.model.CleaningReportDoc
import com.example.cleanfypab.data.repository.CleaningReportFirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReportViewModel(
    private val repo: CleaningReportFirebaseRepository = CleaningReportFirebaseRepository()
) : ViewModel() {

    private val _reports = MutableStateFlow<List<CleaningReportDoc>>(emptyList())
    val reports: StateFlow<List<CleaningReportDoc>> = _reports

    init {
        viewModelScope.launch {
            repo.streamReports().collect { _reports.value = it }
        }
    }

    fun addReportAndUpdateRoom(report: CleaningReportDoc, newStatus: String) {
        viewModelScope.launch {
            repo.addReportAndUpdateRoom(report, newStatus)
        }
    }

    fun deleteReport(reportId: String) {
        viewModelScope.launch { repo.deleteReport(reportId) }
    }
}
