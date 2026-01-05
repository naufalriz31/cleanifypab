package com.example.cleanfypab.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cleanfypab.viewmodel.RoomViewModel

@Composable
fun DetailRoomScreen(
    nav: NavHostController,
    vm: RoomViewModel,
    roomId: String // ✅ ubah ke String (docId Firestore)
) {
    val room = vm.getRoomById(roomId)

    if (room == null) {
        Text("Ruangan tidak ditemukan", Modifier.padding(16.dp))
        return
    }

    var note by remember { mutableStateOf("") }

    /* ===== PALET WARNA CLEANIFY (LIGHT) ===== */
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
    val warning = Color(0xFFE67E22)
    val danger = Color(0xFFE74C3C)

    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    /* ===== STATUS COLOR ===== */
    val statusColor by animateColorAsState(
        targetValue = when (room.status) {
            "Selesai" -> primaryGreen
            "Menunggu" -> warning
            "Perlu Dicek" -> danger
            else -> grayText
        },
        label = "statusColorAnim"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .padding(16.dp)
    ) {

        /* ===== HEADER ===== */
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Kembali",
                tint = darkText,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { nav.popBackStack() }
            )
            Spacer(Modifier.width(12.dp))
            Text(
                "Detail Ruangan",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = darkText
            )
        }

        Spacer(Modifier.height(16.dp))

        /* ===== CARD INFORMASI RUANGAN ===== */
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = card),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(Modifier.padding(20.dp)) {

                Text(
                    room.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkText
                )

                Spacer(Modifier.height(10.dp))

                /* ===== BADGE STATUS ===== */
                Box(
                    modifier = Modifier
                        .background(
                            statusColor.copy(alpha = 0.15f),
                            RoundedCornerShape(20.dp)
                        )
                        .border(
                            1.dp,
                            statusColor,
                            RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        room.status,
                        color = statusColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    "Waktu Penugasan",
                    color = grayText,
                    fontSize = 12.sp
                )
                Text(
                    room.time.ifBlank { "-" }, // ✅ aman kalau kosong
                    color = darkText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        /* ===== UPLOAD FOTO ===== */
        OutlinedButton(
            onClick = { /* TODO: Kamera / Galeri */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, primaryGreen),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = primaryGreen
            )
        ) {
            Icon(Icons.Default.CameraAlt, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Tambah Foto Bukti (Opsional)")
        }

        Spacer(Modifier.height(20.dp))

        /* ===== CATATAN ===== */
        Text(
            "Catatan Tambahan (Opsional)",
            color = darkText,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(8.dp))

        TextField(
            value = note,
            onValueChange = { note = it },
            placeholder = { Text("Tulis catatan singkat...", color = grayText) },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = cardSoft,
                unfocusedContainerColor = cardSoft,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = darkText,
                unfocusedTextColor = darkText,
                cursorColor = primaryGreen
            ),
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(Modifier.weight(1f))

        /* ===== BUTTON KONFIRMASI ===== */
        Button(
            onClick = {
                vm.markRoomClean(roomId) // ✅ sekarang String
                nav.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryGreen)
        ) {
            Text(
                "Tandai Sebagai Selesai",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
