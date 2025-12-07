package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

    var selectedTab by remember { mutableStateOf(1) } // 0=Semua, 1=Belum, 2=Selesai
    val rooms = vm.roomList.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Tugas Hari Ini", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Selasa, 21 Mei 2024", color = Color.Gray, fontSize = 14.sp)

        Spacer(Modifier.height(16.dp))

        // TAB AREA
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color(0xFFF2F2F2), MaterialTheme.shapes.medium)
                .padding(4.dp)
        ) {
            TaskTab(
                text = "Semua",
                active = selectedTab == 0,
                modifier = Modifier.weight(1f)
            ) {
                selectedTab = 0
            }

            TaskTab(
                text = "Belum Selesai",
                active = selectedTab == 1,
                modifier = Modifier.weight(1f)
            ) {
                selectedTab = 1
            }

            TaskTab(
                text = "Selesai",
                active = selectedTab == 2,
                modifier = Modifier.weight(1f)
            ) {
                selectedTab = 2
            }
        }

        Spacer(Modifier.height(16.dp))

        // FILTERING
        val filtered = when (selectedTab) {
            0 -> rooms.value
            1 -> rooms.value.filter { it.status != "Selesai" }
            else -> rooms.value.filter { it.status == "Selesai" }
        }

        LazyColumn {
            items(filtered) { room ->
                TaskItem(
                    room = room,
                    onScanClick = {
                        nav.navigate("detail/${room.id}")
                    }
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}


// ==================================================
//                TaskTab FIXED VERSION
// ==================================================

@Composable
fun TaskTab(
    text: String,
    active: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                if (active) Color.White else Color.Transparent,
                MaterialTheme.shapes.medium
            )
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            color = if (active) Color(0xFF2962FF) else Color.Gray,
            fontWeight = if (active) FontWeight.Bold else FontWeight.Normal
        )
    }
}
