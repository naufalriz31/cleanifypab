package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cleanfypab.ui.navigation.AdminRoutes
import com.example.cleanfypab.viewmodel.AdminDashboardViewModel



@Composable
fun AdminDashboardScreen(
    onNotificationClick: () -> Unit,
    onNavigate: (route: String) -> Unit,
    vm: AdminDashboardViewModel = viewModel()
) {
    val state by vm.state.collectAsState()

    /* ===== CLEANIFY LIGHT ===== */
    val bgGradient = Brush.verticalGradient(listOf(Color(0xFFF6FBF8), Color(0xFFE9F5EE)))
    val card = Color.White
    val borderSoft = Color(0xFFE0E0E0)

    val green = Color(0xFF2ECC71)
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    LaunchedEffect(Unit) { vm.loadSummary() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        /* ================= HEADER ================= */
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Dashboard Admin",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkText
                )
                Text(
                    "Ringkasan Cleanify hari ini",
                    fontSize = 13.sp,
                    color = grayText
                )
            }

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(card)
                    .border(1.dp, borderSoft, CircleShape)
                    .clickable { onNotificationClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Notifications, contentDescription = "Notifikasi", tint = darkText)
            }
        }

        /* ================= SUMMARY CARDS ================= */
        Card(
            colors = CardDefaults.cardColors(containerColor = card),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, borderSoft, RoundedCornerShape(20.dp))
        ) {
            Column(Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Ringkasan", color = darkText, fontWeight = FontWeight.Bold)
                    Text(
                        if (state.loading) "Memuat..." else "Refresh",
                        color = green,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { vm.loadSummary() }
                    )
                }

                state.error?.let {
                    Spacer(Modifier.height(8.dp))
                    Text(it, color = Color.Red, fontSize = 13.sp)
                }

                Spacer(Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SummaryChip(title = "Ruangan", value = state.totalRooms.toString(), accent = green)
                    SummaryChip(title = "Petugas Aktif", value = state.activePetugas.toString(), accent = green)
                    SummaryChip(title = "Task Hari Ini", value = state.tasksToday.toString(), accent = green)
                }
            }
        }

        /* ================= QUICK ACTIONS ================= */
        Text("Menu Cepat", color = darkText, fontWeight = FontWeight.Bold)

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickMenuItem(
                icon = Icons.Default.MeetingRoom,
                title = "Manajemen Ruangan",
                subtitle = "Tambah / hapus ruangan & QR",
                onClick = { onNavigate(AdminRoutes.ROOMS) },
                borderSoft = borderSoft
            )

            QuickMenuItem(
                icon = Icons.Default.Group,
                title = "Manajemen Petugas",
                subtitle = "Assign task ke petugas",
                onClick = { onNavigate(AdminRoutes.PETUGAS) },
                borderSoft = borderSoft
            )

            QuickMenuItem(
                icon = Icons.Default.Assignment,
                title = "Buat Task",
                subtitle = "Assign ruangan ke petugas",
                onClick = { onNavigate(AdminRoutes.CREATE_TASK) },
                borderSoft = borderSoft
            )

            QuickMenuItem(
                icon = Icons.Default.Person,
                title = "Daftar User",
                subtitle = "Lihat data user di sistem",
                onClick = { onNavigate(AdminRoutes.USERS) },
                borderSoft = borderSoft
            )
        }

        Spacer(Modifier.height(6.dp))

        /* ================= TIP ================= */
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F7F4)),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Tips Admin", fontWeight = FontWeight.Bold, color = darkText)
                Spacer(Modifier.height(6.dp))
                Text(
                    "Mulai dari tambah ruangan → assign task ke petugas → pantau laporan dari petugas.",
                    color = grayText,
                    fontSize = 13.sp
                )
            }
        }
    }
}

/* ================= KOMPONEN ================= */

@Composable
private fun SummaryChip(title: String, value: String, accent: Color) {
    Column(
        modifier = Modifier
            .background(Color(0xFFF2F7F4), RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(16.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(title, fontSize = 12.sp, color = Color(0xFF6B7C75))
        Spacer(Modifier.height(6.dp))
        Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E2D28))
    }
}

@Composable
private fun QuickMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    borderSoft: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(18.dp))
            .border(1.dp, borderSoft, RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFFF2F7F4)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Color(0xFF1E2D28))
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, color = Color(0xFF1E2D28))
            Text(subtitle, fontSize = 12.sp, color = Color(0xFF6B7C75))
        }

        Text("›", fontSize = 26.sp, color = Color(0xFF6B7C75))
    }
}
