package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun NotificationScreen(nav: NavHostController) {

    /* ===== COLOR PALETTE (SAMA DENGAN HOME) ===== */
    val bg = Color(0xFF0D1F15)
    val card = Color(0xFF14231C)
    val green = Color(0xFF00E676)
    val red = Color(0xFFE53935)
    val orange = Color(0xFFFFA000)
    val grayText = Color(0xFF9BA5A0)

    var selectedTab by remember { mutableStateOf("TODO") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(16.dp)
    ) {

        /* ================= HEADER ================= */
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { nav.popBackStack() }
            )

            Spacer(Modifier.width(12.dp))

            Text(
                "Pengingat",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(20.dp))

        /* ================= FILTER CHIPS ================= */
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            FilterChip("To Do", selectedTab == "TODO", green) { selectedTab = "TODO" }
            FilterChip("Terlambat", selectedTab == "OVERDUE", red) { selectedTab = "OVERDUE" }
            FilterChip("Mendesak", selectedTab == "URGENT", orange) { selectedTab = "URGENT" }
        }

        Spacer(Modifier.height(20.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(18.dp)) {

            /* ================= ACTION REQUIRED ================= */
            if (selectedTab == "OVERDUE") {
                item {
                    SectionHeader("PERLU TINDAKAN", red)
                }

                item {
                    NotificationCard(
                        icon = Icons.Default.Report,
                        iconBg = red,
                        title = "Kirim Laporan Insiden",
                        subtitle = "Insiden #4021 - Ruang 104",
                        badgeText = "TERLAMBAT",
                        badgeColor = red,
                        timeText = "Terlambat 45 menit",
                        cardColor = card
                    )
                }
            }

            /* ================= UPCOMING & URGENT ================= */
            if (selectedTab == "URGENT") {
                item {
                    SectionHeader("MENDESAK", orange)
                }

                item {
                    NotificationCard(
                        icon = Icons.Default.Build,
                        iconBg = orange,
                        title = "Pintu Rusak",
                        subtitle = "Ruang 302 - Perlu segera dicek",
                        badgeText = "URGENT",
                        badgeColor = orange,
                        timeText = "Harus segera ditangani",
                        cardColor = card,
                        showLeftAccent = true,
                        accentColor = orange
                    )
                }
            }

            /* ================= TODO ================= */
            if (selectedTab == "TODO") {
                item {
                    SectionHeader("TUGAS HARI INI", green)
                }

                item {
                    NotificationCard(
                        icon = Icons.Default.Schedule,
                        iconBg = green,
                        title = "Inspeksi Lantai 2",
                        subtitle = "Pemeriksaan rutin keamanan",
                        badgeText = "TUGAS",
                        badgeColor = green,
                        timeText = "Pukul 14:00 (1 jam)",
                        cardColor = card
                    )
                }
            }

            /* ================= HISTORY ================= */
            item {
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("SELESAI / RIWAYAT", color = grayText, fontWeight = FontWeight.Bold)
                    Text("Lihat Semua", color = green, fontSize = 13.sp)
                }
            }

            item {
                NotificationCard(
                    icon = Icons.Default.CheckCircle,
                    iconBg = green,
                    title = "Laporan Harian #4022",
                    subtitle = "Berhasil tersinkron ke server",
                    badgeText = "SELESAI",
                    badgeColor = green,
                    timeText = "Kemarin",
                    cardColor = card,
                    faded = true
                )
            }
        }
    }
}

/* ================= COMPONENTS ================= */

@Composable
private fun FilterChip(
    text: String,
    active: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                if (active) color else Color(0xFF1F2C25),
                RoundedCornerShape(50)
            )
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 10.dp)
    ) {
        Text(
            text,
            color = if (active) Color.Black else Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun SectionHeader(text: String, color: Color) {
    Text(
        text,
        color = color,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp
    )
}

@Composable
private fun NotificationCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconBg: Color,
    title: String,
    subtitle: String,
    badgeText: String,
    badgeColor: Color,
    timeText: String,
    cardColor: Color,
    showLeftAccent: Boolean = false,
    accentColor: Color = Color.Transparent,
    faded: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(cardColor.copy(alpha = if (faded) 0.6f else 1f), RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {

        if (showLeftAccent) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(64.dp)
                    .background(accentColor, RoundedCornerShape(4.dp))
            )
            Spacer(Modifier.width(12.dp))
        }

        Box(
            modifier = Modifier
                .size(44.dp)
                .background(iconBg.copy(alpha = 0.2f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = iconBg)
        }

        Spacer(Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Color(0xFF9BA5A0), fontSize = 12.sp)

            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .background(badgeColor.copy(alpha = 0.2f), RoundedCornerShape(50))
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
