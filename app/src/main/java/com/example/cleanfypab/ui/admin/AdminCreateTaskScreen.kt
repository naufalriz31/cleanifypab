package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cleanfypab.data.model.PetugasFirestore
import com.example.cleanfypab.data.model.RoomFirestore
import com.example.cleanfypab.viewmodel.admin.AdminTaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCreateTaskScreen(
    onCancel: () -> Unit,
    onAssign: () -> Unit,
    vm: AdminTaskViewModel = viewModel()
) {
    val state by vm.state.collectAsState()

    /* ===== STYLE CLEANIFY ===== */
    val bgGradient = Brush.verticalGradient(listOf(Color(0xFFF6FBF8), Color(0xFFE9F5EE)))
    val card = Color.White
    val borderSoft = Color(0xFFE0E0E0)
    val green = Color(0xFF2ECC71)
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    var selectedPetugas by remember { mutableStateOf<PetugasFirestore?>(null) }
    var selectedRoom by remember { mutableStateOf<RoomFirestore?>(null) }
    var note by remember { mutableStateOf("") }

    var petugasExpanded by remember { mutableStateOf(false) }
    var roomExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { vm.loadData() }

    // ✅ kalau sukses create task → balik
    LaunchedEffect(state.success) {
        if (state.success) {
            vm.clearFlags()
            onAssign()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .padding(16.dp)
    ) {

        /* ===== HEADER ===== */
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onCancel) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = darkText)
            }
            Text(
                "Assign Task",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = darkText
            )
        }

        Spacer(Modifier.height(14.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = card),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, borderSoft, RoundedCornerShape(20.dp))
        ) {
            Column(Modifier.padding(16.dp)) {

                Text("Pilih Petugas & Ruangan", color = darkText, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))

                // ===== PETUGAS DROPDOWN =====
                ExposedDropdownMenuBox(
                    expanded = petugasExpanded,
                    onExpandedChange = { petugasExpanded = !petugasExpanded }

                ) {
                    OutlinedTextField(
                        value = selectedPetugas?.let { "${it.name}" } ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Petugas") },
                        placeholder = { Text("Pilih petugas", color = grayText) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(14.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = petugasExpanded,
                        onDismissRequest = { petugasExpanded = false }

                    ) {
                        state.petugas.forEach { p ->
                            DropdownMenuItem(
                                text = { Text("${p.name} ") },
                                onClick = {
                                    selectedPetugas = p
                                    petugasExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // ===== ROOM DROPDOWN =====
                ExposedDropdownMenuBox(
                    expanded = roomExpanded,
                    onExpandedChange = { roomExpanded = !roomExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedRoom?.let { "${it.name} • ${it.qrValue}" } ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Ruangan") },
                        placeholder = { Text("Pilih ruangan", color = grayText) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(14.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = roomExpanded,
                        onDismissRequest = { roomExpanded = false }
                    ) {
                        state.rooms.forEach { r ->
                            DropdownMenuItem(
                                text = { Text("${r.name} • ${r.qrValue}") },
                                onClick = {
                                    selectedRoom = r
                                    roomExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Catatan Tugas") },
                    placeholder = { Text("Contoh: Bersihkan lantai & buang sampah", color = grayText) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    minLines = 3
                )

                Spacer(Modifier.height(14.dp))

                state.error?.let {
                    Text(it, color = Color.Red, fontSize = 13.sp)
                    Spacer(Modifier.height(8.dp))
                }

                Button(
                    onClick = { vm.createTask(selectedPetugas, selectedRoom, note) },
                    enabled = !state.loading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = green)
                ) {
                    if (state.loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                        Spacer(Modifier.width(10.dp))
                        Text("Menyimpan...", color = Color.White, fontWeight = FontWeight.Bold)
                    } else {
                        Text("Assign Task", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(Modifier.height(10.dp))

        if (state.loading && (state.petugas.isEmpty() || state.rooms.isEmpty())) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Memuat data...", color = grayText)
            }
        }
    }
}
