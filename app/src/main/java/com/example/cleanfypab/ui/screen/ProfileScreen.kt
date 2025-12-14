package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cleanfypab.ui.navigation.Routes

// Theme colors
val BG_DARK = Color(0xFF071A12)
val CARD_DARK = Color(0xFF0F2A1D)
val GREEN_ACCENT = Color(0xFF00E676)
val GRAY_TEXT = Color(0xFF9BA5A0)
val WHITE = Color.White

@Composable
fun ProfileScreen(nav: NavHostController) {

    var notificationEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BG_DARK)
            .padding(16.dp)
    ) {

        // ==== TOP HEADER ====
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = WHITE,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { nav.popBackStack() }
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                "Profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = WHITE
            )
        }

        Spacer(Modifier.height(28.dp))

        // ==== PROFILE PHOTO ====
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(CARD_DARK)
                    .border(4.dp, GREEN_ACCENT, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    modifier = Modifier.size(70.dp),
                    contentDescription = "Profile Photo",
                    tint = GRAY_TEXT
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "Raffi Andika Awal",
                fontSize = 26.sp,
                color = WHITE,
                fontWeight = FontWeight.Bold
            )

            Text(
                "Room Inspector",
                color = GRAY_TEXT,
                fontSize = 16.sp
            )

            Spacer(Modifier.height(10.dp))

            Text(
                "Edit Profile",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = GREEN_ACCENT,
                modifier = Modifier.clickable { }
            )
        }

        Spacer(Modifier.height(28.dp))

        // ==== SETTINGS TITLE ====
        Text(
            "SETTINGS",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = GRAY_TEXT,
            modifier = Modifier.padding(start = 4.dp)
        )

        Spacer(Modifier.height(10.dp))

        SettingsCard {
            SettingsItem(
                icon = Icons.Default.Notifications,
                title = "Notifications",
                trailing = {
                    Switch(
                        checked = notificationEnabled,
                        onCheckedChange = { notificationEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = WHITE,
                            checkedTrackColor = GREEN_ACCENT
                        )
                    )
                }
            )

            Divider(color = Color(0xFF173327))

            SettingsItem(
                icon = Icons.Default.DarkMode,
                title = "Appearance",
                trailing = { ArrowIcon() }
            )

            Divider(color = Color(0xFF173327))

            SettingsItem(
                icon = Icons.Default.Language,
                title = "Language",
                trailing = { ArrowIcon() }
            )
        }

        Spacer(Modifier.height(28.dp))

        // ==== ACCOUNT TITLE ====
        Text(
            "ACCOUNT",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = GRAY_TEXT,
            modifier = Modifier.padding(start = 4.dp)
        )

        Spacer(Modifier.height(10.dp))

        SettingsCard {

            SettingsItem(
                icon = Icons.Default.Lock,
                title = "Change Password",
                trailing = { ArrowIcon() }
            )

            Divider(color = Color(0xFF173327))

            SettingsItem(
                icon = Icons.Default.Security,
                title = "Privacy Policy",
                trailing = { ArrowIcon() }
            )

            Divider(color = Color(0xFF173327))

            SettingsItem(
                icon = Icons.Default.Description,
                title = "Terms of Service",
                trailing = { ArrowIcon() }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // ==== LOGOUT BUTTON ====
        Button(
            onClick = {
                nav.navigate(Routes.LOGIN) {
                    popUpTo(Routes.HOME) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GREEN_ACCENT)
        ) {
            Text("Log Out", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = BG_DARK)
        }
    }
}

@Composable
fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CARD_DARK, RoundedCornerShape(16.dp))
            .padding(4.dp),
        content = content
    )
}

@Composable
fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(icon, contentDescription = title, tint = GREEN_ACCENT, modifier = Modifier.size(22.dp))

        Spacer(modifier = Modifier.width(16.dp))

        Text(title, fontSize = 18.sp, color = WHITE, modifier = Modifier.weight(1f))

        trailing?.invoke()
    }
}

@Composable
fun ArrowIcon() {
    Icon(
        Icons.Default.KeyboardArrowRight,
        contentDescription = "Next",
        tint = GRAY_TEXT
    )
}
