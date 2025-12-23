package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminReportScreen(
    onBack: () -> Unit = {}
) {

    val bgColor = Color(0xFF0F2A1D)
    val cardColor = Color(0xFF163828)
    val green = Color(0xFF2DFF8F)
    val red = Color(0xFFFF5C5C)
    val yellow = Color(0xFFFFC107)

    var selectedTab by remember { mutableStateOf("ALL") }

    /* ===== FEEDBACK STATE ===== */
    var showFeedbackDialog by remember { mutableStateOf(false) }
    var feedbackText by remember { mutableStateOf("") }
    var selectedReport by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(16.dp)
    ) {

        /* ================= HEADER ================= */
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Text(
                text = "Report Management",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Icon(Icons.Default.Download, contentDescription = null, tint = Color.White)
        }

        Spacer(Modifier.height(16.dp))

        /* ================= SEARCH ================= */
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search by room or issue...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = cardColor,
                unfocusedContainerColor = cardColor,
                focusedBorderColor = green,
                unfocusedBorderColor = cardColor,
                cursorColor = green
            )
        )

        Spacer(Modifier.height(16.dp))

        /* ================= FILTER ================= */
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            ReportFilter("ALL", "All", selectedTab, green) { selectedTab = "ALL" }
            ReportFilter("NEW", "New", selectedTab, green) { selectedTab = "NEW" }
            ReportFilter("ASSIGNED", "Assigned", selectedTab, green) { selectedTab = "ASSIGNED" }
            ReportFilter("RESOLVED", "Resolved", selectedTab, green) { selectedTab = "RESOLVED" }
        }

        Spacer(Modifier.height(16.dp))

        /* ================= LIST ================= */
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {

            item {
                ReportCard(
                    title = "Room 304 - AC Malfunction",
                    description = "The AC unit is making a loud rattling noise and not cooling.",
                    status = "CRITICAL",
                    statusColor = red,
                    time = "2h ago",
                    user = "John Doe",
                    action = "Feedback",
                    cardColor = cardColor,
                    onActionClick = {
                        selectedReport = "Room 304 - AC Malfunction"
                        showFeedbackDialog = true
                    }
                )
            }

            item {
                ReportCard(
                    title = "Lobby - Spill",
                    description = "Large coffee spill near the main entrance.",
                    status = "NEW",
                    statusColor = green,
                    time = "4h ago",
                    user = "Sarah Smith",
                    action = "Feedback",
                    cardColor = cardColor,
                    onActionClick = {
                        selectedReport = "Lobby - Spill"
                        showFeedbackDialog = true
                    }
                )
            }

            item {
                ReportCard(
                    title = "Corridor 2F - Flickering Light",
                    description = "Light fixture flickering constantly.",
                    status = "IN PROGRESS",
                    statusColor = yellow,
                    time = "Updated 10m ago",
                    user = "Mike R.",
                    action = "",
                    cardColor = cardColor,
                    onActionClick = {}
                )
            }
        }
    }

    /* ================= FEEDBACK DIALOG ================= */
    if (showFeedbackDialog) {
        AlertDialog(
            onDismissRequest = { showFeedbackDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showFeedbackDialog = false
                    feedbackText = ""
                }) {
                    Text("Kirim")
                }
            },
            dismissButton = {
                TextButton(onClick = { showFeedbackDialog = false }) {
                    Text("Batal")
                }
            },
            title = { Text("Feedback ke Petugas") },
            text = {
                Column {
                    Text(
                        text = selectedReport,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = feedbackText,
                        onValueChange = { feedbackText = it },
                        placeholder = { Text("Masukkan feedback admin...") }
                    )
                }
            }
        )
    }
}

/* ================= COMPONENTS ================= */

@Composable
fun ReportFilter(
    key: String,
    label: String,
    selected: String,
    accent: Color,
    onClick: () -> Unit
) {
    Surface(
        color = if (selected == key) accent else Color.Transparent,
        shape = RoundedCornerShape(50),
        border = if (selected != key) BorderStroke(1.dp, accent) else null,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (selected == key) Color.Black else accent,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
fun ReportCard(
    title: String,
    description: String,
    status: String,
    statusColor: Color,
    time: String,
    user: String,
    action: String,
    cardColor: Color,
    onActionClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(cardColor, RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(title, color = Color.White, fontWeight = FontWeight.Bold)
            Surface(
                color = statusColor.copy(alpha = 0.2f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    status,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = statusColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(Modifier.height(6.dp))

        Text(
            description,
            color = Color.Gray,
            fontSize = 13.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("$time • $user", color = Color.Gray, fontSize = 12.sp)
            if (action.isNotEmpty()) {
                Text(
                    action,
                    color = Color(0xFF2DFF8F),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onActionClick() }
                )
            }
        }
    }
}
