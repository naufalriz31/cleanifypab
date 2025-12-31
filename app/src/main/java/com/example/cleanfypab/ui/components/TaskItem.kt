package com.example.cleanfypab.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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

    /* ===== WARNA LIGHT CLEANIFY ===== */
    val cardColor = Color.White
    val borderColor = Color(0xFFE0E0E0)

    val green = Color(0xFF2ECC71)
    val orange = Color(0xFFE67E22)
    val red = Color(0xFFE74C3C)
    val blue = Color(0xFF2196F3)

    val darkText = Color(0xFF1E2D28)
    val mutedText = Color(0xFF6B7C75)

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
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            /* ===== INFO RUANGAN ===== */
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    room.name,
                    color = darkText,
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

            /* ===== BUTTON SCAN ===== */
            Box(
                modifier = Modifier
                    .background(
                        if (room.status == "Selesai")
                            Color(0xFFE0E0E0)
                        else
                            blue,
                        RoundedCornerShape(10.dp)
                    )
                    .clickable(enabled = room.status != "Selesai") {
                        onScanClick()
                    }
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Scan",
                    color = if (room.status == "Selesai")
                        Color.Gray
                    else
                        Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
