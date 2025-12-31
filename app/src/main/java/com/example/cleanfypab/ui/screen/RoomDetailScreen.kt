package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
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

    /* ===== PALET WARNA CLEANIFY (LIGHT) ===== */
    val bgGradient = Brush.verticalGradient(
        listOf(
            Color(0xFFF6FBF8),
            Color(0xFFE9F5EE)
        )
    )

    val card = Color.White
    val cardSoft = Color(0xFFF2F7F4)

    val primaryGreen = Color(0xFF2ECC71)
    val warning = Color(0xFFFFC107)
    val danger = Color(0xFFE53935)

    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .padding(20.dp)
    ) {

        Spacer(Modifier.height(20.dp))

        /* ===== HEADER ===== */
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    tint = darkText
                )
            }
            Text(
                "Detail Ruangan",
                color = darkText,
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(Modifier.height(20.dp))

        /* ===== CARD DETAIL ===== */
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = card),
            elevation = CardDefaults.cardElevation(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(20.dp)) {

                Text(
                    room?.name ?: "Ruang $id",
                    color = darkText,
                    fontSize = 24.sp
                )

                Text(
                    "Pembaruan Terakhir: ${room?.time ?: "-"}",
                    color = grayText,
                    fontSize = 14.sp
                )

                Spacer(Modifier.height(16.dp))

                val status = room?.status ?: "-"
                val statusColor = when (status) {
                    "Selesai" -> primaryGreen
                    "Menunggu" -> warning
                    "Perlu Dicek" -> danger
                    else -> grayText
                }

                Surface(
                    color = statusColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, statusColor)
                ) {
                    Text(
                        status,
                        color = statusColor,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        /* ===== FOTO ===== */
        Text("Foto", color = darkText, fontSize = 18.sp)
        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(cardSoft),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Image,
                        contentDescription = "Foto",
                        tint = grayText,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(30.dp))

        /* ===== BUTTON ===== */
        Button(
            onClick = { nav.navigate("update_status/$id") },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryGreen)
        ) {
            Text("Perbarui Status", color = Color.White, fontSize = 16.sp)
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                vm.markRoomClean(id)
                nav.navigate("history")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            border = BorderStroke(1.dp, primaryGreen),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = primaryGreen
            )
        ) {
            Text("Tandai Selesai (Cepat)")
        }
    }
}
