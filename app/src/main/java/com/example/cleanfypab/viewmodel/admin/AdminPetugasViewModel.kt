package com.example.cleanfypab.viewmodel.admin


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanfypab.data.model.PetugasFirestore
import com.example.cleanfypab.data.repository.PetugasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AdminPetugasState(
    val loading: Boolean = false,
    val petugas: List<PetugasFirestore> = emptyList(),
    val error: String? = null
)

class AdminPetugasViewModel(
    private val repo: PetugasRepository = PetugasRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(AdminPetugasState())
    val state: StateFlow<AdminPetugasState> = _state

    fun loadPetugas() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                val list = repo.getPetugas()
                _state.value = _state.value.copy(loading = false, petugas = list)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = e.message ?: "Gagal memuat data petugas"
                )
            }
        }
    }
}
