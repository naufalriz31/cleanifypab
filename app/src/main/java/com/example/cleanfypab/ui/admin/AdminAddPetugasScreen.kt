package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminAddPetugasScreen(
    onBack: () -> Unit = {},
    onSave: (PetugasForm) -> Unit = {}
) {

    /* ===== WARNA CLEANIFY ===== */
    val bgGradient = Brush.verticalGradient(
        listOf(
            Color(0xFFF6FBF8),
            Color(0xFFE9F5EE)
        )
    )

    val green = Color(0xFF2ECC71)
    val borderSoft = Color(0xFFE0E0E0)
    val cardColor = Color.White
    val darkText = Color(0xFF1E2D28)

    var nama by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Aktif") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .padding(16.dp)
    ) {

        /* ================= HEADER ================= */
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }

            Spacer(Modifier.width(8.dp))

            Text(
                "Tambah Petugas",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = darkText
            )
        }

        Spacer(Modifier.height(24.dp))

        /* ================= FORM ================= */
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                /* Nama */
                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text("Nama Petugas") },
                    leadingIcon = { Icon(Icons.Default.Person, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(Modifier.height(14.dp))

                /* Role */
                OutlinedTextField(
                    value = role,
                    onValueChange = { role = it },
                    label = { Text("Role / Jabatan") },
                    leadingIcon = { Icon(Icons.Default.Work, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(Modifier.height(14.dp))

                /* Nomor HP */
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("No. HP") },
                    leadingIcon = { Icon(Icons.Default.Phone, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(Modifier.height(14.dp))

                /* Status */
                Text(
                    "Status Petugas",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Spacer(Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatusChip("Aktif", status == "Aktif") { status = "Aktif" }
                    StatusChip("Bertugas", status == "Bertugas") { status = "Bertugas" }
                    StatusChip("Cuti", status == "Cuti") { status = "Cuti" }
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        /* ================= BUTTON SIMPAN ================= */
        Button(
            onClick = {
                onSave(
                    PetugasForm(
                        nama = nama,
                        role = role,
                        phone = phone,
                        status = status
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = green),
            enabled = nama.isNotBlank() && role.isNotBlank()
        ) {
            Text("Simpan Petugas", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

/* ================= KOMPONEN ================= */

@Composable
fun StatusChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = if (selected) Color(0xFF2ECC71).copy(alpha = 0.15f) else Color.Transparent,
        border = BorderStroke(1.dp, Color(0xFF2ECC71)),
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (selected) Color(0xFF2ECC71) else Color(0xFF1E2D28),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

/* ================= MODEL ================= */

data class PetugasForm(
    val nama: String,
    val role: String,
    val phone: String,
    val status: String
)
