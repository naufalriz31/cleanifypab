package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Work
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
fun AdminPetugasScreen(
    onAddPetugas: () -> Unit
) {

    /* ===== WARNA CLEANIFY ===== */
    val bgGradient = Brush.verticalGradient(
        listOf(
            Color(0xFFF6FBF8),
            Color(0xFFE9F5EE)
        )
    )

    val green = Color(0xFF2ECC71)
    val blue = Color(0xFF3498DB)
    val yellow = Color(0xFFF1C40F)

    val cardColor = Color.White
    val borderSoft = Color(0xFFE0E0E0)
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    var search by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("ALL") }

    val petugasList = listOf(
        Petugas("Ahmad Fauzi", "Petugas Kebersihan", "Aktif"),
        Petugas("Rina Aulia", "Supervisor", "Bertugas"),
        Petugas("Budi Santoso", "Petugas Kebersihan", "Cuti")
    )

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
                "Manajemen Petugas",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = darkText
            )

            FloatingActionButton(
                onClick = onAddPetugas,
                containerColor = green,
                modifier = Modifier.size(48.dp),
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, null, tint = Color.White)
            }
        }

        Spacer(Modifier.height(16.dp))

        /* ================= SEARCH ================= */
        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            placeholder = { Text("Cari nama atau role petugas...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = cardColor,
                unfocusedContainerColor = cardColor,
                focusedBorderColor = green,
                unfocusedBorderColor = borderSoft,
                cursorColor = green
            )
        )

        Spacer(Modifier.height(20.dp))

        /* ================= OVERVIEW (SAMA DENGAN ROOMS) ================= */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OverviewCardPetugas(
                modifier = Modifier.weight(1f),
                value = "15",
                label = "Total Petugas",
                icon = Icons.Default.Person,
                accent = green
            )
            OverviewCardPetugas(
                modifier = Modifier.weight(1f),
                value = "10",
                label = "Aktif",
                icon = Icons.Default.Person,
                accent = green
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OverviewCardPetugas(
                modifier = Modifier.weight(1f),
                value = "3",
                label = "Bertugas",
                icon = Icons.Default.Work,
                accent = blue
            )
            OverviewCardPetugas(
                modifier = Modifier.weight(1f),
                value = "2",
                label = "Cuti",
                icon = Icons.Default.Person,
                accent = yellow
            )
        }

        Spacer(Modifier.height(20.dp))

        /* ================= FILTER ================= */
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            FilterChipPetugas("ALL", "Semua", selectedFilter, green) { selectedFilter = "ALL" }
            FilterChipPetugas("AKTIF", "Aktif", selectedFilter, green) { selectedFilter = "AKTIF" }
            FilterChipPetugas("BERTUGAS", "Bertugas", selectedFilter, green) { selectedFilter = "BERTUGAS" }
            FilterChipPetugas("CUTI", "Cuti", selectedFilter, green) { selectedFilter = "CUTI" }
        }

        Spacer(Modifier.height(16.dp))

        /* ================= LIST ================= */
        LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            items(petugasList) { petugas ->
                PetugasItemLight(petugas, cardColor, darkText, grayText)
            }
            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

/* ================= KOMPONEN ================= */

@Composable
fun OverviewCardPetugas(
    modifier: Modifier,
    value: String,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accent: Color
) {
    Row(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(18.dp))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(18.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(label, color = accent, fontSize = 13.sp)
        }
        Icon(icon, null, tint = accent)
    }
}

@Composable
fun FilterChipPetugas(
    filter: String,
    label: String,
    selected: String,
    accent: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .border(1.dp, accent, RoundedCornerShape(50))
            .background(
                if (selected == filter) accent.copy(alpha = 0.15f) else Color.Transparent,
                RoundedCornerShape(50)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            label,
            color = if (selected == filter) accent else Color(0xFF1E2D28),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
fun PetugasItemLight(
    petugas: Petugas,
    card: Color,
    darkText: Color,
    grayText: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(card, RoundedCornerShape(20.dp))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(20.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {
            Text(petugas.nama, fontWeight = FontWeight.Bold, color = darkText)
            Text(petugas.role, fontSize = 13.sp, color = grayText)
        }

        StatusBadge(petugas.status)
    }
}

@Composable
fun StatusBadge(status: String) {
    val (bg, text) = when (status) {
        "Aktif" -> Color(0xFFE8F8F0) to Color(0xFF2ECC71)
        "Bertugas" -> Color(0xFFEAF2FB) to Color(0xFF3498DB)
        else -> Color(0xFFFFF6E5) to Color(0xFFF1C40F)
    }

    Box(
        modifier = Modifier
            .background(bg, RoundedCornerShape(50))
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(status, color = text, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}

/* ================= MODEL ================= */

data class Petugas(
    val nama: String,
    val role: String,
    val status: String
)
