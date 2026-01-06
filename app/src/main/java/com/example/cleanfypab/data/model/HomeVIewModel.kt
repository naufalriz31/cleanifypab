package com.example.cleanfypab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanfypab.data.repository.HomeRepository
import com.example.cleanfypab.data.repository.HomeTaskItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val todayTasks: List<HomeTaskItem> = emptyList(),
    val recentReports: List<HomeTaskItem> = emptyList()
)

class HomeViewModel(
    private val repo: HomeRepository = HomeRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state

    fun load() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                val res = repo.loadHomeData()
                _state.value = HomeUiState(
                    loading = false,
                    todayTasks = res.todayTasks,
                    recentReports = res.recentReports
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = e.message ?: "Gagal memuat data"
                )
            }
        }
    }
}
