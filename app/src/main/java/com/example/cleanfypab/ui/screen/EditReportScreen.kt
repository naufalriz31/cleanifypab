package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.NavHostController
import com.example.cleanfypab.viewmodel.EditReportViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditReportScreen(
    nav: NavHostController,
    taskId: String,
    vm: EditReportViewModel = viewModel()
) {
    val state by vm.state.collectAsState()

    val bgGradient = Brush.verticalGradient(listOf(Color(0xFFF6FBF8), Color(0xFFE9F5EE)))
    val card = Color.White
    val borderSoft = Color(0xFFE0E0E0)
    val green = Color(0xFF2ECC71)
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    var statusExpanded by remember { mutableStateOf(false) }
    val statusOptions = listOf(
        "ASSIGNED" to "Menunggu",
        "IN_PROGRESS" to "Dikerjakan",
        "DONE" to "Selesai"
    )

    LaunchedEffect(taskId) { vm.load(taskId) }

    // selesai simpan -> balik
    LaunchedEffect(state.saved) {
        if (state.saved) nav.popBackStack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = darkText)
            }
            Text("Edit Laporan", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = darkText)
        }

        Spacer(Modifier.height(12.dp))

        if (state.loading) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
            return@Column
        }

        state.error?.let {
            Text(it, color = Color.Red, fontSize = 13.sp)
            Spacer(Modifier.height(8.dp))
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = card),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, borderSoft, RoundedCornerShape(18.dp))
        ) {
            Column(Modifier.padding(16.dp)) {

                Text(
                    text = state.task?.roomName ?: "Ruangan",
                    fontWeight = FontWeight.Bold,
                    color = darkText,
                    fontSize = 16.sp
                )

                Spacer(Modifier.height(14.dp))

                // Status dropdown
                ExposedDropdownMenuBox(
                    expanded = statusExpanded,
                    onExpandedChange = { statusExpanded = !statusExpanded }
                ) {
                    val statusLabel = statusOptions.firstOrNull { it.first == state.status }?.second ?: "Menunggu"

                    OutlinedTextField(
                        value = statusLabel,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Status") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(14.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = statusExpanded,
                        onDismissRequest = { statusExpanded = false }
                    ) {
                        statusOptions.forEach { (value, label) ->
                            DropdownMenuItem(
                                text = { Text(label) },
                                onClick = {
                                    vm.setStatus(value)
                                    statusExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(14.dp))

                // Note
                OutlinedTextField(
                    value = state.note,
                    onValueChange = vm::setNote,
                    label = { Text("Catatan (opsional)") },
                    placeholder = { Text("Misal: lantai basah, butuh pel tambahan", color = grayText) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    minLines = 3
                )

                Spacer(Modifier.height(14.dp))

                Text("Checklist", fontWeight = FontWeight.Bold, color = darkText)
                Spacer(Modifier.height(8.dp))

                ChecklistRow("Lantai sudah bersih", state.checkFloor) { vm.setCheckFloor(it) }
                ChecklistRow("Meja/permukaan rapi", state.checkTable) { vm.setCheckTable(it) }
                ChecklistRow("Sampah dibuang", state.checkTrash) { vm.setCheckTrash(it) }

                Spacer(Modifier.height(18.dp))

                Button(
                    onClick = { vm.save(taskId) },
                    enabled = !state.saving,
                    colors = ButtonDefaults.buttonColors(containerColor = green),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text(
                        if (state.saving) "Menyimpan..." else "Simpan",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun ChecklistRow(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}
