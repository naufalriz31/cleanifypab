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
import androidx.compose.material.icons.filled.FilterList
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
import androidx.navigation.NavHostController
import com.example.cleanfypab.data.model.RoomModel

@Composable
fun ReportHistoryScreen(
    nav: NavHostController,
    rooms: List<RoomModel>
) {

    /* ===== PALET WARNA (SESUAI LOGIN / HOME) ===== */
    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF6FBF8),
            Color(0xFFE9F5EE)
        )
    )

    val card = Color.White
    val cardSoft = Color(0xFFF2F7F4)
    val borderSoft = Color(0xFFE0E0E0)

    val primaryGreen = Color(0xFF2ECC71)
    val orange = Color(0xFFE67E22)
    val red = Color(0xFFE74C3C)

    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    var selectedTab by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    val tabs = listOf("Semua", "Selesai", "Menunggu", "Perlu Dicek")

    val filteredRooms = rooms.filter { room ->
        val cocokStatus = when (selectedTab) {
            1 -> room.status == "Selesai"
            2 -> room.status == "Menunggu"
            3 -> room.status == "Perlu Dicek"
            else -> true
        }
        cocokStatus && room.name.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .padding(16.dp)
    ) {

        Spacer(Modifier.height(12.dp))

        /* ===== HEADER ===== */
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    tint = darkText
                )
            }
            Text(
                "Riwayat Laporan",
                color = darkText,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(20.dp))

        /* ===== PENCARIAN ===== */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(cardSoft, RoundedCornerShape(14.dp))
                .border(1.dp, borderSoft, RoundedCornerShape(14.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text("Cari ruangan...", color = grayText)
                },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = primaryGreen,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = darkText,
                    unfocusedTextColor = darkText
                ),
                singleLine = true
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(card)
                    .border(1.dp, borderSoft, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.FilterList,
                    contentDescription = "Filter",
                    tint = darkText
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        /* ===== TAB FILTER ===== */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            tabs.forEachIndexed { index, title ->
                val selected = selectedTab == index
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(
                            if (selected) primaryGreen.copy(alpha = 0.15f) else card
                        )
                        .border(
                            1.dp,
                            if (selected) primaryGreen else borderSoft,
                            RoundedCornerShape(30.dp)
                        )
                        .clickable { selectedTab = index }
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        title,
                        fontSize = 14.sp,
                        color = if (selected) primaryGreen else darkText,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        /* ===== DAFTAR LAPORAN ===== */
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(filteredRooms) { room ->
                ReportHistoryItemLight(room) {
                    nav.navigate("detail/${room.id}")
                }
            }
        }
    }
}

/* ================= ITEM ================= */

@Composable
private fun ReportHistoryItemLight(
    room: RoomModel,
    onClick: () -> Unit
) {

    val statusColor = when (room.status) {
        "Selesai" -> Color(0xFF2ECC71)
        "Menunggu" -> Color(0xFFE67E22)
        "Perlu Dicek" -> Color(0xFFE74C3C)
        else -> Color.Gray
    }

    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)
    val borderSoft = Color(0xFFE0E0E0)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .border(1.dp, borderSoft, RoundedCornerShape(16.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF2F7F4)),
                contentAlignment = Alignment.Center
            ) {
                Text("▣", color = darkText, fontSize = 22.sp)
            }

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    room.name,
                    color = darkText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    room.time,
                    color = grayText,
                    fontSize = 13.sp,
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
                Text(
                    room.status,
                    color = statusColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.width(6.dp))
            Text("›", color = grayText, fontSize = 26.sp)
        }
    }
}
