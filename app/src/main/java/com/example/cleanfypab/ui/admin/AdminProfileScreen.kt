package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
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

@Composable
fun AdminProfileScreen(
    onBack: () -> Unit = {},
    onEdit: () -> Unit = {},
    onSignOut: () -> Unit = {}
) {

    /* ===== CLEANIFY LIGHT PALET ===== */
    val bgGradient = Brush.verticalGradient(
        listOf(
            Color(0xFFF6FBF8),
            Color(0xFFE9F5EE)
        )
    )

    val cardColor = Color.White
    val borderSoft = Color(0xFFE0E0E0)

    val green = Color(0xFF2ECC71)
    val red = Color(0xFFFF6B6B)

    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {

        Spacer(Modifier.height(12.dp))

        /* ================= HEADER (TETAP) ================= */
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    tint = darkText
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                "Profil",
                color = darkText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.weight(1f))

            Text(
                "Edit",
                color = green,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onEdit() }
            )
        }

        Spacer(Modifier.height(28.dp))

        /* ================= AVATAR (CENTER FIX – ISI SAMA) ================= */
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(contentAlignment = Alignment.BottomEnd) {

                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE9F5EE))
                            .padding(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(cardColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Person,
                                null,
                                tint = darkText,
                                modifier = Modifier.size(56.dp)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(green),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    "Jane Doe",
                    color = darkText,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(6.dp))

                Surface(
                    color = green.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        "SUPER ADMIN",
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        color = green,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text("jane.doe@company.com", color = grayText, fontSize = 13.sp)
                Text("Manajer Fasilitas Senior", color = grayText, fontSize = 13.sp)
            }
        }

        Spacer(Modifier.height(28.dp))

        /* ================= STATS (TETAP) ================= */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatBoxLight("12", "TUGAS\nMENUNGGU", cardColor, green, darkText)
            StatBoxLight("30", "RUANGAN\nDIKELOLA", cardColor, green, darkText)
            StatBoxLight("98%", "TINGKAT\nRESPON", cardColor, green, darkText)
        }

        Spacer(Modifier.height(32.dp))

        SectionTitleLight("PENGATURAN AKUN", grayText)
        MenuItemLight(Icons.Default.Lock, "Keamanan Akun", "Kata sandi, 2FA", cardColor, darkText, grayText)
        MenuItemLight(Icons.Default.Notifications, "Notifikasi", "Push, Email, SMS", cardColor, darkText, grayText)

        Spacer(Modifier.height(24.dp))

        SectionTitleLight("PENGATURAN APLIKASI", grayText)
        ToggleItemLight(Icons.Default.DarkMode, "Tampilan", "Mode Gelap Aktif", cardColor, green, darkText, grayText)
        MenuItemLight(Icons.Default.Language, "Bahasa", "Bahasa Indonesia", cardColor, darkText, grayText)

        Spacer(Modifier.height(24.dp))

        SectionTitleLight("DUKUNGAN", grayText)
        MenuItemLight(Icons.Default.Help, "Pusat Bantuan", null, cardColor, darkText, grayText)
        MenuItemLight(Icons.Default.BugReport, "Laporkan Bug", null, cardColor, darkText, grayText)

        Spacer(Modifier.height(28.dp))

        OutlinedButton(
            onClick = onSignOut,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            border = BorderStroke(1.dp, red),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = red)
        ) {
            Icon(Icons.AutoMirrored.Filled.Logout, null)
            Spacer(Modifier.width(8.dp))
            Text("Keluar", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(16.dp))

        Text(
            "Versi 1.0.2 (Build 240)",
            color = grayText,
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(24.dp))
    }
}

/* ================= COMPONENTS (TETAP) ================= */

@Composable
private fun StatBoxLight(
    value: String,
    label: String,
    card: Color,
    accent: Color,
    darkText: Color
) {
    Column(
        modifier = Modifier
            .width(110.dp)
            .background(card, RoundedCornerShape(18.dp))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(18.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, color = darkText, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text(label, color = accent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SectionTitleLight(text: String, color: Color) {
    Text(text, color = color, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    Spacer(Modifier.height(12.dp))
}

@Composable
private fun MenuItemLight(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String?,
    card: Color,
    darkText: Color,
    grayText: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(card, RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = darkText)
        Spacer(Modifier.width(12.dp))
        Column {
            Text(title, color = darkText, fontWeight = FontWeight.Bold)
            subtitle?.let {
                Text(it, color = grayText, fontSize = 12.sp)
            }
        }
        Spacer(Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, null, tint = grayText)
    }
    Spacer(Modifier.height(12.dp))
}

@Composable
private fun ToggleItemLight(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    card: Color,
    accent: Color,
    darkText: Color,
    grayText: Color
) {
    var enabled by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(card, RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = darkText)
        Spacer(Modifier.width(12.dp))
        Column {
            Text(title, color = darkText, fontWeight = FontWeight.Bold)
            Text(subtitle, color = grayText, fontSize = 12.sp)
        }
        Spacer(Modifier.weight(1f))
        Switch(
            checked = enabled,
            onCheckedChange = { enabled = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = accent,
                checkedTrackColor = accent.copy(alpha = 0.4f)
            )
        )
    }
    Spacer(Modifier.height(12.dp))
}
