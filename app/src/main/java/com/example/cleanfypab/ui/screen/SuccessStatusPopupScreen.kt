package com.example.cleanfypab.ui.screen

import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun SuccessStatusPopupScreen(nav: NavHostController) {

    LaunchedEffect(Unit) {
        delay(1200)
        nav.popBackStack()
    }

    /* ===== PALET WARNA CLEANIFY ===== */
    val overlay = Color(0x55000000)
    val card = Color.White
    val borderSoft = Color(0xFFE0E0E0)

    val primaryGreen = Color(0xFF2ECC71)
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(overlay),
        contentAlignment = Alignment.Center
    ) {

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = card),
            modifier = Modifier
                .padding(24.dp)
                .border(1.dp, borderSoft, RoundedCornerShape(20.dp))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Berhasil",
                    tint = primaryGreen,
                    modifier = Modifier.size(80.dp)
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    "Status Berhasil Diperbarui!",
                    color = darkText,
                    fontSize = 22.sp
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    "Menyimpan laporan Anda...",
                    color = grayText,
                    fontSize = 14.sp
                )
            }
        }
    }
}
