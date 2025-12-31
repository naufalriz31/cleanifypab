package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminAssignRoomScreen(
    onClose: () -> Unit = {}
) {

    /* ===== PALET ADMIN ===== */
    val bg = Color(0xFF0F2A1D)
    val card = Color(0xFF163828)
    val border = Color(0xFF245C3A)
    val green = Color(0xFF2ECC71)
    val textSecondary = Color(0xFF9BA5A0)

    var selectedOfficer by remember { mutableStateOf("Petugas Wick") }
    var notes by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
    ) {

        /* ================= CONTENT ================= */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 90.dp)
                .verticalScroll(rememberScrollState())
        ) {

            /* ===== HEADER ===== */
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Tutup",
                    tint = Color.White,
                    modifier = Modifier.clickable { onClose() }
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    "Penugasan Ruangan",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(24.dp))

            /* ===== PILIH RUANGAN ===== */
            Text("Pilih Ruangan", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(
                "Nomor atau nama ruangan",
                color = textSecondary,
                fontSize = 12.sp
            )

            Spacer(Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(card, RoundedCornerShape(16.dp))
                    .border(1.dp, border, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Pilih ruangan...", color = textSecondary)
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = textSecondary
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            /* ===== PILIH PETUGAS ===== */
            Text("Tugaskan Petugas", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                leadingIcon = { Icon(Icons.Default.Search, null, tint = textSecondary) },
                placeholder = {
                    Text("Cari petugas berdasarkan nama...", color = textSecondary)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = card,
                    unfocusedContainerColor = card,
                    focusedBorderColor = green,
                    unfocusedBorderColor = border,
                    cursorColor = green,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(Modifier.height(16.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                item {
                    OfficerChip("Petugas Sarah", selectedOfficer == "Petugas Sarah", green) {
                        selectedOfficer = "Petugas Sarah"
                    }
                }
                item {
                    OfficerChip("Petugas Wick", selectedOfficer == "Petugas Wick", green) {
                        selectedOfficer = "Petugas Wick"
                    }
                }
                item {
                    OfficerChip("Petugas Bob", selectedOfficer == "Petugas Bob", green) {
                        selectedOfficer = "Petugas Bob"
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            /* ===== CATATAN ===== */
            Text("Catatan Tambahan", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                placeholder = {
                    Text(
                        "Masukkan instruksi khusus atau kebutuhan peralatan...",
                        color = textSecondary
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = card,
                    unfocusedContainerColor = card,
                    focusedBorderColor = green,
                    unfocusedBorderColor = border,
                    cursorColor = green,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )
        }

        /* ================= BUTTON KONFIRMASI ================= */
        Button(
            onClick = {
                println("PENUGASAN DIKONFIRMASI")
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = green),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.CheckCircle, null, tint = Color.Black)
            Spacer(Modifier.width(8.dp))
            Text(
                "Konfirmasi Penugasan",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/* ================= COMPONENT ================= */

@Composable
fun OfficerChip(
    name: String,
    selected: Boolean,
    accent: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(
                if (selected) accent.copy(alpha = 0.15f) else Color.Transparent,
                RoundedCornerShape(50)
            )
            .border(1.dp, accent, RoundedCornerShape(50))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(accent.copy(alpha = 0.9f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Person, null, tint = Color.Black)
        }
        Spacer(Modifier.width(8.dp))
        Text(name, color = Color.White, fontSize = 14.sp)
    }
}
