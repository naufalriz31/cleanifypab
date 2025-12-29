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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cleanfypab.ui.navigation.Routes

/* ================= WARNA ================= */
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
            .verticalScroll(rememberScrollState()) // ✅ FIX SCROLL
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
                tint = WHITE,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { nav.popBackStack() }
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = "Profil",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = WHITE
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
                    .background(CARD_DARK)
                    .border(4.dp, GREEN_ACCENT, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Foto Profil",
                    tint = GRAY_TEXT,
                    modifier = Modifier.size(70.dp)
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "Raffi Andika Awal",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = WHITE
            )

            Text(
                "Petugas Pemeriksa Ruangan",
                fontSize = 16.sp,
                color = GRAY_TEXT
            )

            Spacer(Modifier.height(10.dp))

            Text(
                "Ubah Profil",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = GREEN_ACCENT,
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
            color = GRAY_TEXT
        )

        Spacer(Modifier.height(10.dp))

        SettingsCard {
            SettingsItem(
                icon = Icons.Default.Notifications,
                title = "Notifikasi",
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
                title = "Tampilan",
                trailing = { ArrowIcon() }
            )

            Divider(color = Color(0xFF173327))

            SettingsItem(
                icon = Icons.Default.Language,
                title = "Bahasa",
                trailing = { ArrowIcon() }
            )
        }

        Spacer(Modifier.height(28.dp))

        /* ===== AKUN ===== */
        Text(
            "AKUN",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = GRAY_TEXT
        )

        Spacer(Modifier.height(10.dp))

        SettingsCard {
            SettingsItem(
                icon = Icons.Default.Lock,
                title = "Ganti Kata Sandi",
                trailing = { ArrowIcon() }
            )

            Divider(color = Color(0xFF173327))

            SettingsItem(
                icon = Icons.Default.Security,
                title = "Kebijakan Privasi",
                trailing = { ArrowIcon() }
            )

            Divider(color = Color(0xFF173327))

            SettingsItem(
                icon = Icons.Default.Description,
                title = "Syarat & Ketentuan",
                trailing = { ArrowIcon() }
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
            colors = ButtonDefaults.buttonColors(containerColor = GREEN_ACCENT)
        ) {
            Text(
                "Keluar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = BG_DARK
            )
        }

        Spacer(Modifier.height(20.dp))
    }
}

/* ================= KOMPONEN ================= */

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
        Icon(icon, contentDescription = title, tint = GREEN_ACCENT)
        Spacer(Modifier.width(16.dp))
        Text(title, fontSize = 18.sp, color = WHITE, modifier = Modifier.weight(1f))
        trailing?.invoke()
    }
}

@Composable
fun ArrowIcon() {
    Icon(
        Icons.AutoMirrored.Filled.KeyboardArrowRight,
        contentDescription = "Lanjut",
        tint = GRAY_TEXT
    )
}
