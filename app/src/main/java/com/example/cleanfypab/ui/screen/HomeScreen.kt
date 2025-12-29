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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cleanfypab.ui.navigation.Routes

@Composable
fun HomeScreen(nav: NavHostController) {

    /* ===== PALET WARNA ===== */
    val bg = Color(0xFF0D1F15)
    val card = Color(0xFF14231C)
    val cardAlt = Color(0xFF1F2C25)
    val green = Color(0xFF00E676)
    val blue = Color(0xFF2196F3)
    val orange = Color(0xFFFFA000)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
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
                        text = "Selamat Pagi, Olivia",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Semoga harimu produktif",
                        color = Color(0xFF9BA5A0),
                        fontSize = 13.sp
                    )
                }

                // 🔔 TOMBOL NOTIFIKASI
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(cardAlt, CircleShape)
                        .clickable {
                            nav.navigate(Routes.NOTIFICATION)
                        },
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

        /* ================= TUGAS HARI INI ================= */
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Tugas Hari Ini", color = Color.White, fontWeight = FontWeight.Bold)
                Text(
                    "Lihat Semua",
                    color = green,
                    fontSize = 13.sp,
                    modifier = Modifier.clickable {
                        nav.navigate(Routes.TASK_TODAY)
                    }
                )
            }
        }

        item {
            TaskCard(
                title = "Ruang 305",
                subtitle = "Perlu Pembersihan Mendalam",
                indicatorColor = orange,
                cardColor = card
            )
        }

        item {
            TaskCard(
                title = "Ruang 405",
                subtitle = "Pembersihan Standar",
                indicatorColor = blue,
                cardColor = card
            )
        }

        /* ================= PROGRES ================= */
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(card, RoundedCornerShape(20.dp))
                    .padding(16.dp)
            ) {

                Text("Progres Hari Ini", color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("3/10 ruangan", color = Color.White)
                    Spacer(Modifier.weight(1f))
                    Text("30%", color = green, fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = 0.3f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    color = green,
                    trackColor = Color(0xFF0F2A1D)
                )

                Spacer(Modifier.height(6.dp))
                Text(
                    "7 ruangan tersisa dalam antrean",
                    color = Color(0xFF9BA5A0),
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
                        .background(blue, RoundedCornerShape(18.dp))
                        .clickable { nav.navigate(Routes.SCAN) }
                        .padding(vertical = 22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.QrCodeScanner,
                        contentDescription = null,
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
                        .clickable { nav.navigate(Routes.HISTORY) }
                        .padding(vertical = 22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = null,
                        tint = orange,
                        modifier = Modifier.size(26.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text("Laporan Masuk", color = Color.White, fontWeight = FontWeight.Bold)
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
                Text("Laporan Terbaru", color = Color.White, fontWeight = FontWeight.Bold)
                Text(
                    "Lihat Semua",
                    color = green,
                    fontSize = 13.sp,
                    modifier = Modifier.clickable {
                        nav.navigate(Routes.HISTORY)
                    }
                )
            }
        }

        item {
            RecentReportItem("Ruang 301", "Selesai • 10:45", Color(0xFF2ECC71))
        }

        item {
            RecentReportItem("Ruang 205", "Sedang Dibersihkan • 09:30", orange)
        }

        item {
            RecentReportItem("Ruang 101", "Selesai • 08:12", Color(0xFF2ECC71))
        }

        item { Spacer(Modifier.height(30.dp)) }
    }
}

/* ================= KOMPONEN ================= */

@Composable
private fun TaskCard(
    title: String,
    subtitle: String,
    indicatorColor: Color,
    cardColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(cardColor, RoundedCornerShape(18.dp))
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
            Text(title, color = Color.White, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Color(0xFF9BA5A0), fontSize = 12.sp)
        }

        Box(
            modifier = Modifier
                .size(22.dp)
                .border(2.dp, Color(0xFF3C4B44), CircleShape)
        )
    }
}

@Composable
private fun RecentReportItem(
    title: String,
    subtitle: String,
    statusColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF14231C), RoundedCornerShape(16.dp))
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
            Text(title, color = Color.White, fontWeight = FontWeight.SemiBold)
            Text(subtitle, color = Color(0xFF9BA5A0), fontSize = 12.sp)
        }
        Text("›", color = Color(0xFF5F6F67), fontSize = 28.sp)
    }
}
