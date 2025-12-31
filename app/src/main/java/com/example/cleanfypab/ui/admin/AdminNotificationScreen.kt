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
import androidx.compose.ui.graphics.Brush
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

    /* ===== WARNA CLEANIFY ===== */
    val bgGradient = Brush.verticalGradient(
        listOf(
            Color(0xFFF6FBF8),
            Color(0xFFE9F5EE)
        )
    )

    val cardColor = Color.White
    val borderSoft = Color(0xFFE0E0E0)

    val green = Color(0xFF2ECC71)
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    var selected by remember { mutableStateOf("Semua") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
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
                tint = darkText,
                modifier = Modifier.clickable { onBack() }
            )

            Spacer(Modifier.weight(1f))

            Text(
                "Notifikasi",
                color = darkText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.weight(1f))

            Text(
                "Tandai semua",
                color = green,
                fontSize = 13.sp,
                modifier = Modifier.clickable { }
            )
        }

        Spacer(Modifier.height(16.dp))

        /* ===== FILTER ===== */
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NotificationChipLight("Semua", selected, green) { selected = "Semua" }
            NotificationChipLight("Laporan", selected, green) { selected = "Laporan" }
            NotificationChipLight("Tugas", selected, green) { selected = "Tugas" }
            NotificationChipLight("Ruangan", selected, green) { selected = "Ruangan" }
        }

        Spacer(Modifier.height(20.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {

            item { SectionTitleLight("BARU") }

            item {
                NotificationCardLight(
                    title = "Masalah pipa dilaporkan",
                    subtitle = "Ruang 102 – Kebocoran pada wastafel.",
                    time = "2 menit lalu",
                    icon = Icons.Default.Warning,
                    accent = green,
                    card = cardColor,
                    border = borderSoft,
                    darkText = darkText,
                    grayText = grayText,
                    showActions = true,
                    onAssign = onAssignTask
                )
            }

            item {
                NotificationCardLight(
                    title = "Pembersihan Selesai",
                    subtitle = "Ruang 205 telah dibersihkan.",
                    time = "1 jam lalu",
                    icon = Icons.Default.CheckCircle,
                    accent = green,
                    card = cardColor,
                    border = borderSoft,
                    darkText = darkText,
                    grayText = grayText
                )
            }

            item { SectionTitleLight("SEBELUMNYA") }

            item {
                NotificationCardLight(
                    title = "Pembaruan Status Ruangan",
                    subtitle = "Ruang 401 ditandai Perlu Dicek.",
                    time = "3 jam lalu",
                    icon = Icons.Default.Sync,
                    accent = green,
                    card = cardColor,
                    border = borderSoft,
                    darkText = darkText,
                    grayText = grayText
                )
            }

            item {
                NotificationCardLight(
                    title = "Pemeliharaan Sistem",
                    subtitle = "Maintenance dijadwalkan pukul 02:00.",
                    time = "5 jam lalu",
                    icon = Icons.Default.Settings,
                    accent = green,
                    card = cardColor,
                    border = borderSoft,
                    darkText = darkText,
                    grayText = grayText
                )
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

/* ================= KOMPONEN ================= */

@Composable
private fun NotificationChipLight(
    text: String,
    selected: String,
    accent: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                if (selected == text) accent.copy(alpha = 0.15f) else Color.Transparent,
                RoundedCornerShape(20.dp)
            )
            .border(
                1.dp,
                if (selected == text) accent else Color(0xFFE0E0E0),
                RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text,
            color = if (selected == text) accent else Color(0xFF1E2D28),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun SectionTitleLight(text: String) {
    Text(
        text,
        color = Color(0xFF6B7C75),
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    )
}

@Composable
private fun NotificationCardLight(
    title: String,
    subtitle: String,
    time: String,
    icon: ImageVector,
    accent: Color,
    card: Color,
    border: Color,
    darkText: Color,
    grayText: Color,
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
                    .background(accent.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = accent)
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = darkText, fontWeight = FontWeight.Bold)
                Text(subtitle, color = grayText, fontSize = 12.sp)
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
                    Text("Tetapkan Tugas", color = Color.White, fontWeight = FontWeight.Bold)
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
