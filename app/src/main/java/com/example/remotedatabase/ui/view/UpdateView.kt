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

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(
    nim: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(nim) {
        viewModel.loadMahasiswa(nim)
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
        Column(
            modifier = modifier
                .padding(innerPadding)
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
                onValueChange = { viewModel.updateField("Nim", it) },
                label = { Text("Nim") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = viewModel.uiState.alamat,
                onValueChange = { viewModel.updateField("alamat", it) },
                label = { Text("Alamat") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = viewModel.uiState.jenisKelamin,
                onValueChange = { viewModel.updateField("jenisKelamin", it) },
                label = { Text("Jenis Kelamin") },
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
                onClick = {
                    viewModel.updateMahasiswa(
                        onSuccess = { navigateBack() },
                        onError = {  }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Simpan")
            }
            Divider(
                thickness = 5.dp,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}
