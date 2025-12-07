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
        "QR Code Missing",
        "QR Code Damaged",
        "Unexpected Cleaning Task",
        "Emergency Cleaning Required"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF05150E))
            .padding(20.dp)
    ) {

        Spacer(Modifier.height(40.dp))

        // -------------------------------
        // HEADER
        // -------------------------------
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                "Manual Report",
                color = Color.White,
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        // -------------------------------
        // INPUT ROOM NAME
        // -------------------------------
        Text("Room Name", color = Color.White, fontSize = 16.sp)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = roomName,
            onValueChange = { roomName = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter room name...", color = Color.LightGray) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF0F2A1D),
                unfocusedContainerColor = Color(0xFF0F2A1D),
                focusedIndicatorColor = Color(0xFF00E676),
                unfocusedIndicatorColor = Color(0xFF445A50),
                cursorColor = Color(0xFF00E676),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(Modifier.height(24.dp))

        // -------------------------------
        // CATEGORY DROPDOWN
        // -------------------------------
        Text("Report Category", color = Color.White, fontSize = 16.sp)
        Spacer(Modifier.height(8.dp))

        var expanded by remember { mutableStateOf(false) }

        Box {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(0xFF0F2A1D),
                    contentColor = Color.White
                )
            ) {
                Text(if (category.isEmpty()) "Choose category..." else category)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = Color(0xFF0F2A1D)
            ) {
                categories.forEach {
                    DropdownMenuItem(
                        text = { Text(it, color = Color.White) },
                        onClick = {
                            category = it
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // -------------------------------
        // PHOTO SECTION
        // -------------------------------
        Text("Upload Photo (optional)", color = Color.White, fontSize = 16.sp)
        Spacer(Modifier.height(12.dp))

        if (!photoAttached) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF11271D))
                    .clickable { photoAttached = true },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.AddAPhoto,
                    contentDescription = "Add Photo",
                    tint = Color(0xFF00E676),
                    modifier = Modifier.size(40.dp)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1D2E24)),
                contentAlignment = Alignment.Center
            ) {
                Text("Photo Attached", color = Color.White)
            }
        }

        Spacer(Modifier.height(24.dp))

        // -------------------------------
        // NOTES
        // -------------------------------
        Text("Additional Notes", color = Color.White, fontSize = 16.sp)
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            placeholder = { Text("Write message...", color = Color.LightGray) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF0F2A1D),
                unfocusedContainerColor = Color(0xFF0F2A1D),
                cursorColor = Color(0xFF00E676),
                focusedIndicatorColor = Color(0xFF00E676),
                unfocusedIndicatorColor = Color(0xFF445A50),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(Modifier.height(30.dp))

        // -------------------------------
        // SUBMIT
        // -------------------------------
        Button(
            onClick = {
                if (roomName.isNotEmpty() && category.isNotEmpty()) {
                    showPopup = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E676)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Submit Manual Report", color = Color.Black, fontSize = 18.sp)
        }
    }

    // Popup
    if (showPopup) {
        ManualReportSuccessPopup {
            showPopup = false
            nav.navigate("home")
        }
    }
}

@Composable
fun ManualReportSuccessPopup(onFinished: () -> Unit) {
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
                    .background(Color(0xFF0E0F0E), RoundedCornerShape(20.dp))
                    .padding(26.dp)
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Icon(
                        Icons.Default.AddAPhoto,
                        contentDescription = "Done",
                        tint = Color(0xFF00E676),
                        modifier = Modifier.size(70.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    Text("Manual Report Sent!", color = Color.White, fontSize = 22.sp)

                    Spacer(Modifier.height(6.dp))

                    Text(
                        "Your manual cleaning report has been saved.",
                        color = Color(0xFFB8C3BD),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
