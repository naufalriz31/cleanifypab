package com.example.cleanfypab.ui.screen

import androidx.compose.ui.text.font.FontWeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun EditReportScreen(nav: NavHostController, id: Int) {

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A1C12))
            .padding(16.dp)
    ) {

        // BAR ATAS
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Kembali",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { nav.popBackStack() }
            )

            Spacer(Modifier.width(16.dp))

            Text(
                "Edit Laporan",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.weight(1f))

            Text(
                "Simpan",
                fontSize = 18.sp,
                color = Color(0xFF00E676),
                modifier = Modifier.clickable { nav.popBackStack() }
            )
        }

        Spacer(Modifier.height(24.dp))

        // JUDUL
        Text(
            "Ruang 301 - Laporan Pembersihan",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(Modifier.height(6.dp))

        Text(
            "Dibuat: 24 Jan 2024 pukul 10:32",
            color = Color(0xFF4CAF50),
            fontSize = 14.sp
        )

        Spacer(Modifier.height(20.dp))

        // BAGIAN FOTO
        Text(
            "Foto",
            color = Color.White,
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
                        .background(Color(0xFF1D2A22)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Image,
                        contentDescription = "Foto",
                        tint = Color.Gray,
                        modifier = Modifier.size(50.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))
            }

            item {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                        .clickable { }
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.AddAPhoto,
                            contentDescription = "Tambah Foto",
                            tint = Color.Gray,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text("Tambah Foto", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // BAGIAN CATATAN
        Text(
            "Catatan",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(8.dp))

        TextField(
            value = notes,
            onValueChange = { notes = it },
            placeholder = {
                Text("Tambahkan detail yang relevan...", color = Color.Gray)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(Color(0xFF0F2A1D), RoundedCornerShape(12.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF0F2A1D),
                unfocusedContainerColor = Color(0xFF0F2A1D),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(Modifier.height(24.dp))

        // BAGIAN CHECKLIST
        Text(
            "Checklist Opsional",
            color = Color.White,
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
                        checkedColor = Color(0xFF00E676),
                        uncheckedColor = Color.White
                    )
                )

                Spacer(Modifier.width(8.dp))

                Text(item, color = Color.White, fontSize = 16.sp)
            }
        }

        Spacer(Modifier.height(40.dp))

        Button(
            onClick = { nav.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00E676)
            )
        ) {
            Text(
                "Simpan Perubahan",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}
