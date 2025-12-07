package com.example.cleanfypab.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cleanfypab.data.model.RoomModel

@Composable
fun TaskItem(
    room: RoomModel,
    onScanClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = MaterialTheme.shapes.medium
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // LEFT SIDE — Room Info
            Column {

                Text(
                    room.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )

                Text(
                    "Status: ${room.status}",
                    color = if (room.status == "Selesai") Color(0xFF4CAF50) else Color(0xFFE53935)
                )

                Text(
                    "Jam Penugasan: ${room.time}",
                    color = Color.Gray
                )
            }

            // RIGHT SIDE — Scan button
            Box(
                modifier = Modifier
                    .background(Color(0xFF1E88E5), shape = MaterialTheme.shapes.small)
                    .padding(horizontal = 14.dp, vertical = 8.dp)
                    .clickable { onScanClick() }
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
