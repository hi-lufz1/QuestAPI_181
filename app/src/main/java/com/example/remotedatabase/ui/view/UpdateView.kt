package com.example.remotedatabase.ui.view

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.remotedatabase.R
import com.example.remotedatabase.ui.navigasi.CostumeTopAppBar
import com.example.remotedatabase.ui.navigasi.DestinasiNavigasi
import com.example.remotedatabase.ui.viewmodel.PenyediaViewModel
import com.example.remotedatabase.ui.viewmodel.UpdateViewModel

object DestinasiUpdate : DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Mhs"
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun UpdateScreen(
    nim: String,
    navigateBack: () -> Unit,
    viewModel: UpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(nim) {
        viewModel.loadMahasiswa(nim) // Load data untuk di-update
    }

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = "Update Mahasiswa",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        FormUpdate(
            viewModel = viewModel,
            onSaveClick = {
                viewModel.updateMahasiswa(
                    onSuccess = { navigateBack() },
                    onError = { Text(stringResource(R.string.retry)) }
                )
            },
            isUpdate = true,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun FormUpdate(
    viewModel: UpdateViewModel,
    onSaveClick: () -> Unit,
    isUpdate: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = viewModel.uiState.nama,
            onValueChange = { viewModel.updateField("nama", it) },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = viewModel.uiState.nim,
            onValueChange = { viewModel.updateField("nim", it) },
            label = { Text("NIM") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !isUpdate // NIM tidak bisa diubah
        )
        OutlinedTextField(
            value = viewModel.uiState.alamat,
            onValueChange = { viewModel.updateField("alamat", it) },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = viewModel.uiState.kelas,
            onValueChange = { viewModel.updateField("kelas", it) },
            label = { Text("Kelas") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = viewModel.uiState.angkatan,
            onValueChange = { viewModel.updateField("angkatan", it) },
            label = { Text("Angkatan") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}
