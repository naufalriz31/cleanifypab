package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cleanfypab.data.model.TaskHistoryItem
import com.example.cleanfypab.viewmodel.TaskHistoryViewModel
import androidx.compose.foundation.BorderStroke

@Composable
fun ReportHistoryScreen(
    nav: NavHostController,
    vm: TaskHistoryViewModel = viewModel()
) {
    val ui by vm.state.collectAsState()

    /* ===== PALET ===== */
    val bgGradient = Brush.verticalGradient(listOf(Color(0xFFF6FBF8), Color(0xFFE9F5EE)))
    val card = Color.White
    val cardSoft = Color(0xFFF2F7F4)
    val borderSoft = Color(0xFFE0E0E0)

    val primaryGreen = Color(0xFF2ECC71)
    val orange = Color(0xFFE67E22)
    val blue = Color(0xFF4DA3FF)

    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    var selectedTab by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    // Tab sesuai status tasks
    val tabs = listOf("Semua", "Selesai", "Menunggu", "Dikerjakan")

    LaunchedEffect(Unit) { vm.load() }

    val filtered = ui.items.filter { item ->
        val cocokStatus = when (selectedTab) {
            1 -> item.status == "DONE"
            2 -> item.status == "ASSIGNED"
            3 -> item.status == "IN_PROGRESS"
            else -> true
        }
        cocokStatus && item.roomName.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .padding(16.dp)
    ) {

        Spacer(Modifier.height(10.dp))

        /* ===== HEADER ===== */
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = darkText)
            }
            Text(
                "Riwayat Task",
                color = darkText,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.weight(1f))
            Text(
                "Refresh",
                color = primaryGreen,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { vm.load() }
            )
        }

        Spacer(Modifier.height(16.dp))

        /* ===== SEARCH ===== */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(cardSoft, RoundedCornerShape(14.dp))
                .border(1.dp, borderSoft, RoundedCornerShape(14.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                tint = grayText,
                modifier = Modifier.padding(start = 12.dp)
            )

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Cari ruangan...", color = grayText) },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = primaryGreen,
                    focusedTextColor = darkText,
                    unfocusedTextColor = darkText
                ),
                singleLine = true
            )
        }

        Spacer(Modifier.height(14.dp))

        /* ===== TAB FILTER ===== */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            tabs.forEachIndexed { index, title ->
                val selected = selectedTab == index
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(if (selected) primaryGreen.copy(alpha = 0.15f) else card)
                        .border(1.dp, if (selected) primaryGreen else borderSoft, RoundedCornerShape(30.dp))
                        .clickable { selectedTab = index }
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Text(
                        title,
                        fontSize = 13.sp,
                        color = if (selected) primaryGreen else darkText,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        ui.error?.let { Text(it, color = Color.Red, fontSize = 13.sp) }

        if (ui.loading) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
            Spacer(Modifier.height(10.dp))
        }

        if (!ui.loading && filtered.isEmpty()) {
            Text("Belum ada task.", color = grayText)
            return@Column
        }

        /* ===== LIST ===== */
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(filtered, key = { it.id }) { item ->
                TaskHistoryCard(
                    item = item,
                    primaryGreen = primaryGreen,
                    orange = orange,
                    blue = blue,
                    darkText = darkText,
                    grayText = grayText,
                    borderSoft = borderSoft,
                    onEdit = {
                        // ✅ menuju EditReportScreen untuk ubah status
                        nav.navigate("edit_report/${item.id}")
                    }
                )
            }
            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun TaskHistoryCard(
    item: TaskHistoryItem,
    primaryGreen: Color,
    orange: Color,
    blue: Color,
    darkText: Color,
    grayText: Color,
    borderSoft: Color,
    onEdit: () -> Unit
) {
    val (statusLabel, statusColor) = when (item.status) {
        "DONE" -> "Selesai" to primaryGreen
        "IN_PROGRESS" -> "Dikerjakan" to blue
        else -> "Menunggu" to orange
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .border(1.dp, borderSoft, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        item.roomName.ifBlank { "Ruangan" },
                        color = darkText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        item.timeLabel,
                        color = grayText,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(statusColor.copy(alpha = 0.15f))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(statusLabel, color = statusColor, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }

            if (item.note.isNotBlank()) {
                Spacer(Modifier.height(10.dp))
                Text(
                    item.note,
                    color = grayText,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(Modifier.height(12.dp))

            // ✅ tombol ubah status
            OutlinedButton(
                onClick = onEdit,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, borderSoft),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = darkText)
            ) {
                Icon(Icons.Default.Edit, contentDescription = null, tint = darkText)
                Spacer(Modifier.width(8.dp))
                Text("Ubah Status / Isi Laporan", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
