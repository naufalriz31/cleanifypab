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
    val bg = Color(0xFF0F2A1D)
    val card = Color(0xFF163828)
    val border = Color(0xFF245C3A)
    val green = Color(0xFF2DFF8F)

    var selected by remember { mutableStateOf("All") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(16.dp)
    ) {

        /* ===== HEADER ===== */
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.clickable { onBack() }
            )

            Spacer(Modifier.weight(1f))

            Text(
                "Notifications",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.weight(1f))

            Text(
                "Mark all read",
                color = green,
                fontSize = 13.sp
            )
        }

        Spacer(Modifier.height(16.dp))

        /* ===== FILTER ===== */
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NotificationChip("All", selected, green) { selected = "All" }
            NotificationChip("Reports", selected, green) { selected = "Reports" }
            NotificationChip("Tasks", selected, green) { selected = "Tasks" }
            NotificationChip("Rooms", selected, green) { selected = "Rooms" }
        }

        Spacer(Modifier.height(20.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {

            item { SectionTitle("NEW") }

            item {
                NotificationCard(
                    title = "Plumbing issue reported",
                    subtitle = "Room 102 – Maintenance reported a leak in the bathroom sink.",
                    time = "2m ago",
                    icon = Icons.Default.Warning,
                    accent = green,
                    card = card,
                    border = border,
                    showActions = true,
                    onAssign = onAssignTask
                )
            }

            item {
                NotificationCard(
                    title = "Cleaning Completed",
                    subtitle = "Room 205 – Jane Doe finished the cleaning task.",
                    time = "1h ago",
                    icon = Icons.Default.CheckCircle,
                    accent = green,
                    card = card,
                    border = border
                )
            }

            item { SectionTitle("EARLIER") }

            item {
                NotificationCard(
                    title = "Room Status Update",
                    subtitle = "Room 401 marked as Vacant/Dirty.",
                    time = "3h ago",
                    icon = Icons.Default.Sync,
                    accent = green,
                    card = card,
                    border = border
                )
            }

            item {
                NotificationCard(
                    title = "System Maintenance",
                    subtitle = "Database maintenance scheduled at 02:00 AM.",
                    time = "5h ago",
                    icon = Icons.Default.Settings,
                    accent = green,
                    card = card,
                    border = border
                )
            }
        }
    }
}

/* ================= COMPONENTS ================= */

@Composable
private fun NotificationChip(
    text: String,
    selected: String,
    accent: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                if (selected == text) accent else Color.Transparent,
                RoundedCornerShape(20.dp)
            )
            .border(1.dp, accent, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text,
            color = if (selected == text) Color.Black else Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        color = Color.Gray,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    )
}

@Composable
private fun NotificationCard(
    title: String,
    subtitle: String,
    time: String,
    icon: ImageVector,
    accent: Color,
    card: Color,
    border: Color,
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
                    .background(accent.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = accent)
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold)
                Text(subtitle, color = Color.Gray, fontSize = 12.sp)
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
                    Text("Assign Task", color = Color.Black, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = {},
                    border = BorderStroke(1.dp, accent),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("View Details", color = accent)
                }
            }
        }
    }
}
