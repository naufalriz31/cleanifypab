package com.example.cleanfypab.ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun ManualReportScreen(nav: NavHostController) {

    var roomName by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var photoAttached by remember { mutableStateOf(false) }
    var showPopup by remember { mutableStateOf(false) }

    val categories = listOf(
        "QR Code Hilang",
        "QR Code Rusak",
        "Tugas Pembersihan Tambahan",
        "Perlu Pembersihan Darurat"
    )

    /* ===== PALET WARNA (SESUAI LOGIN & HOME) ===== */
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
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .padding(20.dp)
    ) {

        Spacer(Modifier.height(24.dp))

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
                "Laporan Manual",
                color = darkText,
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        /* ================= INPUT NAMA RUANGAN ================= */
        Text("Nama Ruangan", color = darkText, fontSize = 16.sp)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = roomName,
            onValueChange = { roomName = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Masukkan nama ruangan...", color = grayText) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = cardSoft,
                unfocusedContainerColor = cardSoft,
                focusedIndicatorColor = primaryGreen,
                unfocusedIndicatorColor = borderSoft,
                cursorColor = primaryGreen,
                focusedTextColor = darkText,
                unfocusedTextColor = darkText
            )
        )

        Spacer(Modifier.height(24.dp))

        /* ================= DROPDOWN KATEGORI ================= */
        Text("Kategori Laporan", color = darkText, fontSize = 16.sp)
        Spacer(Modifier.height(8.dp))

        var expanded by remember { mutableStateOf(false) }

        Box {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = cardSoft,
                    contentColor = darkText
                )
            ) {
                Text(if (category.isEmpty()) "Pilih kategori..." else category)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = card
            ) {
                categories.forEach {
                    DropdownMenuItem(
                        text = { Text(it, color = darkText) },
                        onClick = {
                            category = it
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        /* ================= FOTO ================= */
        Text("Unggah Foto (opsional)", color = darkText, fontSize = 16.sp)
        Spacer(Modifier.height(12.dp))

        if (!photoAttached) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(cardSoft)
                    .border(1.dp, borderSoft, RoundedCornerShape(16.dp))
                    .clickable { photoAttached = true },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.AddAPhoto,
                    contentDescription = "Tambah Foto",
                    tint = primaryGreen,
                    modifier = Modifier.size(40.dp)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(card),
                contentAlignment = Alignment.Center
            ) {
                Text("Foto Terlampir", color = darkText)
            }
        }

        Spacer(Modifier.height(24.dp))

        /* ================= CATATAN ================= */
        Text("Catatan Tambahan", color = darkText, fontSize = 16.sp)
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            placeholder = { Text("Tulis pesan...", color = grayText) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = cardSoft,
                unfocusedContainerColor = cardSoft,
                cursorColor = primaryGreen,
                focusedIndicatorColor = primaryGreen,
                unfocusedIndicatorColor = borderSoft,
                focusedTextColor = darkText,
                unfocusedTextColor = darkText
            )
        )

        Spacer(Modifier.height(30.dp))

        /* ================= KIRIM ================= */
        Button(
            onClick = {
                if (roomName.isNotEmpty() && category.isNotEmpty()) {
                    showPopup = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryGreen),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "Kirim Laporan Manual",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }

    /* ================= POPUP SUKSES ================= */
    if (showPopup) {
        ManualReportSuccessPopupLight {
            showPopup = false
            nav.navigate("home")
        }
    }
}

@Composable
fun ManualReportSuccessPopupLight(onFinished: () -> Unit) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        delay(1200)
        visible = false
        delay(200)
        onFinished()
    }

    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(tween(250)) + fadeIn(tween(250)),
        exit = fadeOut(tween(200))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x55000000))
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(20.dp))
                    .padding(26.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.AddAPhoto,
                        contentDescription = "Selesai",
                        tint = Color(0xFF2ECC71),
                        modifier = Modifier.size(70.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        "Laporan Manual Terkirim!",
                        color = Color(0xFF1E2D28),
                        fontSize = 22.sp
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        "Laporan pembersihan manual berhasil disimpan.",
                        color = Color(0xFF6B7C75),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
