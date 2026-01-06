package com.example.cleanfypab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.Date

data class AdminDashboardState(
    val loading: Boolean = false,
    val totalRooms: Int = 0,
    val activePetugas: Int = 0,
    val tasksToday: Int = 0,
    val error: String? = null
)

class AdminDashboardViewModel(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ViewModel() {

    private val _state = MutableStateFlow(AdminDashboardState())
    val state: StateFlow<AdminDashboardState> = _state

    fun loadSummary() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                val roomsCount = db.collection("rooms").get().await().size()

                val petugasCount = db.collection("users")
                    .whereEqualTo("role", "petugas")
                    .whereEqualTo("active", true)
                    .get()
                    .await()
                    .size()

                val startToday = startOfToday()
                val tasksTodayCount = db.collection("tasks")
                    .whereGreaterThanOrEqualTo("createdAt", startToday)
                    .get()
                    .await()
                    .size()

                _state.value = AdminDashboardState(
                    loading = false,
                    totalRooms = roomsCount,
                    activePetugas = petugasCount,
                    tasksToday = tasksTodayCount
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = e.message ?: "Gagal memuat dashboard"
                )
            }
        }
    }

    private fun startOfToday(): Date {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.time
    }
}
