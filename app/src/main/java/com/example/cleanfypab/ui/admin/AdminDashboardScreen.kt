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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminDashboardScreen(
    onNotificationClick: () -> Unit = {} // ✅ TIDAK DIUBAH
) {

    val bgColor = Color(0xFF0F2A1D)
    val cardColor = Color(0xFF163828)
    val green = Color(0xFF2DFF8F)
    val red = Color(0xFFFF6B6B)
    val yellow = Color(0xFFFFC107)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
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
                    Text("CLEANFY", color = Color.Gray, fontSize = 12.sp)
                    Text(
                        "Pengguna Admin",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // 🔔 IKON NOTIFIKASI
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(cardColor, CircleShape)
                        .clickable { onNotificationClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = "Notifikasi",
                        tint = Color.White
                    )
                }
            }
        }

        /* ================= STATISTIK ================= */
        item {
            Text(
                text = "Statistik Hari Ini",
                color = Color.White,
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
                cardColor
            )
        }

        item {
            DashboardCard(
                "Ruangan Bersih",
                "12",
                "Siap digunakan",
                Icons.Default.CheckCircle,
                green,
                cardColor
            )
        }

        item {
            DashboardCard(
                "Belum Dibersihkan",
                "13",
                "Perlu tindakan",
                Icons.Default.Warning,
                red,
                cardColor
            )
        }

        item {
            DashboardCard(
                "Laporan Hari Ini",
                "5",
                "Masuk hari ini",
                Icons.Default.Description,
                yellow,
                cardColor
            )
        }

        /* ================= AKSI CEPAT ================= */
        item {
            Text("Aksi Cepat", color = Color.White, fontWeight = FontWeight.Bold)
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
                    cardColor,
                    Modifier.weight(1f)
                )
                QuickActionCard(
                    "Lihat Laporan",
                    "5 masalah aktif",
                    Icons.AutoMirrored.Filled.List,
                    cardColor,
                    Modifier.weight(1f)
                )
            }
        }

        /* ================= AKTIVITAS ================= */
        item {
            Text("Aktivitas Terbaru", color = Color.White, fontWeight = FontWeight.Bold)
        }

        item {
            ActivityCard(
                "Ruang 304 - AC Bermasalah",
                "Dilaporkan oleh petugas kebersihan",
                "2 menit lalu",
                red,
                cardColor
            )
        }

        item {
            ActivityCard(
                "Ruang 105 - Pembersihan Tertunda",
                "Menunggu petugas",
                "1 jam lalu",
                yellow,
                cardColor
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
    cardColor: Color
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
            Text(title, color = Color.Gray, fontSize = 12.sp)
            Text(value, color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(subtitle, color = accent, fontSize = 12.sp)
        }

        Box(
            modifier = Modifier
                .size(44.dp)
                .background(accent.copy(alpha = 0.2f), CircleShape),
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
    cardColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(cardColor, RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Icon(icon, null, tint = Color(0xFF2DFF8F))
        Spacer(Modifier.height(8.dp))
        Text(title, color = Color.White, fontWeight = FontWeight.Bold)
        Text(subtitle, color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
fun ActivityCard(
    title: String,
    subtitle: String,
    time: String,
    accent: Color,
    cardColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(cardColor, RoundedCornerShape(20.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(title, color = Color.White, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Color.Gray, fontSize = 12.sp)
        }
        Text(time, color = accent, fontSize = 12.sp)
    }
}
