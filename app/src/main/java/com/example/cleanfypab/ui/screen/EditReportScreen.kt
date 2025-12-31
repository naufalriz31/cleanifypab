package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun EditReportScreen(
    nav: NavHostController,
    id: Int
) {

    var notes by remember { mutableStateOf("") }

    val checklistItems = listOf(
        "Mengisi ulang minibar",
        "Mengganti seprai tempat tidur",
        "Membersihkan permukaan kamar mandi",
        "Memeriksa detektor asap"
    )

    var selectedChecklist = remember {
        mutableStateOf(mutableSetOf("Membersihkan permukaan kamar mandi"))
    }

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
            .padding(16.dp)
    ) {

        /* ================= BAR ATAS ================= */
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Kembali",
                tint = darkText,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { nav.popBackStack() }
            )

            Spacer(Modifier.width(16.dp))

            Text(
                "Edit Laporan",
                fontSize = 20.sp,
                color = darkText,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.weight(1f))

            Text(
                "Simpan",
                fontSize = 16.sp,
                color = primaryGreen,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { nav.popBackStack() }
            )
        }

        Spacer(Modifier.height(24.dp))

        /* ================= JUDUL ================= */
        Text(
            "Ruang 301 - Laporan Pembersihan",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = darkText
        )

        Spacer(Modifier.height(6.dp))

        Text(
            "Dibuat: 24 Jan 2024 pukul 10:32",
            color = primaryGreen,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(20.dp))

        /* ================= FOTO ================= */
        Text(
            "Foto",
            color = darkText,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(12.dp))

        LazyRow {
            items(3) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(cardSoft)
                        .border(1.dp, borderSoft, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Image,
                        contentDescription = "Foto",
                        tint = grayText,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))
            }

            item {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(card)
                        .border(1.dp, borderSoft, RoundedCornerShape(12.dp))
                        .clickable { }
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.AddAPhoto,
                            contentDescription = "Tambah Foto",
                            tint = primaryGreen,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Tambah Foto",
                            color = darkText,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        /* ================= CATATAN ================= */
        Text(
            "Catatan",
            color = darkText,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(8.dp))

        TextField(
            value = notes,
            onValueChange = { notes = it },
            placeholder = {
                Text("Tambahkan detail yang relevan...", color = grayText)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(cardSoft, RoundedCornerShape(12.dp))
                .border(1.dp, borderSoft, RoundedCornerShape(12.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = cardSoft,
                unfocusedContainerColor = cardSoft,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = primaryGreen,
                focusedTextColor = darkText,
                unfocusedTextColor = darkText
            )
        )

        Spacer(Modifier.height(24.dp))

        /* ================= CHECKLIST ================= */
        Text(
            "Checklist Opsional",
            color = darkText,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(12.dp))

        checklistItems.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (selectedChecklist.value.contains(item)) {
                            selectedChecklist.value.remove(item)
                        } else {
                            selectedChecklist.value.add(item)
                        }
                    }
                    .padding(vertical = 10.dp)
            ) {
                Checkbox(
                    checked = selectedChecklist.value.contains(item),
                    onCheckedChange = {
                        if (selectedChecklist.value.contains(item)) {
                            selectedChecklist.value.remove(item)
                        } else {
                            selectedChecklist.value.add(item)
                        }
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = primaryGreen,
                        uncheckedColor = grayText
                    )
                )

                Spacer(Modifier.width(8.dp))

                Text(item, color = darkText, fontSize = 16.sp)
            }
        }

        Spacer(Modifier.height(40.dp))

        /* ================= BUTTON SIMPAN ================= */
        Button(
            onClick = { nav.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryGreen
            )
        ) {
            Text(
                "Simpan Perubahan",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
