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
import androidx.compose.ui.graphics.Brush
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

    /* ===== PALET SAMA DENGAN AdminRoomScreen ===== */
    val bgGradient = Brush.verticalGradient(
        listOf(
            Color(0xFFF6FBF8),
            Color(0xFFE9F5EE)
        )
    )

    val card = Color.White
    val borderSoft = Color(0xFFE0E0E0)

    val green = Color(0xFF2ECC71)
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    var taskType by remember { mutableStateOf("ROOM") }
    var title by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var urgent by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 90.dp)
                .verticalScroll(rememberScrollState())
        ) {

            /* ================= HEADER ================= */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Batal",
                    color = green,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onCancel() }
                )

                Text(
                    "Buat Tugas",
                    color = darkText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.width(48.dp))
            }

            Spacer(Modifier.height(24.dp))

            SectionTitle("PENUGASAN")

            CardWhite(borderSoft) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, null, tint = grayText)
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Pilih Petugas", color = darkText, fontWeight = FontWeight.Bold)
                        Text("Siapa yang bertanggung jawab?", color = grayText, fontSize = 12.sp)
                    }
                    Icon(Icons.Default.ChevronRight, null, tint = grayText)
                }
            }

            Spacer(Modifier.height(24.dp))

            SectionTitle("KONTEKS & JENIS")

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                TypeChipLight("Spesifik Ruangan", taskType == "ROOM", green) {
                    taskType = "ROOM"
                }
                TypeChipLight("Umum", taskType == "GENERAL", green) {
                    taskType = "GENERAL"
                }
            }

            Spacer(Modifier.height(12.dp))

            CardWhite(borderSoft) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.MeetingRoom, null, tint = green)
                    Spacer(Modifier.width(12.dp))
                    Text("Cari Nomor Ruangan (mis. 101)", color = grayText)
                }
            }

            Spacer(Modifier.height(24.dp))

            SectionTitle("DETAIL TUGAS")

            CardWhite(borderSoft) {
                Text("JUDUL TUGAS", color = green, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))

                InputLight(
                    value = title,
                    placeholder = "mis. Perbaiki Unit AC",
                    green = green,
                    border = borderSoft
                ) { title = it }

                Spacer(Modifier.height(16.dp))

                Text("CATATAN", color = grayText, fontSize = 12.sp)
                Spacer(Modifier.height(4.dp))

                InputLight(
                    value = notes,
                    placeholder = "Tambahkan instruksi detail...",
                    height = 120.dp,
                    green = green,
                    border = borderSoft
                ) { notes = it }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text("${notes.length}/200", color = grayText, fontSize = 12.sp)
                }
            }

            Spacer(Modifier.height(24.dp))

            SectionTitle("WAKTU & PRIORITAS")

            CardWhite(borderSoft) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarToday, null, tint = green)
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Batas Waktu", color = darkText, fontWeight = FontWeight.Bold)
                        Text("Besok, 10:00", color = grayText, fontSize = 12.sp)
                    }
                    Icon(Icons.Default.ChevronRight, null, tint = grayText)
                }
            }

            Spacer(Modifier.height(12.dp))

            CardWhite(borderSoft) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.PriorityHigh, null, tint = Color(0xFFFF6B6B))
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Prioritas Tinggi", color = darkText, fontWeight = FontWeight.Bold)
                        Text("Tandai tugas ini sebagai mendesak", color = grayText, fontSize = 12.sp)
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

        /* ================= BUTTON ================= */
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
            Icon(Icons.Default.CheckCircle, null, tint = Color.White)
            Spacer(Modifier.width(8.dp))
            Text(
                "Tetapkan Tugas",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/* ================= COMPONENT ================= */

@Composable
private fun SectionTitle(text: String) {
    Text(text, color = Color(0xFF6B7C75), fontSize = 12.sp, fontWeight = FontWeight.Bold)
}

@Composable
private fun CardWhite(
    border: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(1.dp, border, RoundedCornerShape(16.dp))
            .padding(16.dp),
        content = content
    )
}

@Composable
private fun TypeChipLight(
    text: String,
    selected: Boolean,
    accent: Color,
    onClick: () -> Unit
) {
    Surface(
        color = if (selected) accent.copy(alpha = 0.15f) else Color.Transparent,
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, accent),
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (selected) accent else Color(0xFF1E2D28),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun InputLight(
    value: String,
    placeholder: String,
    height: Dp = 48.dp,
    green: Color,
    border: Color,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = green,
            unfocusedBorderColor = border,
            cursorColor = green,
            focusedTextColor = Color(0xFF1E2D28),
            unfocusedTextColor = Color(0xFF1E2D28)
        )
    )
}
