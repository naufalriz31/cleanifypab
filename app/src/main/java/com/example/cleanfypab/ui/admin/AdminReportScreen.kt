package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminReportScreen(
    onAssignClick: () -> Unit
) {

    /* ===== COLORS ===== */
    val bg = Color(0xFF0F2A1D)
    val card = Color(0xFF163828)
    val green = Color(0xFF2DFF8F)
    val red = Color(0xFFFF6B6B)
    val yellow = Color(0xFFFFC107)
    val gray = Color(0xFF9E9E9E)

    var selectedFilter by remember { mutableStateOf("Semua") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            /* ===== HEADER ===== */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Kembali", tint = Color.White)
                Text(
                    "Manajemen Laporan",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(Icons.Default.Download, contentDescription = "Unduh", tint = Color.White)
            }

            Spacer(Modifier.height(16.dp))

            /* ===== SEARCH ===== */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(card, RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Search, contentDescription = "Cari", tint = gray)
                    Spacer(Modifier.width(8.dp))
                    Text("Cari berdasarkan ruangan atau masalah...", color = gray)
                }
            }

            Spacer(Modifier.height(16.dp))

            /* ===== FILTER ===== */
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                FilterChip("Semua", selectedFilter == "Semua", green) { selectedFilter = "Semua" }
                FilterChip("Baru", selectedFilter == "Baru", green, dot = true) { selectedFilter = "Baru" }
                FilterChip("Ditugaskan", selectedFilter == "Ditugaskan", green) { selectedFilter = "Ditugaskan" }
                FilterChip("Selesai", selectedFilter == "Selesai", green) { selectedFilter = "Selesai" }
            }

            Spacer(Modifier.height(20.dp))

            /* ===== LIST ===== */
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                item {
                    ReportItem(
                        title = "Ruang 304 - AC Bermasalah",
                        desc = "Unit AC mengeluarkan suara keras dan tidak mendinginkan ruangan...",
                        status = "KRITIS",
                        statusColor = red,
                        time = "2 jam lalu",
                        user = "John Doe",
                        actionText = "Tetapkan >",
                        onActionClick = onAssignClick
                    )
                }

                item {
                    ReportItem(
                        title = "Lobi - Tumpahan",
                        desc = "Tumpahan kopi besar di dekat pintu masuk utama...",
                        status = "BARU",
                        statusColor = green,
                        time = "4 jam lalu",
                        user = "Sarah Smith",
                        actionText = "Detail >",
                        onActionClick = {}
                    )
                }

                item {
                    ReportItem(
                        title = "Koridor Lt. 2 - Lampu Berkedip",
                        desc = "Lampu berkedip terus-menerus. Suku cadang telah dipesan.",
                        status = "DALAM PROSES",
                        statusColor = yellow,
                        time = "Diperbarui 10 menit lalu",
                        user = "Mike R.",
                        actionText = "",
                        onActionClick = {}
                    )
                }
            }
        }

        /* ===== FAB ===== */
        FloatingActionButton(
            onClick = {},
            containerColor = green,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Tambah", tint = Color.Black)
        }
    }
}

/* ================= COMPONENTS ================= */

@Composable
fun FilterChip(
    text: String,
    selected: Boolean,
    accent: Color,
    dot: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(
                if (selected) accent else Color(0xFF1C3A2A),
                RoundedCornerShape(50)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text,
            color = if (selected) Color.Black else Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
        if (dot) {
            Spacer(Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(accent, CircleShape)
            )
        }
    }
}

@Composable
fun ReportItem(
    title: String,
    desc: String,
    status: String,
    statusColor: Color,
    time: String,
    user: String,
    actionText: String,
    onActionClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF163828), RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(title, color = Color.White, fontWeight = FontWeight.Bold)
            Box(
                modifier = Modifier
                    .background(statusColor.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(status, color = statusColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(Modifier.height(6.dp))

        Text(
            desc,
            color = Color.Gray,
            fontSize = 13.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text("$time • $user", color = Color.Gray, fontSize = 12.sp)
            if (actionText.isNotEmpty()) {
                Text(
                    actionText,
                    color = Color(0xFF2DFF8F),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onActionClick() }
                )
            }
        }
    }
}
