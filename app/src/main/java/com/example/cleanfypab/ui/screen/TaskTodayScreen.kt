package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    val bg = Color(0xFF0D1F15)
    val card = Color(0xFF14231C)
    val cardAlt = Color(0xFF1F2C25)
    val green = Color(0xFF00E676)
    val muted = Color(0xFF9BA5A0)

    var selectedTab by remember { mutableStateOf(1) } // 0=Semua, 1=Belum, 2=Selesai
    val rooms = vm.roomList.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .background(bg)
            .padding(16.dp)
    ) {

        /* ================= HEADER ================= */
        Text(
            "Tugas Hari Ini",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            "Selasa, 21 Mei 2024",
            color = muted,
            fontSize = 13.sp
        )

        Spacer(Modifier.height(16.dp))

        /* ================= TAB ================= */
        Row(
            Modifier
                .fillMaxWidth()
                .background(cardAlt, RoundedCornerShape(30.dp))
                .padding(6.dp)
        ) {

            TaskTab(
                text = "Semua",
                active = selectedTab == 0,
                accent = green,
                modifier = Modifier.weight(1f)
            ) { selectedTab = 0 }

            TaskTab(
                text = "Belum Selesai",
                active = selectedTab == 1,
                accent = green,
                modifier = Modifier.weight(1f)
            ) { selectedTab = 1 }

            TaskTab(
                text = "Selesai",
                active = selectedTab == 2,
                accent = green,
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
                    TASK TAB (DARK)
================================================== */

@Composable
fun TaskTab(
    text: String,
    active: Boolean,
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                if (active) accent else Color.Transparent,
                RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            color = if (active) Color.Black else Color.White,
            fontWeight = if (active) FontWeight.Bold else FontWeight.Normal,
            fontSize = 13.sp
        )
    }
}
