package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminNotificationScreen(
    onBack: () -> Unit = {},
    onAssignTask: () -> Unit = {}
) {
    val bg = Color(0xFF0F2A1D)
    val card = Color(0xFF163828)
    val border = Color(0xFF245C3A)
    val green = Color(0xFF2DFF8F)

    var selected by remember { mutableStateOf("Semua") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(16.dp)
    ) {

        /* ===== HEADER ===== */
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Kembali",
                tint = Color.White,
                modifier = Modifier.clickable { onBack() }
            )

            Spacer(Modifier.weight(1f))

            Text(
                "Notifikasi",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.weight(1f))

            Text(
                "Tandai semua dibaca",
                color = green,
                fontSize = 13.sp
            )
        }

        Spacer(Modifier.height(16.dp))

        /* ===== FILTER ===== */
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NotificationChip("Semua", selected, green) { selected = "Semua" }
            NotificationChip("Laporan", selected, green) { selected = "Laporan" }
            NotificationChip("Tugas", selected, green) { selected = "Tugas" }
            NotificationChip("Ruangan", selected, green) { selected = "Ruangan" }
        }

        Spacer(Modifier.height(20.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {

            item { SectionTitle("BARU") }

            item {
                NotificationCard(
                    title = "Masalah pipa dilaporkan",
                    subtitle = "Ruang 102 – Tim pemeliharaan melaporkan kebocoran pada wastafel.",
                    time = "2 menit lalu",
                    icon = Icons.Default.Warning,
                    accent = green,
                    card = card,
                    border = border,
                    showActions = true,
                    onAssign = onAssignTask
                )
            }

            item {
                NotificationCard(
                    title = "Pembersihan Selesai",
                    subtitle = "Ruang 205 – Jane Doe telah menyelesaikan tugas pembersihan.",
                    time = "1 jam lalu",
                    icon = Icons.Default.CheckCircle,
                    accent = green,
                    card = card,
                    border = border
                )
            }

            item { SectionTitle("SEBELUMNYA") }

            item {
                NotificationCard(
                    title = "Pembaruan Status Ruangan",
                    subtitle = "Ruang 401 ditandai sebagai Kosong/Kotor.",
                    time = "3 jam lalu",
                    icon = Icons.Default.Sync,
                    accent = green,
                    card = card,
                    border = border
                )
            }

            item {
                NotificationCard(
                    title = "Pemeliharaan Sistem",
                    subtitle = "Pemeliharaan basis data dijadwalkan pukul 02:00.",
                    time = "5 jam lalu",
                    icon = Icons.Default.Settings,
                    accent = green,
                    card = card,
                    border = border
                )
            }
        }
    }
}

/* ================= KOMPONEN ================= */

@Composable
private fun NotificationChip(
    text: String,
    selected: String,
    accent: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                if (selected == text) accent else Color.Transparent,
                RoundedCornerShape(20.dp)
            )
            .border(1.dp, accent, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text,
            color = if (selected == text) Color.Black else Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        color = Color.Gray,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    )
}

@Composable
private fun NotificationCard(
    title: String,
    subtitle: String,
    time: String,
    icon: ImageVector,
    accent: Color,
    card: Color,
    border: Color,
    showActions: Boolean = false,
    onAssign: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(card, RoundedCornerShape(16.dp))
            .border(1.dp, border, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(accent.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = accent)
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold)
                Text(subtitle, color = Color.Gray, fontSize = 12.sp)
            }

            Text(time, color = accent, fontSize = 11.sp)
        }

        if (showActions) {
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onAssign,
                    colors = ButtonDefaults.buttonColors(containerColor = accent),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Tetapkan Tugas", color = Color.Black, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = {},
                    border = BorderStroke(1.dp, accent),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Lihat Detail", color = accent)
                }
            }
        }
    }
}
