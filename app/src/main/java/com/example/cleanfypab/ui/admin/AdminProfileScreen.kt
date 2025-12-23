package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminProfileScreen(
    onBack: () -> Unit = {},
    onEdit: () -> Unit = {}
) {

    val bgColor = Color(0xFF0F2A1D)
    val cardColor = Color(0xFF163828)
    val green = Color(0xFF2DFF8F)
    val red = Color(0xFFFF5C5C)
    val gray = Color(0xFF9E9E9E)

    var darkMode by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(16.dp)
    ) {

        /* HEADER */
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Text(
                "Profile",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            TextButton(onClick = onEdit) {
                Text("Edit", color = green, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(Modifier.height(24.dp))

        /* PROFILE INFO */
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.DarkGray, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, null, tint = Color.White, modifier = Modifier.size(48.dp))
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(32.dp)
                        .background(green, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Edit, null, tint = Color.Black, modifier = Modifier.size(16.dp))
                }
            }

            Spacer(Modifier.height(16.dp))

            Text("Jane Doe", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)

            Surface(color = green.copy(alpha = 0.2f), shape = RoundedCornerShape(50)) {
                Text(
                    "SUPER ADMIN",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    color = green,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(8.dp))

            Text("jane.doe@company.com", color = gray, fontSize = 13.sp)
            Text("Senior Facility Manager", color = gray, fontSize = 13.sp)
        }

        Spacer(Modifier.height(24.dp))

        /* STATS (TANPA WEIGHT) */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileStat("12", "PENDING TASKS", cardColor, green)
            ProfileStat("45", "ROOMS MANAGED", cardColor, green)
            ProfileStat("98%", "RESPONSE RATE", cardColor, green)
        }

        Spacer(Modifier.height(32.dp))

        SectionTitle("ACCOUNT SETTINGS")
        SettingsItem(Icons.Default.Lock, "Account Security", "Password, 2FA", cardColor, green)
        SettingsItem(Icons.Default.Notifications, "Notifications", "Push, Email, SMS", cardColor, green)

        Spacer(Modifier.height(24.dp))

        SectionTitle("APP SETTINGS")
        SettingsItem(
            icon = Icons.Default.DarkMode,
            title = "Appearance",
            subtitle = "Dark Mode Active",
            cardColor = cardColor,
            accent = green,
            trailing = {
                Switch(
                    checked = darkMode,
                    onCheckedChange = { darkMode = it },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = green,
                        checkedThumbColor = Color.White
                    )
                )
            }
        )

        SettingsItem(Icons.Default.Language, "Language", "English (US)", cardColor, green)

        Spacer(Modifier.height(24.dp))

        SectionTitle("SUPPORT")
        SettingsItem(Icons.AutoMirrored.Filled.Help, "Help Center", "", cardColor, green)
        SettingsItem(Icons.Default.BugReport, "Report a Bug", "", cardColor, green)

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(1.dp, red),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.Logout, null, tint = red)
            Spacer(Modifier.width(8.dp))
            Text("Sign Out", color = red, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(16.dp))

        Text(
            "Version 1.0.2 (Build 240)",
            color = gray,
            fontSize = 11.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

/* COMPONENTS */

@Composable
fun ProfileStat(value: String, label: String, cardColor: Color, accent: Color) {
    Column(
        modifier = Modifier
            .background(cardColor, RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text(label, color = accent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(title, color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    cardColor: Color,
    accent: Color,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(cardColor, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(accent.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = accent)
            }

            Spacer(Modifier.width(12.dp))

            Column {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold)
                if (subtitle.isNotEmpty()) {
                    Text(subtitle, color = Color.Gray, fontSize = 12.sp)
                }
            }
        }

        trailing?.invoke() ?: Icon(Icons.Default.ChevronRight, null, tint = Color.Gray)
    }
}
