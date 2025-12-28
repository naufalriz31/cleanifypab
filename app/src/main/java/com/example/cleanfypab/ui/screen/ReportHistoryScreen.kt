package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.background
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

    val bg = Color(0xFF0D1F15)
    val card = Color(0xFF14231C)
    val cardSoft = Color(0xFF1F2C25)
    val green = Color(0xFF00E676)
    val textSecondary = Color(0xFF9BA5A0)

    var selectedTab by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    val tabs = listOf("Semua", "Selesai", "Menunggu", "Perlu Dicek")

    val filteredRooms = rooms.filter { room ->
        val matchStatus = when (selectedTab) {
            1 -> room.status == "Selesai"
            2 -> room.status == "Menunggu"
            3 -> room.status == "Perlu Dicek"
            else -> true
        }
        matchStatus && room.name.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(16.dp)
    ) {

        Spacer(Modifier.height(12.dp))

        /* ===== HEADER ===== */
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                "Report History",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(20.dp))

        /* ===== SEARCH ===== */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(cardSoft, RoundedCornerShape(14.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text("Cari ruangan...", color = textSecondary)
                },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(card),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.FilterList, null, tint = Color.White)
            }
        }

        Spacer(Modifier.height(20.dp))

        /* ===== TABS ===== */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            tabs.forEachIndexed { index, title ->
                val selected = selectedTab == index
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(if (selected) green else cardSoft)
                        .clickable { selectedTab = index }
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        title,
                        fontSize = 14.sp,
                        color = if (selected) Color.Black else Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        /* ===== LIST ===== */
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(filteredRooms) { room ->
                ReportHistoryItem(room) {
                    nav.navigate("detail/${room.id}")
                }
            }
        }
    }
}

@Composable
private fun ReportHistoryItem(
    room: RoomModel,
    onClick: () -> Unit
) {

    val statusColor = when (room.status) {
        "Selesai" -> Color(0xFF2ECC71)
        "Menunggu" -> Color(0xFFE67E22)
        "Perlu Dicek" -> Color(0xFFE74C3C)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF14231C)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF1F2C25)),
                contentAlignment = Alignment.Center
            ) {
                Text("▣", color = Color.White, fontSize = 22.sp)
            }

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    room.name,
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    room.time,
                    color = Color(0xFF9BA5A0),
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(statusColor.copy(alpha = 0.2f))
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
            Text("›", color = Color(0xFF5F6F67), fontSize = 26.sp)
        }
    }
}
