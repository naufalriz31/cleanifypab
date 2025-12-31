package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cleanfypab.viewmodel.RoomViewModel
import com.example.cleanfypab.ui.components.TaskItem

@Composable
fun TaskTodayScreen(
    nav: NavHostController,
    vm: RoomViewModel
) {

    /* ===== PALET WARNA (SESUAI LOGIN & HOME) ===== */
    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF6FBF8),
            Color(0xFFE9F5EE)
        )
    )

    val cardSoft = Color(0xFFF2F7F4)
    val borderSoft = Color(0xFFE0E0E0)

    val primaryGreen = Color(0xFF2ECC71)
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    var selectedTab by remember { mutableStateOf(1) } // 0=Semua, 1=Belum, 2=Selesai
    val rooms = vm.roomList.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .background(bgGradient)
            .padding(16.dp)
    ) {

        /* ================= HEADER ================= */
        Text(
            "Tugas Hari Ini",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = darkText
        )
        Text(
            "Selasa, 21 Mei 2024",
            color = grayText,
            fontSize = 13.sp
        )

        Spacer(Modifier.height(16.dp))

        /* ================= TAB ================= */
        Row(
            Modifier
                .fillMaxWidth()
                .background(cardSoft, RoundedCornerShape(30.dp))
                .border(1.dp, borderSoft, RoundedCornerShape(30.dp))
                .padding(6.dp)
        ) {

            TaskTabLight(
                text = "Semua",
                active = selectedTab == 0,
                accent = primaryGreen,
                modifier = Modifier.weight(1f)
            ) { selectedTab = 0 }

            TaskTabLight(
                text = "Belum Selesai",
                active = selectedTab == 1,
                accent = primaryGreen,
                modifier = Modifier.weight(1f)
            ) { selectedTab = 1 }

            TaskTabLight(
                text = "Selesai",
                active = selectedTab == 2,
                accent = primaryGreen,
                modifier = Modifier.weight(1f)
            ) { selectedTab = 2 }
        }

        Spacer(Modifier.height(16.dp))

        /* ================= FILTER ================= */
        val filtered = when (selectedTab) {
            0 -> rooms.value
            1 -> rooms.value.filter { it.status != "Selesai" }
            else -> rooms.value.filter { it.status == "Selesai" }
        }

        /* ================= LIST ================= */
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(filtered) { room ->
                TaskItem(
                    room = room,
                    onScanClick = {
                        nav.navigate("detail/${room.id}")
                    }
                )
            }
        }
    }
}

/* ==================================================
                    TASK TAB (LIGHT)
================================================== */

@Composable
fun TaskTabLight(
    text: String,
    active: Boolean,
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                if (active) accent.copy(alpha = 0.15f) else Color.Transparent,
                RoundedCornerShape(20.dp)
            )
            .border(
                1.dp,
                if (active) accent else Color.Transparent,
                RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            color = if (active) accent else Color(0xFF1E2D28),
            fontWeight = if (active) FontWeight.Bold else FontWeight.Normal,
            fontSize = 13.sp
        )
    }
}
