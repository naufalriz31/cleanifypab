package com.example.cleanfypab.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cleanfypab.data.model.RoomModel

@Composable
fun TaskItem(
    room: RoomModel,
    onScanClick: () -> Unit
) {

    // 🎨 WARNA KONSISTEN DENGAN HOME & REPORT
    val cardColor = Color(0xFF14231C)
    val mutedText = Color(0xFF9BA5A0)
    val green = Color(0xFF00E676)
    val orange = Color(0xFFFFA000)
    val red = Color(0xFFE53935)
    val blue = Color(0xFF2196F3)

    val statusColor = when (room.status) {
        "Selesai" -> green
        "Menunggu" -> orange
        "Perlu Dicek" -> red
        else -> mutedText
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor // ✅ INI KUNCI UTAMA
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // LEFT — INFO
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    room.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    "Status: ${room.status}",
                    color = statusColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    "Jam Penugasan: ${room.time}",
                    color = mutedText,
                    fontSize = 12.sp
                )
            }

            // RIGHT — SCAN BUTTON
            Box(
                modifier = Modifier
                    .background(blue, RoundedCornerShape(10.dp))
                    .clickable(enabled = room.status != "Selesai") {
                        onScanClick()
                    }
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Scan",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
