package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.background
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
fun AdminDashboardScreen() {

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
                    Text("WELCOME BACK", color = Color.Gray, fontSize = 12.sp)
                    Text(
                        "Admin User",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(cardColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Notifications, null, tint = Color.White)
                }
            }
        }

        /* ================= STATISTIK HARI INI ================= */
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
                title = "Total Ruangan",
                value = "120",
                subtitle = "Keseluruhan",
                icon = Icons.Default.Home,
                accent = green,
                cardColor = cardColor
            )
        }

        item {
            DashboardCard(
                title = "Ruangan Bersih",
                value = "85",
                subtitle = "Siap digunakan",
                icon = Icons.Default.CheckCircle,
                accent = green,
                cardColor = cardColor
            )
        }

        item {
            DashboardCard(
                title = "Belum Dibersihkan",
                value = "35",
                subtitle = "Perlu tindakan",
                icon = Icons.Default.Warning,
                accent = red,
                cardColor = cardColor
            )
        }

        item {
            DashboardCard(
                title = "Laporan Hari Ini",
                value = "5",
                subtitle = "Masuk hari ini",
                icon = Icons.Default.Description,
                accent = yellow,
                cardColor = cardColor
            )
        }

        /* ================= QUICK ACTIONS ================= */
        item {
            Text(
                text = "Quick Actions",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionCard(
                    title = "Manage Rooms",
                    subtitle = "Edit availability",
                    icon = Icons.Default.MeetingRoom,
                    cardColor = cardColor,
                    modifier = Modifier.weight(1f)
                )
                QuickActionCard(
                    title = "View Reports",
                    subtitle = "5 active issues",
                    icon = Icons.AutoMirrored.Filled.List,
                    cardColor = cardColor,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        /* ================= RECENT ACTIVITY ================= */
        item {
            Text(
                text = "Recent Activity",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            ActivityCard(
                title = "Room 304 - AC Malfunction",
                subtitle = "Reported by Housekeeping",
                time = "2m ago",
                accent = red,
                cardColor = cardColor
            )
        }

        item {
            ActivityCard(
                title = "Lobby - Light Replacement",
                subtitle = "Maintenance scheduled",
                time = "1h ago",
                accent = yellow,
                cardColor = cardColor
            )
        }

        item { Spacer(Modifier.height(32.dp)) }
    }
}

/* ================= COMPONENTS ================= */

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
