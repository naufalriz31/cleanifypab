package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
    val green = Color(0xFF2DFF8F)
    val borderColor = Color(0xFF245C3A)

    var selectedOfficer by remember { mutableStateOf("Ofc. Wick") }
    var scheduleType by remember { mutableStateOf("ONCE") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(16.dp)
    ) {

        /* HEADER */
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.Close,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable { onClose() }
            )
            Spacer(Modifier.width(16.dp))
            Text("Assign Room", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
            item { OfficerChip("Ofc. Sarah", selectedOfficer == "Ofc. Sarah", green) { selectedOfficer = "Ofc. Sarah" } }
            item { OfficerChip("Ofc. Wick", selectedOfficer == "Ofc. Wick", green) { selectedOfficer = "Ofc. Wick" } }
            item { OfficerChip("Ofc. Bob", selectedOfficer == "Ofc. Bob", green) { selectedOfficer = "Ofc. Bob" } }
        }

        Spacer(Modifier.height(24.dp))

        /* SCHEDULE */
        Text("Schedule", color = Color.White, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .background(cardColor, RoundedCornerShape(50))
                .padding(4.dp)
        ) {
            ScheduleChip("Once", scheduleType == "ONCE", green) { scheduleType = "ONCE" }
            ScheduleChip("Recurring", scheduleType == "RECURRING", green) { scheduleType = "RECURRING" }
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DateBox("Start Date", "10/27/2023, 09:00", cardColor, borderColor)
            DateBox("End Date", "10/27/2023, 05:00", cardColor, borderColor)
        }

        Spacer(Modifier.height(24.dp))

        /* NOTES */
        Text("Additional Notes", color = Color.White, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Enter any special instructions or equipment needs...") },
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

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = green),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.CheckCircle, null, tint = Color.Black)
            Spacer(Modifier.width(8.dp))
            Text("Confirm Assignment", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

/* COMPONENTS */

@Composable
fun OfficerChip(name: String, selected: Boolean, accent: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .background(if (selected) accent.copy(alpha = 0.15f) else Color.Transparent, RoundedCornerShape(50))
            .border(1.dp, if (selected) accent else Color.Gray, RoundedCornerShape(50))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(32.dp).background(accent, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Person, null, tint = Color.Black)
        }
        Spacer(Modifier.width(8.dp))
        Text(name, color = if (selected) accent else Color.White)
    }
}

@Composable
fun ScheduleChip(text: String, selected: Boolean, accent: Color, onClick: () -> Unit) {
    Text(
        text = text,
        modifier = Modifier
            .background(if (selected) accent else Color.Transparent, RoundedCornerShape(50))
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 10.dp),
        color = if (selected) Color.Black else Color.White,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun DateBox(label: String, value: String, cardColor: Color, borderColor: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(cardColor, RoundedCornerShape(16.dp))
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Text(label, color = Color.Gray, fontSize = 12.sp)
        Spacer(Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(value, color = Color.White, fontSize = 12.sp)
            Icon(Icons.Default.CalendarToday, null, tint = Color.Gray)
        }
    }
}
