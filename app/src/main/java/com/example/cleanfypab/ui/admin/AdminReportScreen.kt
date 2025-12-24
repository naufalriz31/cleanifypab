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

    var selectedFilter by remember { mutableStateOf("All") }

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
                Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                Text(
                    "Report Management",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(Icons.Default.Download, null, tint = Color.White)
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
                    Icon(Icons.Default.Search, null, tint = gray)
                    Spacer(Modifier.width(8.dp))
                    Text("Search by room or issue...", color = gray)
                }
            }

            Spacer(Modifier.height(16.dp))

            /* ===== FILTER ===== */
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                FilterChip("All", selectedFilter == "All", green) { selectedFilter = "All" }
                FilterChip("New", selectedFilter == "New", green, dot = true) { selectedFilter = "New" }
                FilterChip("Assigned", selectedFilter == "Assigned", green) { selectedFilter = "Assigned" }
                FilterChip("Resolved", selectedFilter == "Resolved", green) { selectedFilter = "Resolved" }
            }

            Spacer(Modifier.height(20.dp))

            /* ===== LIST ===== */
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                item {
                    ReportItem(
                        title = "Room 304 - AC Malfunction",
                        desc = "The AC unit is making a loud rattling noise and not cooling...",
                        status = "CRITICAL",
                        statusColor = red,
                        time = "2h ago",
                        user = "John Doe",
                        actionText = "Assign >",
                        onActionClick = onAssignClick
                    )
                }

                item {
                    ReportItem(
                        title = "Lobby - Spill",
                        desc = "Large coffee spill near the main entrance...",
                        status = "NEW",
                        statusColor = green,
                        time = "4h ago",
                        user = "Sarah Smith",
                        actionText = "Details >",
                        onActionClick = {}
                    )
                }

                item {
                    ReportItem(
                        title = "Corridor 2F - Flickering Light",
                        desc = "Light fixture flickering constantly. Parts ordered.",
                        status = "IN PROGRESS",
                        statusColor = yellow,
                        time = "Updated 10m ago",
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
            Icon(Icons.Default.Add, null, tint = Color.Black)
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
