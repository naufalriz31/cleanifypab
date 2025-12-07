package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun UpdateStatusScreen(
    nav: NavHostController,
    roomId: Int
) {
    var selected by remember { mutableStateOf("") }
    var showPopup by remember { mutableStateOf(false) }

    val roomName = "Room #$roomId"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF05150E))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            Spacer(Modifier.height(40.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { nav.popBackStack() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text("Update Status", color = Color.White, fontSize = 22.sp)
            }

            Spacer(Modifier.height(20.dp))

            // ROOM INFO
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0A2018))
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text("Room Name", color = Color(0xFF9BA5A0), fontSize = 14.sp)
                    Text(roomName, color = Color.White, fontSize = 20.sp)
                    Spacer(Modifier.height(12.dp))
                    Text("Last Check: 09:45 AM", color = Color(0xFF6F7B75), fontSize = 14.sp)
                }
            }

            Spacer(Modifier.height(30.dp))

            Text("Select Room Status", color = Color.White, fontSize = 18.sp)
            Spacer(Modifier.height(16.dp))

            StatusOptionCard(
                title = "Cleaned",
                description = "Room has been cleaned properly",
                color = Color(0xFF4CAF50),
                icon = Icons.Default.CheckCircle,
                selected = selected == "cleaned",
                onClick = { selected = "cleaned" }
            )

            StatusOptionCard(
                title = "Not Yet",
                description = "Room has not been cleaned yet",
                color = Color(0xFFFFC107),
                icon = Icons.Default.Close,
                selected = selected == "notyet",
                onClick = { selected = "notyet" }
            )

            StatusOptionCard(
                title = "Needs Cleaning",
                description = "Room requires urgent cleaning",
                color = Color(0xFFE53935),
                icon = Icons.Default.Warning,
                selected = selected == "issue",
                onClick = { selected = "issue" }
            )

            Spacer(Modifier.height(30.dp))

            Button(
                onClick = { if (selected.isNotEmpty()) showPopup = true },
                enabled = selected.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E676))
            ) {
                Text("Submit Status", color = Color.Black, fontSize = 18.sp)
            }
        }

        if (showPopup) {
            SuccessUpdatePopup(
                onFinished = {
                    showPopup = false
                    nav.navigate("home")
                }
            )
        }
    }
}

@Composable
fun StatusOptionCard(
    title: String,
    description: String,
    color: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bg = if (selected) color.copy(alpha = 0.15f) else Color(0xFF0F2A1D)
    val borderColor = if (selected) color else Color(0xFF1E2D24)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, borderColor),
        colors = CardDefaults.cardColors(containerColor = bg)
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = title, tint = color, modifier = Modifier.size(32.dp))
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(title, color = Color.White, fontSize = 18.sp)
                Text(description, color = Color(0xFF9BA5A0), fontSize = 13.sp)
            }
            if (selected) {
                Icon(Icons.Default.CheckCircle, contentDescription = "Selected", tint = color)
            }
        }
    }
}

@Composable
fun SuccessUpdatePopup(onFinished: () -> Unit) {

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        delay(1200)
        visible = false
        delay(250)
        onFinished()
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(250)) + scaleIn(tween(250)),
        exit = fadeOut(tween(200))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x55000000))
        ) {
            Card(
                modifier = Modifier.align(Alignment.Center),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(Color(0xFF0E0F0E))
            ) {
                Column(
                    modifier = Modifier.padding(26.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Success", tint = Color(0xFF00E676), modifier = Modifier.size(80.dp))
                    Spacer(Modifier.height(10.dp))
                    Text("Status Updated!", color = Color.White, fontSize = 22.sp)
                    Text("Saving your report...", color = Color(0xFFB8C3BD), fontSize = 14.sp)
                }
            }
        }
    }
}
