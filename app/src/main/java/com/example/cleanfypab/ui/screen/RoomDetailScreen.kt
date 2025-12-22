package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cleanfypab.viewmodel.RoomViewModel

@Composable
fun RoomDetailScreen(
    nav: NavHostController,
    vm: RoomViewModel,
    id: Int
) {
    val room = vm.getRoomById(id)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF05150E))
            .padding(20.dp)
    ) {
        Spacer(Modifier.height(30.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                "Room Detail",
                color = Color.White,
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2A1D)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(20.dp)) {
                Text(room?.name ?: "Room $id", color = Color.White, fontSize = 24.sp)
                Text("Last Update: ${room?.time ?: "-"}", color = Color(0xFF9BA5A0), fontSize = 14.sp)

                Spacer(Modifier.height(16.dp))

                val status = room?.status ?: "-"
                val color = when (status) {
                    "Selesai" -> Color(0xFF00E676)
                    "Menunggu" -> Color(0xFFFFC107)
                    "Perlu Dicek" -> Color(0xFFE53935)
                    else -> Color.Gray
                }

                Surface(
                    color = color.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, color)
                ) {
                    Text(
                        status,
                        color = color,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Text("Photos", color = Color.White, fontSize = 18.sp)
        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1B2F23)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Image,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(30.dp))

        Button(
            onClick = { nav.navigate("update_status/$id") },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E676))
        ) {
            Text("Update Status", color = Color.Black, fontSize = 18.sp)
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = { vm.markRoomClean(id); nav.navigate("history") },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            border = BorderStroke(1.dp, Color.White),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
        ) {
            Text("Tandai Selesai (Quick)")
        }
    }
}
