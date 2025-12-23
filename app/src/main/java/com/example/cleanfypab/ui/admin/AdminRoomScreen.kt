package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminRoomScreen(
    onAddRoom: () -> Unit = {}
) {

    val bgColor = Color(0xFF0F2A1D)
    val cardColor = Color(0xFF163828)
    val green = Color(0xFF2DFF8F)
    val red = Color(0xFFFF5C5C)
    val yellow = Color(0xFFFFC107)

    var selectedFilter by remember { mutableStateOf("ALL") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(16.dp)
    ) {

        /* HEADER */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Room Management",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            FloatingActionButton(
                onClick = onAddRoom,
                containerColor = green,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Add, null, tint = Color.Black)
            }
        }

        Spacer(Modifier.height(16.dp))

        /* SEARCH */
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search room number or type...") },
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

        /* OVERVIEW */
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OverviewCard("45", "Total Rooms", green, cardColor)
            OverviewCard("12", "Available Now", green, cardColor)
        }

        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OverviewCard("28", "Occupied", red, cardColor)
            OverviewCard("5", "Maintenance", yellow, cardColor)
        }

        Spacer(Modifier.height(20.dp))

        /* FILTER */
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            RoomFilter("ALL", "All", selectedFilter, green) { selectedFilter = "ALL" }
            RoomFilter("AVAILABLE", "Available", selectedFilter, green) { selectedFilter = "AVAILABLE" }
            RoomFilter("OCCUPIED", "Occupied", selectedFilter, green) { selectedFilter = "OCCUPIED" }
            RoomFilter("MAINT", "Maintenance", selectedFilter, green) { selectedFilter = "MAINT" }
        }

        Spacer(Modifier.height(16.dp))

        /* ROOM LIST */
        LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            item { RoomItem("Room 101", "Deluxe Suite", "Available", green, cardColor) }
            item { RoomItem("Room 102", "Standard Single", "Occupied", red, cardColor) }
            item { RoomItem("Room 103", "Conference A", "Maintenance", yellow, cardColor) }
            item { RoomItem("Room 204", "Double Twin", "Occupied", red, cardColor) }
            item { RoomItem("Room 205", "Superior Queen", "Available", green, cardColor) }
        }
    }
}

/* ================= COMPONENTS ================= */

@Composable
fun OverviewCard(
    value: String,
    label: String,
    accent: Color,
    cardColor: Color
) {
    Column(
        modifier = Modifier
            .background(cardColor, RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Text(value, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text(label, color = accent, fontSize = 13.sp)
    }
}

@Composable
fun RoomFilter(
    filter: String,
    label: String,
    selected: String,
    accent: Color,
    onClick: () -> Unit
) {
    Surface(
        color = if (selected == filter) accent else Color.Transparent,
        shape = RoundedCornerShape(50),
        border = if (selected != filter) BorderStroke(1.dp, accent) else null,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (selected == filter) Color.Black else accent,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
fun RoomItem(
    room: String,
    type: String,
    status: String,
    statusColor: Color,
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
            Text(room, color = Color.White, fontWeight = FontWeight.Bold)
            Text(type, color = Color.Gray, fontSize = 13.sp)
        }

        Surface(
            color = statusColor.copy(alpha = 0.2f),
            shape = RoundedCornerShape(50)
        ) {
            Text(
                status,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                color = statusColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
