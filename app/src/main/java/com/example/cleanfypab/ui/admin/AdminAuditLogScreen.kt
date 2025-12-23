package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AdminAuditLogScreen() {

    val card = Color(0xFF163828)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F2A1D))
            .padding(16.dp)
    ) {

        Text("Audit Log", color = Color.White, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(12.dp))

        repeat(5) { index ->
            AuditItem(
                user = "Petugas $index",
                time = "10:${index}0 WIB",
                before = "Belum Dibersihkan",
                after = "Bersih",
                cardColor = card
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
fun AuditItem(
    user: String,
    time: String,
    before: String,
    after: String,
    cardColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(cardColor, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(user, color = Color.White, fontWeight = FontWeight.Bold)
        Text("Waktu: $time", color = Color.Gray)
        Text("Status: $before → $after", color = Color(0xFF2DFF8F))
    }
}
