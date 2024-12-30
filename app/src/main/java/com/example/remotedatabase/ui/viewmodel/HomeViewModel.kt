package com.example.remotedatabase.ui.viewmodel

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remotedatabase.model.Mahasiswa
import com.example.remotedatabase.repository.MahasiswaRepository
import kotlinx.coroutines.launch
import java.io.IOException


sealed class HomeUiState {
    data class Success(val mahasiswa: List<Mahasiswa>) : HomeUiState()
    object  Error : HomeUiState()
    object Loading : HomeUiState()
}


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class HomeViewModel (private val mhs: MahasiswaRepository): ViewModel(){
    var mhsUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set
    init {
        getMhs()
    }
    fun getMhs() {
        viewModelScope.launch {
            mhsUIState = HomeUiState.Loading
            try {
                val data = mhs.getMahasiswa()
                mhsUIState = HomeUiState.Success(data)
            } catch (e: Exception) {
                println("Error loading mahasiswa: ${e.message}")
                mhsUIState = HomeUiState.Error
            }
        }
    }


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun deleteMhs(nim: String){
        viewModelScope.launch {
            try {
                mhs.deleteMahasiswa(nim)
            }
            catch (e: IOException){
                HomeUiState.Error
            }
            catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
}

