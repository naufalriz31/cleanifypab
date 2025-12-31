package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cleanfypab.ui.navigation.Routes

@Composable
fun ProfileScreen(nav: NavHostController) {

    var notificationEnabled by remember { mutableStateOf(true) }

    /* ===== PALET WARNA CLEANIFY (LIGHT) ===== */
    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF6FBF8),
            Color(0xFFE9F5EE)
        )
    )

    val card = Color.White
    val cardSoft = Color(0xFFF2F7F4)
    val borderSoft = Color(0xFFE0E0E0)

    val primaryGreen = Color(0xFF2ECC71)
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        /* ===== HEADER ===== */
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Kembali",
                tint = darkText,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { nav.popBackStack() }
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = "Profil",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = darkText
            )
        }

        Spacer(Modifier.height(28.dp))

        /* ===== INFORMASI PROFIL ===== */
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(cardSoft)
                    .border(4.dp, primaryGreen, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Foto Profil",
                    tint = grayText,
                    modifier = Modifier.size(70.dp)
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "Raffi Andika Awal",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = darkText
            )

            Text(
                "Petugas Pemeriksa Ruangan",
                fontSize = 16.sp,
                color = grayText
            )

            Spacer(Modifier.height(10.dp))

            Text(
                "Ubah Profil",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = primaryGreen,
                modifier = Modifier.clickable {
                    nav.navigate(Routes.EDIT_PROFILE)
                }
            )
        }

        Spacer(Modifier.height(28.dp))

        /* ===== PENGATURAN ===== */
        Text(
            "PENGATURAN",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = grayText
        )

        Spacer(Modifier.height(10.dp))

        SettingsCardLight {
            SettingsItemLight(
                icon = Icons.Default.Notifications,
                title = "Notifikasi",
                trailing = {
                    Switch(
                        checked = notificationEnabled,
                        onCheckedChange = { notificationEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = primaryGreen
                        )
                    )
                },
                primaryGreen = primaryGreen,
                darkText = darkText
            )

            Divider(color = borderSoft)

            SettingsItemLight(
                icon = Icons.Default.DarkMode,
                title = "Tampilan",
                trailing = { ArrowIconLight(grayText) },
                primaryGreen = primaryGreen,
                darkText = darkText
            )

            Divider(color = borderSoft)

            SettingsItemLight(
                icon = Icons.Default.Language,
                title = "Bahasa",
                trailing = { ArrowIconLight(grayText) },
                primaryGreen = primaryGreen,
                darkText = darkText
            )
        }

        Spacer(Modifier.height(28.dp))

        /* ===== AKUN ===== */
        Text(
            "AKUN",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = grayText
        )

        Spacer(Modifier.height(10.dp))

        SettingsCardLight {
            SettingsItemLight(
                icon = Icons.Default.Lock,
                title = "Ganti Kata Sandi",
                trailing = { ArrowIconLight(grayText) },
                primaryGreen = primaryGreen,
                darkText = darkText
            )

            Divider(color = borderSoft)

            SettingsItemLight(
                icon = Icons.Default.Security,
                title = "Kebijakan Privasi",
                trailing = { ArrowIconLight(grayText) },
                primaryGreen = primaryGreen,
                darkText = darkText
            )

            Divider(color = borderSoft)

            SettingsItemLight(
                icon = Icons.Default.Description,
                title = "Syarat & Ketentuan",
                trailing = { ArrowIconLight(grayText) },
                primaryGreen = primaryGreen,
                darkText = darkText
            )
        }

        Spacer(Modifier.height(32.dp))

        /* ===== KELUAR ===== */
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
            colors = ButtonDefaults.buttonColors(containerColor = primaryGreen)
        ) {
            Text(
                "Keluar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(Modifier.height(20.dp))
    }
}

/* ================= KOMPONEN (LIGHT) ================= */

@Composable
fun SettingsCardLight(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(16.dp))
            .padding(4.dp),
        content = content
    )
}

@Composable
fun SettingsItemLight(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    trailing: @Composable (() -> Unit)? = null,
    primaryGreen: Color,
    darkText: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = primaryGreen)
        Spacer(Modifier.width(16.dp))
        Text(
            title,
            fontSize = 18.sp,
            color = darkText,
            modifier = Modifier.weight(1f)
        )
        trailing?.invoke()
    }
}

@Composable
fun ArrowIconLight(color: Color) {
    Icon(
        Icons.AutoMirrored.Filled.KeyboardArrowRight,
        contentDescription = "Lanjut",
        tint = color
    )
}
