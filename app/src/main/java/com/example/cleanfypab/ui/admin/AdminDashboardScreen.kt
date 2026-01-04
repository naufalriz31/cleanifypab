package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminDashboardScreen(
    onNotificationClick: () -> Unit = {}
) {

    /* ===== WARNA SESUAI LOGIN ===== */
    val bgGradient = Brush.verticalGradient(
        listOf(
            Color(0xFFF6FBF8),
            Color(0xFFE9F5EE)
        )
    )

    val cardColor = Color.White
    val borderSoft = Color(0xFFE0E0E0)

    val green = Color(0xFF2ECC71)
    val red = Color(0xFFE53935)
    val yellow = Color(0xFFFFC107)

    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        /* ================= HEADER ================= */
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("CLEANIFY", color = grayText, fontSize = 12.sp)
                    Text(
                        "Admin Dashboard",
                        color = darkText,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(Color.White, CircleShape)
                        .clickable { onNotificationClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = "Notifikasi",
                        tint = green
                    )
                }
            }
        }

        /* ================= STATISTIK ================= */
        item {
            Text(
                "Statistik Hari Ini",
                color = darkText,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        item {
            DashboardCard(
                "Total Ruangan",
                "30",
                "Keseluruhan",
                Icons.Default.Home,
                green,
                cardColor,
                darkText,
                grayText
            )
        }

        item {
            DashboardCard(
                "Ruangan Bersih",
                "12",
                "Siap digunakan",
                Icons.Default.CheckCircle,
                green,
                cardColor,
                darkText,
                grayText
            )
        }

        item {
            DashboardCard(
                "Belum Dibersihkan",
                "13",
                "Perlu tindakan",
                Icons.Default.Warning,
                red,
                cardColor,
                darkText,
                grayText
            )
        }

        item {
            DashboardCard(
                "Laporan Hari Ini",
                "5",
                "Masuk hari ini",
                Icons.Default.Description,
                yellow,
                cardColor,
                darkText,
                grayText
            )
        }

        /* ================= AKSI CEPAT ================= */
        item {
            Text("Aksi Cepat", color = darkText, fontWeight = FontWeight.Bold)
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionCard(
                    "Kelola Ruangan",
                    "Atur ketersediaan",
                    Icons.Default.MeetingRoom,
                    green,
                    cardColor,
                    darkText,
                    Modifier.weight(1f)
                )

                QuickActionCard(
                    "Lihat Petugas",
                    "10 petugas aktif",
                    Icons.AutoMirrored.Filled.List,
                    green,
                    cardColor,
                    darkText,
                    Modifier.weight(1f)
                )
            }
        }

        /* ================= AKTIVITAS ================= */
        item {
            Text("Aktivitas Terbaru", color = darkText, fontWeight = FontWeight.Bold)
        }

        item {
            ActivityCard(
                "Ruang 304 - AC Bermasalah",
                "Dilaporkan oleh petugas",
                "2 menit lalu",
                red,
                cardColor,
                darkText,
                grayText
            )
        }

        item {
            ActivityCard(
                "Ruang 105 - Pembersihan Tertunda",
                "Menunggu petugas",
                "1 jam lalu",
                yellow,
                cardColor,
                darkText,
                grayText
            )
        }

        item { Spacer(Modifier.height(32.dp)) }
    }
}

/* ================= KOMPONEN ================= */

@Composable
fun DashboardCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    accent: Color,
    cardColor: Color,
    darkText: Color,
    grayText: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(cardColor, RoundedCornerShape(20.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(title, color = grayText, fontSize = 12.sp)
            Text(value, color = darkText, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(subtitle, color = accent, fontSize = 12.sp)
        }

        Box(
            modifier = Modifier
                .size(46.dp)
                .background(accent.copy(alpha = 0.15f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = accent)
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accent: Color,
    cardColor: Color,
    darkText: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(cardColor, RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Icon(icon, null, tint = accent)
        Spacer(Modifier.height(8.dp))
        Text(title, color = darkText, fontWeight = FontWeight.Bold)
        Text(subtitle, color = Color(0xFF6B7C75), fontSize = 12.sp)
    }
}

@Composable
fun ActivityCard(
    title: String,
    subtitle: String,
    time: String,
    accent: Color,
    cardColor: Color,
    darkText: Color,
    grayText: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(cardColor, RoundedCornerShape(20.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(title, color = darkText, fontWeight = FontWeight.Bold)
            Text(subtitle, color = grayText, fontSize = 12.sp)
        }
        Text(time, color = accent, fontSize = 12.sp)
    }
}
