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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cleanfypab.data.model.PetugasFirestore
import com.example.cleanfypab.viewmodel.admin.AdminPetugasViewModel
import androidx.compose.foundation.BorderStroke

@Composable
fun AdminPetugasScreen(
    onAddTask: () -> Unit,                  // ✅ tombol +
    onPetugasClick: (String) -> Unit = {},  // opsional: detail petugas
    vm: AdminPetugasViewModel = viewModel()
) {
    val state by vm.state.collectAsState()

    /* ===== WARNA CLEANIFY ===== */
    val bgGradient = Brush.verticalGradient(listOf(Color(0xFFF6FBF8), Color(0xFFE9F5EE)))
    val card = Color.White
    val borderSoft = Color(0xFFE0E0E0)
    val green = Color(0xFF2ECC71)
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("ALL") } // ALL / ACTIVE / INACTIVE

    LaunchedEffect(Unit) { vm.loadPetugas() }

    val filtered = state.petugas
        .filter { p -> p.name.contains(searchQuery, ignoreCase = true) || p.id.contains(searchQuery, ignoreCase = true) }
        .filter { p ->
            when (selectedFilter) {
                "ACTIVE" -> p.active
                "INACTIVE" -> !p.active
                else -> true
            }
        }

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
            Column {
                Text(
                    "Manajemen Petugas",
                    color = darkText,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Assign tugas ke petugas",
                    color = grayText,
                    fontSize = 13.sp
                )
            }

            FloatingActionButton(
                onClick = onAddTask,
                containerColor = green,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Task", tint = Color.White)
            }
        }

        Spacer(Modifier.height(16.dp))

        /* ================= SEARCH ================= */
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Cari petugas (nama/uid)...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = card,
                unfocusedContainerColor = card,
                focusedBorderColor = green,
                unfocusedBorderColor = borderSoft,
                cursorColor = green
            ),
            singleLine = true
        )

        Spacer(Modifier.height(14.dp))

        /* ================= FILTER ================= */
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            FilterChipLight("Semua", selectedFilter == "ALL", green) { selectedFilter = "ALL" }
            FilterChipLight("Aktif", selectedFilter == "ACTIVE", green) { selectedFilter = "ACTIVE" }
            FilterChipLight("Nonaktif", selectedFilter == "INACTIVE", green) { selectedFilter = "INACTIVE" }
        }

        Spacer(Modifier.height(16.dp))

        /* ================= STATE: LOADING / ERROR ================= */
        if (state.loading) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
            Spacer(Modifier.height(12.dp))
        }

        state.error?.let {
            Text(it, color = Color.Red, fontSize = 13.sp)
            Spacer(Modifier.height(8.dp))
        }

        if (!state.loading && filtered.isEmpty()) {
            Text("Belum ada data petugas.", color = grayText)
            return@Column
        }

        /* ================= LIST PETUGAS ================= */
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(filtered, key = { it.id }) { p ->
                PetugasItemLight(
                    petugas = p,
                    card = card,
                    border = borderSoft,
                    green = green,
                    darkText = darkText,
                    grayText = grayText,
                    onClick = { onPetugasClick(p.id) }
                )
            }
            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

/* ================= KOMPONEN ================= */

@Composable
private fun FilterChipLight(
    label: String,
    selected: Boolean,
    accent: Color,
    onClick: () -> Unit
) {
    Surface(
        color = if (selected) accent.copy(alpha = 0.15f) else Color.Transparent,
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, if (selected) accent else Color(0xFFE0E0E0)),
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            label,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            color = if (selected) accent else Color(0xFF1E2D28),
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun PetugasItemLight(
    petugas: PetugasFirestore,
    card: Color,
    border: Color,
    green: Color,
    darkText: Color,
    grayText: Color,
    onClick: () -> Unit
) {
    val statusColor = if (petugas.active) green else Color(0xFFB0B0B0)
    val statusText = if (petugas.active) "Aktif" else "Nonaktif"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(card, RoundedCornerShape(20.dp))
            .border(1.dp, border, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(Color(0xFFF2F7F4))
                .border(1.dp, border, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = petugas.name.take(1).uppercase(),
                color = darkText,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                petugas.name.ifBlank { "Tanpa Nama" },
                color = darkText,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                "UID: ${petugas.id.take(10)}...",
                color = grayText,
                fontSize = 12.sp
            )
        }

        Surface(
            color = statusColor.copy(alpha = 0.15f),
            shape = RoundedCornerShape(50)
        ) {
            Text(
                statusText,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                color = statusColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
