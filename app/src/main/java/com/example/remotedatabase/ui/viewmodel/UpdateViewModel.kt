package com.example.remotedatabase.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remotedatabase.model.Mahasiswa
import com.example.remotedatabase.repository.MahasiswaRepository
import kotlinx.coroutines.launch

class UpdateViewModel(
    private val repository: MahasiswaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var uiState by mutableStateOf(UpdateUiState())
        private set

    // Menyimpan dan memuat data ke SaveStateHandle
    init {
        val nim = savedStateHandle.get<String>("nim")
        nim?.let {
            loadMahasiswa(it)
        }
    }

    // Memuat data mahasiswa berdasarkan nim
    fun loadMahasiswa(nim: String) {
        viewModelScope.launch {
            try {
                val mahasiswa = repository.getMahasiswaByID(nim)
                uiState = uiState.copy(
                    nim = mahasiswa.nim,
                    nama = mahasiswa.nama,
                    alamat = mahasiswa.alamat,
                    jenisKelamin = mahasiswa.jenisKelamin,
                    kelas = mahasiswa.kelas,
                    angkatan = mahasiswa.angkatan
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Memperbarui state untuk setiap field
    fun updateField(fieldName: String, value: String) {
        uiState = when (fieldName) {
            "nama" -> uiState.copy(nama = value)
            "alamat" -> uiState.copy(alamat = value)
            "jenisKelamin" -> uiState.copy(jenisKelamin = value)
            "kelas" -> uiState.copy(kelas = value)
            "angkatan" -> uiState.copy(angkatan = value)
            else -> uiState
        }
    }

    // Menyimpan data mahasiswa yang telah diperbarui
    fun updateMahasiswa(onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                val mahasiswa = Mahasiswa(
                    nim = uiState.nim,
                    nama = uiState.nama,
                    alamat = uiState.alamat,
                    jenisKelamin = uiState.jenisKelamin,
                    kelas = uiState.kelas,
                    angkatan = uiState.angkatan
                )
                repository.updateMahasiswa(mahasiswa.nim, mahasiswa)
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                onError()
            }
        }
    }
}


data class UpdateUiState(
    val nim: String = "",
    val nama: String = "",
    val alamat: String = "",
    val jenisKelamin: String = "",
    val kelas: String = "",
    val angkatan: String = ""
)
