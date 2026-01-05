package com.example.cleanfypab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanfypab.data.model.TaskHistoryItem
import com.example.cleanfypab.data.repository.TaskHistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class TaskHistoryState(
    val loading: Boolean = false,
    val items: List<TaskHistoryItem> = emptyList(),
    val error: String? = null
)

class TaskHistoryViewModel(
    private val repo: TaskHistoryRepository = TaskHistoryRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(TaskHistoryState())
    val state: StateFlow<TaskHistoryState> = _state

    fun load() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                val data = repo.getMyTasksHistory()
                _state.value = _state.value.copy(loading = false, items = data)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = e.message ?: "Gagal memuat riwayat"
                )
            }
        }
    }
}
