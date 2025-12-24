package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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

    val bgColor = Color(0xFF0F2A1D)
    val cardColor = Color(0xFF163828)
    val borderColor = Color(0xFF245C3A)
    val green = Color(0xFF2DFF8F)

    var selectedOfficer by remember { mutableStateOf("Ofc. Wick") }
    var notes by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {

        /* ================= CONTENT ================= */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 90.dp) // ruang tombol
                .verticalScroll(rememberScrollState())
        ) {

            /* HEADER */
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.clickable { onClose() }
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    "Assign Room",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(24.dp))

            /* SELECT ROOM */
            Text("Select Room", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("Room Number or Name", color = Color.Gray, fontSize = 12.sp)
            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(cardColor, RoundedCornerShape(16.dp))
                    .border(1.dp, borderColor, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Choose a room...", color = Color.Gray)
                    Icon(Icons.Default.KeyboardArrowDown, null, tint = Color.Gray)
                }
            }

            Spacer(Modifier.height(24.dp))

            /* ASSIGN PERSONNEL */
            Text("Assign Personnel", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                leadingIcon = { Icon(Icons.Default.Search, null) },
                placeholder = { Text("Search officer by name...") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = cardColor,
                    unfocusedContainerColor = cardColor,
                    focusedBorderColor = green,
                    unfocusedBorderColor = borderColor,
                    cursorColor = green
                ),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(Modifier.height(16.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                item {
                    OfficerChip("Ofc. Sarah", selectedOfficer == "Ofc. Sarah", green) {
                        selectedOfficer = "Ofc. Sarah"
                    }
                }
                item {
                    OfficerChip("Ofc. Wick", selectedOfficer == "Ofc. Wick", green) {
                        selectedOfficer = "Ofc. Wick"
                    }
                }
                item {
                    OfficerChip("Ofc. Bob", selectedOfficer == "Ofc. Bob", green) {
                        selectedOfficer = "Ofc. Bob"
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            /* NOTES */
            Text("Additional Notes", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                placeholder = {
                    Text("Enter any special instructions or equipment needs...")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = cardColor,
                    unfocusedContainerColor = cardColor,
                    focusedBorderColor = green,
                    unfocusedBorderColor = borderColor,
                    cursorColor = green
                ),
                shape = RoundedCornerShape(16.dp)
            )
        }

        /* ================= CONFIRM BUTTON ================= */
        Button(
            onClick = {
                // AKSI CONFIRM
                println("ASSIGNMENT CONFIRMED")
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
                "Confirm Assignment",
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
                .background(accent, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Person, null, tint = Color.Black)
        }
        Spacer(Modifier.width(8.dp))
        Text(name, color = Color.White)
    }
}
