package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminCreateTaskScreen(
    onCancel: () -> Unit = {},
    onAssign: () -> Unit = {}
) {

    val bg = Color(0xFF0F2A1D)
    val cardBg = Color(0xFF163828)
    val border = Color(0xFF245C3A)
    val green = Color(0xFF2DFF8F)

    var taskType by remember { mutableStateOf("ROOM") }
    var title by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var urgent by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 90.dp)
                .verticalScroll(rememberScrollState())
        ) {

            /* HEADER */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Batal",
                    color = green,
                    modifier = Modifier.clickable { onCancel() }
                )

                Text(
                    "Buat Tugas",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            AdminTaskSectionTitle("PENUGASAN")

            AdminTaskCard(cardBg, border) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, null, tint = Color.Gray)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Pilih Petugas", color = Color.White)
                        Text("Siapa yang bertanggung jawab?", color = Color.Gray, fontSize = 12.sp)
                    }
                    Icon(Icons.Default.ChevronRight, null, tint = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            AdminTaskSectionTitle("KONTEKS & JENIS")

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AdminTaskTypeChip("Spesifik Ruangan", taskType == "ROOM", green) {
                    taskType = "ROOM"
                }
                AdminTaskTypeChip("Umum", taskType == "GENERAL", green) {
                    taskType = "GENERAL"
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            AdminTaskCard(cardBg, border) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.MeetingRoom, null, tint = green)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Cari Nomor Ruangan (mis. 101)", color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            AdminTaskSectionTitle("DETAIL TUGAS")

            AdminTaskCard(cardBg, border) {
                Column {

                    Text("JUDUL TUGAS", color = green, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    AdminTaskInput(
                        value = title,
                        placeholder = "mis., Perbaiki Unit AC"
                    ) { title = it }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("CATATAN", color = Color.Gray, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    AdminTaskInput(
                        value = notes,
                        placeholder = "Tambahkan instruksi detail...",
                        height = 120.dp
                    ) { notes = it }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text("${notes.length}/200", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            AdminTaskSectionTitle("WAKTU & PRIORITAS")

            AdminTaskCard(cardBg, border) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarToday, null, tint = green)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Batas Waktu", color = Color.White)
                        Text("Besok, 10:00", color = Color.Gray, fontSize = 12.sp)
                    }
                    Icon(Icons.Default.ChevronRight, null, tint = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            AdminTaskCard(cardBg, border) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.PriorityHigh, null, tint = Color.Red)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Prioritas Tinggi", color = Color.White)
                        Text("Tandai tugas ini sebagai mendesak", color = Color.Gray, fontSize = 12.sp)
                    }
                    Switch(
                        checked = urgent,
                        onCheckedChange = { urgent = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = green,
                            checkedTrackColor = green.copy(alpha = 0.4f)
                        )
                    )
                }
            }
        }

        Button(
            onClick = onAssign,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = green),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.CheckCircle, null, tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Tetapkan Tugas", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

/* ===== COMPONENTS ===== */

@Composable
private fun AdminTaskSectionTitle(text: String) {
    Text(text, color = Color.Gray, fontWeight = FontWeight.Bold, fontSize = 12.sp)
}

@Composable
private fun AdminTaskCard(
    bg: Color,
    border: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(bg, RoundedCornerShape(16.dp))
            .border(1.dp, border, RoundedCornerShape(16.dp))
            .padding(16.dp),
        content = content
    )
}

@Composable
private fun AdminTaskTypeChip(
    text: String,
    selected: Boolean,
    accent: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                if (selected) accent else Color.Transparent,
                RoundedCornerShape(12.dp)
            )
            .border(1.dp, accent, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(
            text,
            color = if (selected) Color.Black else Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun AdminTaskInput(
    value: String,
    placeholder: String,
    height: Dp = 48.dp,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF163828),
            unfocusedContainerColor = Color(0xFF163828),
            focusedBorderColor = Color(0xFF2DFF8F),
            unfocusedBorderColor = Color(0xFF245C3A),
            cursorColor = Color(0xFF2DFF8F)
        ),
        shape = RoundedCornerShape(12.dp)
    )
}
