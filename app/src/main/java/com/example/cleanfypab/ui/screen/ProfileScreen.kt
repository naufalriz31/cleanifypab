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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cleanfypab.ui.navigation.Routes
import androidx.compose.foundation.BorderStroke



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

    val cardSoft = Color(0xFFF2F7F4)
    val borderSoft = Color(0xFFE6EAE8)

    val primaryGreen = Color(0xFF2ECC71)
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    /* ===== layout tokens iOS-like ===== */
    val screenPadding = 16.dp
    val radius = 18.dp
    val sectionGap = 18.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = screenPadding),
    ) {

        Spacer(Modifier.height(12.dp))

        /* ===== HEADER ===== */
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(1.dp, borderSoft, CircleShape)
                    .clickable { nav.popBackStack() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    tint = darkText,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Text(
                text = "Profil",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-0.2).sp,
                color = darkText
            )
        }

        Spacer(Modifier.height(24.dp))

        /* ===== INFORMASI PROFIL ===== */
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(cardSoft)
                    .border(3.dp, primaryGreen, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Foto Profil",
                    tint = grayText,
                    modifier = Modifier.size(56.dp)
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "Raffi Andika Awal",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-0.2).sp,
                color = darkText
            )

            Spacer(Modifier.height(4.dp))

            Text(
                "Petugas Pemeriksa Ruangan",
                fontSize = 13.sp,
                color = grayText
            )

            Spacer(Modifier.height(12.dp))

            Text(
                "Ubah Profil",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = primaryGreen,
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(primaryGreen.copy(alpha = 0.12f))
                    .clickable { nav.navigate(Routes.EDIT_PROFILE) }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            )
        }

        Spacer(Modifier.height(sectionGap))

        /* ===== PENGATURAN ===== */
        SectionTitle("PENGATURAN", grayText)

        Spacer(Modifier.height(10.dp))

        SettingsCardLight(radius = radius, borderSoft = borderSoft) {

            SettingsItemLight(
                icon = Icons.Default.Notifications,
                title = "Notifikasi",
                titleColor = darkText,
                iconTint = primaryGreen,
                trailing = {
                    Switch(
                        checked = notificationEnabled,
                        onCheckedChange = { notificationEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = primaryGreen
                        )
                    )
                }
            )

            Divider(color = borderSoft)

            SettingsItemLight(
                icon = Icons.Default.DarkMode,
                title = "Tampilan (Akan Datang)",
                titleColor = grayText,
                iconTint = grayText,
                trailing = { ArrowIconLight(grayText) }
            )

            Divider(color = borderSoft)

            SettingsItemLight(
                icon = Icons.Default.Language,
                title = "Bahasa (Akan Datang)",
                titleColor = grayText,
                iconTint = grayText,
                trailing = { ArrowIconLight(grayText) }
            )
        }

        Spacer(Modifier.height(sectionGap))

        /* ===== AKUN ===== */
        SectionTitle("AKUN", grayText)

        Spacer(Modifier.height(10.dp))

        SettingsCardLight(radius = radius, borderSoft = borderSoft) {

            SettingsItemLight(
                icon = Icons.Default.Security,
                title = "Kebijakan Privasi",
                titleColor = darkText,
                iconTint = primaryGreen,
                trailing = { ArrowIconLight(grayText) }
            )

            Divider(color = borderSoft)

            SettingsItemLight(
                icon = Icons.Default.Description,
                title = "Syarat & Ketentuan",
                titleColor = darkText,
                iconTint = primaryGreen,
                trailing = { ArrowIconLight(grayText) }
            )
        }

        Spacer(Modifier.height(26.dp))

        /* ===== KELUAR ===== */
        Button(
            onClick = {
                nav.navigate(Routes.LOGIN) {
                    popUpTo(Routes.HOME) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryGreen)
        ) {
            Text(
                "Keluar",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Spacer(Modifier.height(22.dp))
    }
}

/* ================= SMALL UI HELPERS ================= */

@Composable
private fun SectionTitle(text: String, color: Color) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = color,
        letterSpacing = 0.8.sp
    )
}

/* ================= KOMPONEN ================= */

@Composable
fun SettingsCardLight(
    radius: androidx.compose.ui.unit.Dp = 16.dp,
    borderSoft: Color = Color(0xFFE6EAE8),
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(radius),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        border = BorderStroke(1.dp, borderSoft)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 4.dp),
            content = content
        )
    }
}

@Composable
fun SettingsItemLight(
    icon: ImageVector,
    title: String,
    trailing: @Composable (() -> Unit)? = null,
    titleColor: Color,
    iconTint: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = iconTint, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(12.dp))
        Text(
            title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = titleColor,
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
        tint = color,
        modifier = Modifier.size(22.dp)
    )
}
