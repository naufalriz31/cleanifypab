package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminAuditLogScreen() {

    /* ===== PALET ADMIN ===== */
    val bg = Color(0xFF0F2A1D)
    val card = Color(0xFF163828)
    val border = Color(0xFF245C3A)
    val green = Color(0xFF2ECC71)
    val textSecondary = Color(0xFF9BA5A0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(16.dp)
    ) {

        /* ===== HEADER ===== */
        Text(
            "Log Audit",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "Riwayat perubahan status ruangan",
            color = textSecondary,
            fontSize = 13.sp
        )

        Spacer(Modifier.height(16.dp))

        /* ===== LIST LOG ===== */
        repeat(5) { index ->
            AuditItem(
                user = "Petugas $index",
                time = "10:${index}0 WIB",
                before = "Belum Dibersihkan",
                after = "Selesai",
                cardColor = card,
                borderColor = border,
                accent = green,
                textSecondary = textSecondary
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
fun AuditItem(
    user: String,
    time: String,
    before: String,
    after: String,
    cardColor: Color,
    borderColor: Color,
    accent: Color,
    textSecondary: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(cardColor, RoundedCornerShape(16.dp))
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {

        /* USER */
        Text(
            user,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )

        Spacer(Modifier.height(4.dp))

        /* TIME */
        Text(
            "Waktu: $time",
            color = textSecondary,
            fontSize = 12.sp
        )

        Spacer(Modifier.height(8.dp))

        /* STATUS CHANGE */
        Row {
            Text(
                before,
                color = textSecondary,
                fontSize = 13.sp
            )
            Text(
                "  →  ",
                color = accent,
                fontWeight = FontWeight.Bold
            )
            Text(
                after,
                color = accent,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }
    }
}
