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
fun RoomItem(
    room: RoomModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

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

            Box(
                modifier = Modifier
                    .background(Color(0xFF1E88E5), shape = MaterialTheme.shapes.small)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("Scan", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
