package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminRoomScreen(
    onAddTask: () -> Unit = {}
) {

    /* ===== WARNA CLEANIFY ===== */
    val bgGradient = Brush.verticalGradient(
        listOf(
            Color(0xFFF6FBF8),
            Color(0xFFE9F5EE)
        )
    )

    val cardColor = Color.White
    val borderSoft = Color(0xFFE0E0E0)

    val green = Color(0xFF2ECC71)
    val red = Color(0xFFFF6B6B)
    val yellow = Color(0xFFFFC107)

    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    var selectedFilter by remember { mutableStateOf("ALL") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .padding(16.dp)
    ) {

        /* ================= HEADER ================= */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Manajemen Ruangan",
                color = darkText,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            FloatingActionButton(
                onClick = onAddTask,
                containerColor = green,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Add, null, tint = Color.White)
            }
        }

        Spacer(Modifier.height(16.dp))

        /* ================= SEARCH ================= */
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Cari nomor atau tipe ruangan...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = cardColor,
                unfocusedContainerColor = cardColor,
                focusedBorderColor = green,
                unfocusedBorderColor = borderSoft,
                cursorColor = green
            )
        )

        Spacer(Modifier.height(20.dp))

        /* ================= OVERVIEW ================= */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OverviewCardLight(
                modifier = Modifier.weight(1f),
                value = "30",
                label = "Total Ruangan",
                icon = Icons.Default.Apartment,
                accent = green,
                card = cardColor,
                darkText = darkText
            )
            OverviewCardLight(
                modifier = Modifier.weight(1f),
                value = "12",
                label = "Tersedia",
                icon = Icons.Default.CheckCircle,
                accent = green,
                card = cardColor,
                darkText = darkText
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OverviewCardLight(
                modifier = Modifier.weight(1f),
                value = "13",
                label = "Terpakai",
                icon = Icons.Default.Person,
                accent = red,
                card = cardColor,
                darkText = darkText
            )
            OverviewCardLight(
                modifier = Modifier.weight(1f),
                value = "5",
                label = "Perawatan",
                icon = Icons.Default.Build,
                accent = yellow,
                card = cardColor,
                darkText = darkText
            )
        }

        Spacer(Modifier.height(20.dp))

        /* ================= FILTER ================= */
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            RoomFilterLight("ALL", "Semua", selectedFilter, green) { selectedFilter = "ALL" }
            RoomFilterLight("AVAILABLE", "Tersedia", selectedFilter, green) { selectedFilter = "AVAILABLE" }
            RoomFilterLight("OCCUPIED", "Terpakai", selectedFilter, green) { selectedFilter = "OCCUPIED" }
            RoomFilterLight("MAINT", "Perawatan", selectedFilter, green) { selectedFilter = "MAINT" }
        }

        Spacer(Modifier.height(16.dp))

        /* ================= ROOM LIST ================= */
        LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {

            item { RoomItemLight("Ruang 101", "Standar Single", "Tersedia", green, cardColor, darkText, grayText) }
            item { RoomItemLight("Ruang 102", "Standar Single", "Terpakai", red, cardColor, darkText, grayText) }
            item { RoomItemLight("Ruang 105", "Suite Deluxe", "Perawatan", yellow, cardColor, darkText, grayText) }
            item { RoomItemLight("Ruang 205", "Superior Queen", "Tersedia", green, cardColor, darkText, grayText) }
            item { RoomItemLight("Ruang 304", "Ruang Eksekutif", "Terpakai", red, cardColor, darkText, grayText) }
            item { RoomItemLight("Ruang 401", "Ruang Rapat", "Tersedia", green, cardColor, darkText, grayText) }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

/* ================= KOMPONEN ================= */

@Composable
fun OverviewCardLight(
    modifier: Modifier,
    value: String,
    label: String,
    icon: ImageVector,
    accent: Color,
    card: Color,
    darkText: Color
) {
    Row(
        modifier = modifier
            .background(card, RoundedCornerShape(18.dp))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(18.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(value, color = darkText, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(label, color = accent, fontSize = 13.sp)
        }
        Icon(icon, null, tint = accent)
    }
}

@Composable
fun RoomFilterLight(
    filter: String,
    label: String,
    selected: String,
    accent: Color,
    onClick: () -> Unit
) {
    Surface(
        color = if (selected == filter) accent.copy(alpha = 0.15f) else Color.Transparent,
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, accent),
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (selected == filter) accent else Color(0xFF1E2D28),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
fun RoomItemLight(
    room: String,
    type: String,
    status: String,
    statusColor: Color,
    card: Color,
    darkText: Color,
    grayText: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(card, RoundedCornerShape(20.dp))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(20.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {
            Text(room, color = darkText, fontWeight = FontWeight.Bold)
            Text(type, color = grayText, fontSize = 13.sp)
        }

        Surface(
            color = statusColor.copy(alpha = 0.15f),
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
