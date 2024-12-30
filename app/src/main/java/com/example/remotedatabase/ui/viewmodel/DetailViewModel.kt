package com.example.remotedatabase.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remotedatabase.model.Mahasiswa
import com.example.remotedatabase.repository.MahasiswaRepository
import kotlinx.coroutines.launch

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val mahasiswa: Mahasiswa) : DetailUiState()
    object Error : DetailUiState()
}

class DetailViewModel(private val mhs: MahasiswaRepository) : ViewModel() {
    var detailUiState by mutableStateOf<DetailUiState>(DetailUiState.Loading)
        private set

    fun getMahasiswaDetail(nim: String) {
        viewModelScope.launch {
            detailUiState = DetailUiState.Loading
            try {
                val mahasiswa = mhs.getMahasiswaByID(nim)
                detailUiState = DetailUiState.Success(mahasiswa)
            } catch (e: Exception) {
                detailUiState = DetailUiState.Error
            }
        }
    }
}