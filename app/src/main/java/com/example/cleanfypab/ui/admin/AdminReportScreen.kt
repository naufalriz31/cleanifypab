package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminReportScreen(
    onAssignClick: () -> Unit
) {

    /* ===== PALET LIGHT CLEANIFY (SAMA DASHBOARD) ===== */
    val bgGradient = Brush.verticalGradient(
        listOf(
            Color(0xFFF6FBF8),
            Color(0xFFE9F5EE)
        )
    )

    val cardColor = Color.White
    val borderSoft = Color(0xFFE0E0E0)

    val green = Color(0xFF2ECC71)
    val red = Color(0xFFFF6B6B)
    val yellow = Color(0xFFFFC107)

    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    var selectedFilter by remember { mutableStateOf("Semua") }

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
            Icon(Icons.Default.ArrowBack, null, tint = darkText)

            Text(
                "Manajemen Laporan",
                color = darkText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Icon(Icons.Default.Download, null, tint = darkText)
        }

        Spacer(Modifier.height(16.dp))

        /* ================= SEARCH ================= */
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text("Cari berdasarkan ruangan atau masalah...", color = grayText)
            },
            leadingIcon = {
                Icon(Icons.Default.Search, null, tint = grayText)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = cardColor,
                unfocusedContainerColor = cardColor,
                focusedBorderColor = borderSoft,
                unfocusedBorderColor = borderSoft,
                cursorColor = darkText,
                focusedTextColor = darkText,
                unfocusedTextColor = darkText
            )
        )

        Spacer(Modifier.height(16.dp))

        /* ================= FILTER ================= */
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            FilterChipLight("Semua", selectedFilter == "Semua") {
                selectedFilter = "Semua"
            }
            FilterChipLight("Baru", selectedFilter == "Baru", dot = true) {
                selectedFilter = "Baru"
            }
            FilterChipLight("Ditugaskan", selectedFilter == "Ditugaskan") {
                selectedFilter = "Ditugaskan"
            }
            FilterChipLight("Selesai", selectedFilter == "Selesai") {
                selectedFilter = "Selesai"
            }
        }

        Spacer(Modifier.height(20.dp))

        /* ================= LIST ================= */
        LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {

            item {
                ReportItemLight(
                    title = "Ruang 304 - AC Bermasalah",
                    desc = "Unit AC mengeluarkan suara keras dan tidak mendinginkan ruangan...",
                    status = "KRITIS",
                    statusColor = red,
                    time = "2 jam lalu",
                    user = "John Doe",
                    actionText = "Tetapkan",
                    actionColor = green,
                    darkText = darkText,
                    grayText = grayText,
                    cardColor = cardColor
                ) { onAssignClick() }
            }

            item {
                ReportItemLight(
                    title = "Lobi - Tumpahan",
                    desc = "Tumpahan kopi besar di dekat pintu masuk utama...",
                    status = "BARU",
                    statusColor = green,
                    time = "4 jam lalu",
                    user = "Sarah Smith",
                    actionText = "Detail",
                    actionColor = green,
                    darkText = darkText,
                    grayText = grayText,
                    cardColor = cardColor
                ) {}
            }

            item {
                ReportItemLight(
                    title = "Koridor Lt. 2 - Lampu Berkedip",
                    desc = "Lampu berkedip terus-menerus. Suku cadang telah dipesan.",
                    status = "DALAM PROSES",
                    statusColor = yellow,
                    time = "Diperbarui 10 menit lalu",
                    user = "Mike R.",
                    actionText = "",
                    actionColor = green,
                    darkText = darkText,
                    grayText = grayText,
                    cardColor = cardColor
                ) {}
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

/* ================= COMPONENT ================= */

@Composable
fun FilterChipLight(
    text: String,
    selected: Boolean,
    dot: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(
                if (selected) Color(0xFFE9F5EE) else Color.Transparent,
                RoundedCornerShape(50)
            )
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(50))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text,
            color = Color(0xFF1E2D28),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
        if (dot) {
            Spacer(Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(Color(0xFF2ECC71), CircleShape)
            )
        }
    }
}

@Composable
fun ReportItemLight(
    title: String,
    desc: String,
    status: String,
    statusColor: Color,
    time: String,
    user: String,
    actionText: String,
    actionColor: Color,
    darkText: Color,
    grayText: Color,
    cardColor: Color,
    onActionClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(cardColor, RoundedCornerShape(20.dp))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(title, color = darkText, fontWeight = FontWeight.Bold)
            Surface(
                color = statusColor.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    status,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = statusColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(Modifier.height(6.dp))

        Text(
            desc,
            color = grayText,
            fontSize = 13.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("$time • $user", color = grayText, fontSize = 12.sp)
            if (actionText.isNotEmpty()) {
                Text(
                    actionText,
                    color = actionColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onActionClick() }
                )
            }
        }
    }
}
