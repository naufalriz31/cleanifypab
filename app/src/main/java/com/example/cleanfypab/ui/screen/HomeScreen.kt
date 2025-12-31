package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cleanfypab.ui.navigation.Routes

@Composable
fun HomeScreen(nav: NavHostController) {

    /* ===== PALET WARNA ===== */
    val bgGradient = Brush.verticalGradient(
        listOf(Color(0xFFF6FBF8), Color(0xFFE9F5EE))
    )

    val card = Color.White
    val primaryGreen = Color(0xFF2ECC71)
    val blue = Color(0xFF4DA3FF)
    val orange = Color(0xFFFFA000)

    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)
    val borderSoft = Color(0xFFE0E0E0)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        /* ================= HEADER ================= */
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Selamat Pagi, Olivia",
                        color = darkText,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Semoga harimu produktif",
                        color = grayText,
                        fontSize = 13.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(card, CircleShape)
                        .border(1.dp, borderSoft, CircleShape)
                        .clickable { nav.navigate(Routes.NOTIFICATION) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = null,
                        tint = darkText
                    )
                }
            }
        }

        /* ================= TUGAS HARI INI ================= */
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Tugas Hari Ini", color = darkText, fontWeight = FontWeight.Bold)
                Text(
                    "Lihat Semua",
                    color = primaryGreen,
                    fontSize = 13.sp,
                    modifier = Modifier.clickable {
                        nav.navigate(Routes.TASK_TODAY)
                    }
                )
            }
        }

        item {
            TaskCard("Ruang 305", "Perlu Pembersihan Mendalam", orange)
        }

        item {
            TaskCard("Ruang 405", "Pembersihan Standar", blue)
        }

        /* ================= PROGRES ================= */
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(card, RoundedCornerShape(20.dp))
                    .border(1.dp, borderSoft, RoundedCornerShape(20.dp))
                    .padding(16.dp)
            ) {

                Text("Progres Hari Ini", color = darkText, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("3/10 ruangan", color = darkText)
                    Spacer(Modifier.weight(1f))
                    Text("30%", color = primaryGreen, fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = 0.3f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    color = primaryGreen,
                    trackColor = Color(0xFFE0EFE7)
                )

                Spacer(Modifier.height(6.dp))
                Text(
                    "7 ruangan tersisa dalam antrean",
                    color = grayText,
                    fontSize = 12.sp
                )
            }
        }

        /* ================= TOMBOL AKSI ================= */
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(primaryGreen, RoundedCornerShape(18.dp))
                        .clickable { nav.navigate(Routes.SCAN) }
                        .padding(vertical = 22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.QrCodeScanner,
                        null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text("Scan Ruangan", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(card, RoundedCornerShape(18.dp))
                        .border(1.dp, borderSoft, RoundedCornerShape(18.dp))
                        .clickable { nav.navigate(Routes.HISTORY) }
                        .padding(vertical = 22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Warning,
                        null,
                        tint = orange,
                        modifier = Modifier.size(26.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text("Laporan Masuk", color = darkText, fontWeight = FontWeight.Bold)
                    Text("3 Perlu Tindakan", color = orange, fontSize = 12.sp)
                }
            }
        }

        /* ================= LAPORAN TERBARU ================= */
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Laporan Terbaru", color = darkText, fontWeight = FontWeight.Bold)
                Text(
                    "Lihat Semua",
                    color = primaryGreen,
                    fontSize = 13.sp,
                    modifier = Modifier.clickable {
                        nav.navigate(Routes.HISTORY)
                    }
                )
            }
        }

        item {
            RecentReportItem(
                title = "Ruang Meeting A101",
                subtitle = "Selesai • 10:45",
                statusColor = primaryGreen
            ) { nav.navigate("detail/1") }
        }

        item {
            RecentReportItem(
                title = "Lobi Utama",
                subtitle = "Sedang Dibersihkan • 09:30",
                statusColor = orange
            ) { nav.navigate("detail/2") }
        }

        item {
            RecentReportItem(
                title = "Toilet Lt.2",
                subtitle = "Selesai • 08:12",
                statusColor = primaryGreen
            ) { nav.navigate("detail/3") }
        }

        item { Spacer(Modifier.height(30.dp)) }
    }
}

/* ================= KOMPONEN ================= */

@Composable
private fun TaskCard(
    title: String,
    subtitle: String,
    indicatorColor: Color
) {
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)
    val borderSoft = Color(0xFFE0E0E0)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(18.dp))
            .border(1.dp, borderSoft, RoundedCornerShape(18.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .width(6.dp)
                .height(48.dp)
                .background(indicatorColor, RoundedCornerShape(6.dp))
        )

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = darkText, fontWeight = FontWeight.Bold)
            Text(subtitle, color = grayText, fontSize = 12.sp)
        }

        Box(
            modifier = Modifier
                .size(22.dp)
                .border(2.dp, Color(0xFFE0E0E0), CircleShape)
        )
    }
}

@Composable
private fun RecentReportItem(
    title: String,
    subtitle: String,
    statusColor: Color,
    onClick: () -> Unit
) {
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)
    val borderSoft = Color(0xFFE0E0E0)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(1.dp, borderSoft, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(statusColor, CircleShape)
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = darkText, fontWeight = FontWeight.SemiBold)
            Text(subtitle, color = grayText, fontSize = 12.sp)
        }
        Text("›", color = grayText, fontSize = 26.sp)
    }
}
