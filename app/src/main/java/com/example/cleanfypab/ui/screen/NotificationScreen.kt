package com.example.cleanfypab.ui.screen

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun NotificationScreen(nav: NavHostController) {

    /* ===== PALET WARNA (SESUAI LOGIN & HOME) ===== */
    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF6FBF8),
            Color(0xFFE9F5EE)
        )
    )

    val card = Color.White
    val borderSoft = Color(0xFFE0E0E0)

    val primaryGreen = Color(0xFF2ECC71)
    val red = Color(0xFFE53935)
    val orange = Color(0xFFFFA000)

    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    var selectedTab by remember { mutableStateOf("TODO") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .padding(16.dp)
    ) {

        /* ================= HEADER ================= */
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = darkText,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { nav.popBackStack() }
            )

            Spacer(Modifier.width(12.dp))

            Text(
                "Pengingat",
                color = darkText,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(20.dp))

        /* ================= FILTER CHIPS ================= */
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            FilterChipLight("To Do", selectedTab == "TODO", primaryGreen) { selectedTab = "TODO" }
            FilterChipLight("Terlambat", selectedTab == "OVERDUE", red) { selectedTab = "OVERDUE" }
            FilterChipLight("Mendesak", selectedTab == "URGENT", orange) { selectedTab = "URGENT" }
        }

        Spacer(Modifier.height(20.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(18.dp)) {

            if (selectedTab == "OVERDUE") {
                item { SectionHeaderLight("PERLU TINDAKAN", red) }

                item {
                    NotificationCardLight(
                        icon = Icons.Default.Report,
                        iconColor = red,
                        title = "Kirim Laporan Insiden",
                        subtitle = "Insiden #4021 - Ruang 104",
                        badgeText = "TERLAMBAT",
                        badgeColor = red,
                        timeText = "Terlambat 45 menit"
                    )
                }
            }

            if (selectedTab == "URGENT") {
                item { SectionHeaderLight("MENDESAK", orange) }

                item {
                    NotificationCardLight(
                        icon = Icons.Default.Build,
                        iconColor = orange,
                        title = "Pintu Rusak",
                        subtitle = "Ruang 302 - Perlu segera dicek",
                        badgeText = "URGENT",
                        badgeColor = orange,
                        timeText = "Harus segera ditangani",
                        showLeftAccent = true
                    )
                }
            }

            if (selectedTab == "TODO") {
                item { SectionHeaderLight("TUGAS HARI INI", primaryGreen) }

                item {
                    NotificationCardLight(
                        icon = Icons.Default.Schedule,
                        iconColor = primaryGreen,
                        title = "Inspeksi Lantai 2",
                        subtitle = "Pemeriksaan rutin keamanan",
                        badgeText = "TUGAS",
                        badgeColor = primaryGreen,
                        timeText = "Pukul 14:00 (1 jam)"
                    )
                }
            }

            item {
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("SELESAI / RIWAYAT", color = grayText, fontWeight = FontWeight.Bold)
                    Text("Lihat Semua", color = primaryGreen, fontSize = 13.sp)
                }
            }

            item {
                NotificationCardLight(
                    icon = Icons.Default.CheckCircle,
                    iconColor = primaryGreen,
                    title = "Laporan Harian #4022",
                    subtitle = "Berhasil tersinkron ke server",
                    badgeText = "SELESAI",
                    badgeColor = primaryGreen,
                    timeText = "Kemarin",
                    faded = true
                )
            }
        }
    }
}

/* ================= COMPONENTS (LIGHT) ================= */

@Composable
private fun FilterChipLight(
    text: String,
    active: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                if (active) color.copy(alpha = 0.15f) else Color.White,
                RoundedCornerShape(50)
            )
            .border(
                1.dp,
                if (active) color else Color(0xFFE0E0E0),
                RoundedCornerShape(50)
            )
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 10.dp)
    ) {
        Text(
            text,
            color = if (active) color else Color(0xFF1E2D28),
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun SectionHeaderLight(text: String, color: Color) {
    Text(
        text,
        color = color,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp
    )
}

@Composable
private fun NotificationCardLight(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String,
    badgeText: String,
    badgeColor: Color,
    timeText: String,
    showLeftAccent: Boolean = false,
    faded: Boolean = false
) {
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = if (faded) 0.6f else 1f), RoundedCornerShape(18.dp))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {

        if (showLeftAccent) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(64.dp)
                    .background(iconColor, RoundedCornerShape(4.dp))
            )
            Spacer(Modifier.width(12.dp))
        }

        Box(
            modifier = Modifier
                .size(44.dp)
                .background(iconColor.copy(alpha = 0.15f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = iconColor)
        }

        Spacer(Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = darkText, fontWeight = FontWeight.Bold)
            Text(subtitle, color = grayText, fontSize = 12.sp)

            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .background(badgeColor.copy(alpha = 0.15f), RoundedCornerShape(50))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        badgeText,
                        color = badgeColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    timeText,
                    color = badgeColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
