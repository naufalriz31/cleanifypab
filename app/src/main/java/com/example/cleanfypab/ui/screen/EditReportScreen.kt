package com.example.cleanfypab.ui.screen

import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Photo
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
fun EditReportScreen(
    nav: NavHostController,
    roomId: Int
) {
    val roomName = "Room #$roomId"

    var photoAttached by remember { mutableStateOf(false) }
    var notes by remember { mutableStateOf("") }
    var showPopup by remember { mutableStateOf(false) }

    val checklistItems = listOf(
        "Sweep the floor",
        "Mop the area",
        "Clean the windows",
        "Empty the trash bin",
        "Disinfect the surfaces"
    )

    val checklistState = remember {
        mutableStateListOf(false, false, false, false, false)
    }

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

                Text(
                    "Edit Report",
                    color = Color.White,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

            ReportRoomInfoCard(roomName)

            Spacer(Modifier.height(26.dp))

            Text("Upload Photo (optional)", color = Color.White, fontSize = 16.sp)
            Spacer(Modifier.height(12.dp))

            UploadPhotoSection(
                photoAttached = photoAttached,
                onPhotoAdded = { photoAttached = true }
            )

            Spacer(Modifier.height(26.dp))

            Text("Checklist", color = Color.White, fontSize = 16.sp)
            Spacer(Modifier.height(12.dp))

            checklistItems.forEachIndexed { index, item ->
                ChecklistRow(
                    text = item,
                    checked = checklistState[index],
                    onCheckedChange = { checklistState[index] = !checklistState[index] }
                )
                Spacer(Modifier.height(10.dp))
            }

            Spacer(Modifier.height(26.dp))

            Text("Additional Notes", color = Color.White, fontSize = 16.sp)
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF0F2A1D),
                    unfocusedContainerColor = Color(0xFF0F2A1D),
                    cursorColor = Color(0xFF00E676),
                    focusedIndicatorColor = Color(0xFF00E676),
                    unfocusedIndicatorColor = Color(0xFF445A50)
                )
            )

            Spacer(Modifier.height(30.dp))

            Button(
                onClick = { showPopup = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00E676)
                )
            ) {
                Text("Submit Report", color = Color.Black, fontSize = 18.sp)
            }
        }

        if (showPopup) {
            ReportSuccessPopup(
                onFinished = {
                    showPopup = false
                    nav.navigate("home")
                }
            )
        }
    }
}

@Composable
fun ReportRoomInfoCard(roomName: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0A2018))
    ) {
        Column(Modifier.padding(20.dp)) {
            Text("Room Name", color = Color(0xFF9BA5A0), fontSize = 14.sp)
            Text(roomName, color = Color.White, fontSize = 20.sp)
            Spacer(Modifier.height(10.dp))
            Text("Assigned at: 10:45 AM", color = Color(0xFF6F7B75), fontSize = 14.sp)
        }
    }
}

@Composable
fun UploadPhotoSection(photoAttached: Boolean, onPhotoAdded: () -> Unit) {
    if (!photoAttached) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF11271D))
                .clickable { onPhotoAdded() },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Default.AddAPhoto,
                    contentDescription = "Add Photo",
                    tint = Color(0xFF00E676),
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.height(8.dp))
                Text("Tap to upload photo", color = Color(0xFF9BA5A0))
            }
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
            Icon(Icons.Default.Photo, contentDescription = "Photo", tint = Color.White)
        }
    }
}

@Composable
fun ChecklistRow(text: String, checked: Boolean, onCheckedChange: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange() }
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange() },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF00E676),
                uncheckedColor = Color.White
            )
        )
        Text(text, color = Color.White, fontSize = 15.sp)
    }
}

@Composable
fun ReportSuccessPopup(onFinished: () -> Unit) {
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
                        Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = Color(0xFF00E676),
                        modifier = Modifier.size(80.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        "Report Submitted!",
                        color = Color.White,
                        fontSize = 22.sp
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        "Your cleaning report has been saved.",
                        color = Color(0xFFB8C3BD),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
