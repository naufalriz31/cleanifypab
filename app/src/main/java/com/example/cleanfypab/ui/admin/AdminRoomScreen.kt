package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cleanfypab.data.model.RoomFirestore
import com.example.cleanfypab.viewmodel.admin.RoomAdminViewModel

@Composable
fun AdminRoomScreen(
    onAddRoom: () -> Unit, // ✅ tombol + ke scene tambah ruangan
    vm: RoomAdminViewModel = viewModel()
) {
    val state by vm.state.collectAsState()

    /* ===== WARNA CLEANIFY ===== */
    val bgGradient = Brush.verticalGradient(listOf(Color(0xFFF6FBF8), Color(0xFFE9F5EE)))
    val cardColor = Color.White
    val borderSoft = Color(0xFFE0E0E0)

    val green = Color(0xFF2ECC71)
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    var confirmDeleteRoom by remember { mutableStateOf<RoomFirestore?>(null) }

    LaunchedEffect(Unit) { vm.loadRooms() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .padding(16.dp)
    ) {

        /* ================= HEADER ================= */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Manajemen Ruangan",
                color = darkText,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            FloatingActionButton(
                onClick = onAddRoom,
                containerColor = green,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Ruangan", tint = Color.White)
            }
        }

        Spacer(Modifier.height(12.dp))

        if (state.loading) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
            Spacer(Modifier.height(12.dp))
        }

        state.error?.let {
            Text(it, color = Color.Red, modifier = Modifier.padding(bottom = 8.dp))
        }
        state.successMessage?.let {
            Text(it, color = green, modifier = Modifier.padding(bottom = 8.dp))
        }

        /* ================= LIST ================= */
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(state.rooms, key = { it.id }) { room ->
                RoomItemDeletable(
                    room = room,
                    card = cardColor,
                    border = borderSoft,
                    darkText = darkText,
                    grayText = grayText,
                    danger = Color(0xFFE74C3C),
                    onDelete = { confirmDeleteRoom = room }
                )
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }

    /* ================= DIALOG KONFIRMASI DELETE ================= */
    confirmDeleteRoom?.let { room ->
        AlertDialog(
            onDismissRequest = { confirmDeleteRoom = null },
            title = { Text("Hapus ruangan?") },
            text = { Text("Kamu yakin ingin menghapus “${room.name}”?") },
            confirmButton = {
                TextButton(onClick = {
                    vm.deleteRoom(room.id)
                    confirmDeleteRoom = null
                }) { Text("Hapus") }
            },
            dismissButton = {
                TextButton(onClick = { confirmDeleteRoom = null }) { Text("Batal") }
            }
        )
    }
}

@Composable
private fun RoomItemDeletable(
    room: RoomFirestore,
    card: Color,
    border: Color,
    darkText: Color,
    grayText: Color,
    danger: Color,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(card, RoundedCornerShape(20.dp))
            .border(1.dp, border, RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(modifier = Modifier.weight(1f)) {
            Text(room.name, color = darkText, fontWeight = FontWeight.Bold)
            Text("QR: ${room.qrValue}", color = grayText, fontSize = 13.sp)
        }

        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = danger)
        }
    }
}
