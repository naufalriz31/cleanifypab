package com.example.cleanfypab.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    roomId: Int
) {
    val room = vm.getRoomById(roomId)

    if (room == null) {
        Text("Ruangan tidak ditemukan", Modifier.padding(16.dp))
        return
    }

    var note by remember { mutableStateOf("") }

    // Animasi warna badge status
    val statusColor by animateColorAsState(
        targetValue = if (room.status == "Selesai")
            Color(0xFF4CAF50)
        else
            Color(0xFFE53935),
        label = "statusColorAnim"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // TOMBOL KEMBALI + JUDUL
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Kembali",
                modifier = Modifier
                    .size(30.dp)
                    .clickable { nav.popBackStack() }
            )
            Spacer(Modifier.width(12.dp))
            Text(
                "Detail Ruangan",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(10.dp))

        // KARTU INFORMASI RUANGAN
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(6.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(Modifier.padding(16.dp)) {

                Text(
                    room.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                // BADGE STATUS
                Box(
                    modifier = Modifier
                        .background(
                            color = statusColor.copy(alpha = 0.15f),
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        room.status,
                        color = statusColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    "Waktu Penugasan: ${room.time}",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // UNGGAH FOTO BUKTI
        OutlinedButton(
            onClick = { /* TODO: Buka kamera atau galeri */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.CameraAlt, contentDescription = "Kamera")
            Spacer(Modifier.width(8.dp))
            Text("Tambah Foto Bukti (Opsional)")
        }

        Spacer(Modifier.height(20.dp))

        // CATATAN TAMBAHAN
        Text("Catatan Tambahan (Opsional)")
        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.weight(1f))

        // TOMBOL KONFIRMASI
        Button(
            onClick = {
                vm.markRoomClean(roomId)
                nav.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Tandai Sebagai Sudah Dibersihkan")
        }
    }
}
