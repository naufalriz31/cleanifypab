package com.example.cleanfypab.ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cleanfypab.viewmodel.RoomViewModel
import kotlinx.coroutines.delay

@Composable
fun UpdateStatusScreen(
    nav: NavHostController,
    vm: RoomViewModel,
    roomId: String
) {
    var selected by remember { mutableStateOf("") }
    var showPopup by remember { mutableStateOf(false) }

    val roomName = vm.getRoomById(roomId)?.name ?: "Ruang #$roomId"

    /* ===== PALET WARNA CLEANIFY ===== */
    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF6FBF8),
            Color(0xFFE9F5EE)
        )
    )

    val card = Color.White
    val cardSoft = Color(0xFFF2F7F4)
    val borderSoft = Color(0xFFE0E0E0)

    val primaryGreen = Color(0xFF2ECC71)
    val yellow = Color(0xFFFFC107)
    val red = Color(0xFFE53935)

    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            Spacer(Modifier.height(16.dp))

            /* ================= HEADER ================= */
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { nav.popBackStack() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = darkText
                    )
                }
                Text(
                    "Perbarui Status",
                    color = darkText,
                    fontSize = 22.sp
                )
            }

            Spacer(Modifier.height(20.dp))

            /* ================= INFO RUANGAN ================= */
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = card),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text("Nama Ruangan", color = grayText, fontSize = 14.sp)
                    Text(roomName, color = darkText, fontSize = 20.sp)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Pemeriksaan Terakhir: -",
                        color = grayText,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(Modifier.height(30.dp))

            Text("Pilih Status Ruangan", color = darkText, fontSize = 18.sp)
            Spacer(Modifier.height(16.dp))

            StatusOptionCardLight(
                title = "Sudah Dibersihkan",
                description = "Ruangan telah dibersihkan dengan baik",
                color = primaryGreen,
                icon = Icons.Default.CheckCircle,
                selected = selected == "cleaned",
                onClick = { selected = "cleaned" }
            )

            StatusOptionCardLight(
                title = "Belum Dibersihkan",
                description = "Ruangan belum dibersihkan",
                color = yellow,
                icon = Icons.Default.Close,
                selected = selected == "notyet",
                onClick = { selected = "notyet" }
            )

            StatusOptionCardLight(
                title = "Perlu Pembersihan",
                description = "Ruangan memerlukan pembersihan segera",
                color = red,
                icon = Icons.Default.Warning,
                selected = selected == "issue",
                onClick = { selected = "issue" }
            )

            Spacer(Modifier.height(30.dp))

            /* ================= BUTTON ================= */
            Button(
                onClick = {
                    val status = when (selected) {
                        "cleaned" -> "Selesai"
                        "notyet" -> "Menunggu"
                        "issue" -> "Perlu Dicek"
                        else -> "Menunggu"
                    }
                    vm.updateRoomStatus(roomId, status)
                    showPopup = true
                },
                enabled = selected.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryGreen)
            ) {
                Text(
                    "Kirim Status",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }

        if (showPopup) {
            SuccessUpdatePopupLight {
                showPopup = false
                nav.navigate("history")
            }
        }
    }
}

/* ==================================================
                STATUS OPTION CARD (LIGHT)
================================================== */

@Composable
fun StatusOptionCardLight(
    title: String,
    description: String,
    color: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bg = if (selected) color.copy(alpha = 0.15f) else Color.White
    val borderColor = if (selected) color else Color(0xFFE0E0E0)

    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, borderColor),
        colors = CardDefaults.cardColors(containerColor = bg)
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = title, tint = color, modifier = Modifier.size(32.dp))
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(title, color = darkText, fontSize = 18.sp)
                Text(description, color = grayText, fontSize = 13.sp)
            }
            if (selected) {
                Icon(Icons.Default.CheckCircle, contentDescription = "Dipilih", tint = color)
            }
        }
    }
}

/* ==================================================
                SUCCESS POPUP (LIGHT)
================================================== */

@Composable
fun SuccessUpdatePopupLight(onFinished: () -> Unit) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        delay(1200)
        visible = false
        delay(250)
        onFinished()
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(250)) + scaleIn(tween(250)),
        exit = fadeOut(tween(200))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x55000000))
        ) {
            Card(
                modifier = Modifier.align(Alignment.Center),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(26.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Berhasil",
                        tint = Color(0xFF2ECC71),
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "Status Berhasil Diperbarui!",
                        color = Color(0xFF1E2D28),
                        fontSize = 22.sp
                    )
                    Text(
                        "Data berhasil disimpan.",
                        color = Color(0xFF6B7C75),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
