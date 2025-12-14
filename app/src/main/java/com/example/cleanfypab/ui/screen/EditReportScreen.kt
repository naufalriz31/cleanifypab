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
        "Restocked mini-bar",
        "Changed bed linens",
        "Cleaned bathroom surfaces",
        "Checked smoke detector"
    )

    var selectedChecklist = remember {
        mutableStateOf(mutableSetOf("Cleaned bathroom surfaces"))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A1C12))
            .padding(16.dp)
    ) {

        // TOP BAR
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { nav.popBackStack() }
            )

            Spacer(Modifier.width(16.dp))

            Text(
                "Edit Report",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.weight(1f))

            Text(
                "Save",
                fontSize = 18.sp,
                color = Color(0xFF00E676),
                modifier = Modifier.clickable { nav.popBackStack() }
            )
        }

        Spacer(Modifier.height(24.dp))

        // TITLE
        Text(
            "Room 301 - Cleaning Report",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(Modifier.height(6.dp))

        Text(
            "Created: Jan 24, 2024 at 10:32 AM",
            color = Color(0xFF4CAF50),
            fontSize = 14.sp
        )

        Spacer(Modifier.height(20.dp))

        // Photos Section
        Text("Photos", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

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
                        contentDescription = "Photo",
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
                            contentDescription = "Add Photo",
                            tint = Color.Gray,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text("Add Photo", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // Notes Section
        Text("Notes", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))

        TextField(
            value = notes,
            onValueChange = { notes = it },
            placeholder = { Text("Add any relevant details...", color = Color.Gray) },
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

        // Checklist Section
        Text("Optional Checklist", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(12.dp))

        checklistItems.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (selectedChecklist.value.contains(item)) {
                            selectedChecklist.value.remove(item)
                        } else selectedChecklist.value.add(item)
                    }
                    .padding(vertical = 10.dp)
            ) {
                Checkbox(
                    checked = selectedChecklist.value.contains(item),
                    onCheckedChange = {
                        if (selectedChecklist.value.contains(item)) {
                            selectedChecklist.value.remove(item)
                        } else selectedChecklist.value.add(item)
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
            Text("Save Changes", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}
