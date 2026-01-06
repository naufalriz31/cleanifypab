package com.example.cleanfypab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanfypab.data.repository.TaskDoc
import com.example.cleanfypab.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class EditReportState(
    val loading: Boolean = false,
    val saving: Boolean = false,
    val task: TaskDoc? = null,
    val status: String = "ASSIGNED",
    val note: String = "",
    val checkFloor: Boolean = false,
    val checkTable: Boolean = false,
    val checkTrash: Boolean = false,
    val error: String? = null,
    val saved: Boolean = false
)

class EditReportViewModel(
    private val repo: TaskRepository = TaskRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(EditReportState())
    val state: StateFlow<EditReportState> = _state

    fun load(taskId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null, saved = false)
            try {
                val task = repo.getTaskById(taskId)
                _state.value = _state.value.copy(
                    loading = false,
                    task = task,
                    status = task.status,
                    note = task.note,
                    checkFloor = task.checkFloor,
                    checkTable = task.checkTable,
                    checkTrash = task.checkTrash
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = e.message ?: "Gagal memuat task"
                )
            }
        }
    }

    fun setStatus(v: String) { _state.value = _state.value.copy(status = v) }
    fun setNote(v: String) { _state.value = _state.value.copy(note = v) }
    fun setCheckFloor(v: Boolean) { _state.value = _state.value.copy(checkFloor = v) }
    fun setCheckTable(v: Boolean) { _state.value = _state.value.copy(checkTable = v) }
    fun setCheckTrash(v: Boolean) { _state.value = _state.value.copy(checkTrash = v) }

    fun save(taskId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(saving = true, error = null, saved = false)
            try {
                val s = _state.value
                repo.updateTaskReport(
                    taskId = taskId,
                    status = s.status,
                    note = s.note,
                    checkFloor = s.checkFloor,
                    checkTable = s.checkTable,
                    checkTrash = s.checkTrash
                )
                _state.value = _state.value.copy(saving = false, saved = true)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    saving = false,
                    error = e.message ?: "Gagal menyimpan"
                )
            }
        }
    }
}
