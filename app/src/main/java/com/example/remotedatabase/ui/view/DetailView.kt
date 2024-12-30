package com.example.remotedatabase.ui.view

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.remotedatabase.ui.navigasi.CostumeTopAppBar
import com.example.remotedatabase.ui.navigasi.DestinasiNavigasi
import com.example.remotedatabase.ui.viewmodel.DetailUiState
import com.example.remotedatabase.ui.viewmodel.DetailViewModel
import com.example.remotedatabase.ui.viewmodel.PenyediaViewModel

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Mhs"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    nim: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.detailUiState

    LaunchedEffect(nim) {
        viewModel.getMahasiswaDetail(nim)
    }

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = "Detail Mahasiswa",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        when (uiState) {
            is DetailUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Loading...")
                }
            }
            is DetailUiState.Success -> {
                val mahasiswa = uiState.mahasiswa
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Nama: ${mahasiswa.nama}", style = MaterialTheme.typography.titleMedium)
                    Text("NIM: ${mahasiswa.nim}", style = MaterialTheme.typography.bodyMedium)
                    Text("Jenis Kelamin: ${mahasiswa.jenisKelamin}", style = MaterialTheme.typography.bodyMedium)
                    Text("Alamat: ${mahasiswa.alamat}", style = MaterialTheme.typography.bodyMedium)
                    Text("Kelas: ${mahasiswa.kelas}", style = MaterialTheme.typography.bodyMedium)
                    Text("Angkatan: ${mahasiswa.angkatan}", style = MaterialTheme.typography.bodyMedium)
                }
            }
            is DetailUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Failed to load data.")
                }
            }
        }
    }
}
