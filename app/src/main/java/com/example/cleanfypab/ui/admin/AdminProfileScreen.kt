package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
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

@Composable
fun AdminProfileScreen(
    onBack: () -> Unit = {},
    onEdit: () -> Unit = {},
    onSignOut: () -> Unit = {}
) {

    val bg = Color(0xFF0F2A1D)
    val card = Color(0xFF163828)
    val green = Color(0xFF2DFF8F)
    val red = Color(0xFFFF5C5C)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 16.dp)
    ) {

        /* ================= HEADER ================= */
        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
            }

            Spacer(Modifier.weight(1f))

            Text(
                "Profil",
                color = Color.White,
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

        /* ================= AVATAR ================= */
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Box(contentAlignment = Alignment.BottomEnd) {

                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(green)
                        .padding(5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            null,
                            tint = Color.White,
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
                    Icon(Icons.Default.Edit, null, tint = Color.Black, modifier = Modifier.size(16.dp))
                }
            }

            Spacer(Modifier.height(16.dp))

            Text("Jane Doe", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(6.dp))

            Surface(color = green.copy(alpha = 0.15f), shape = RoundedCornerShape(50)) {
                Text(
                    "SUPER ADMIN",
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    color = green,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(8.dp))

            Text("jane.doe@company.com", color = Color.Gray, fontSize = 13.sp)
            Text("Manajer Fasilitas Senior", color = Color.Gray, fontSize = 13.sp)
        }

        Spacer(Modifier.height(28.dp))

        /* ================= STATS ================= */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatBox("12", "TUGAS\nMENUNGGU", card, green)
            StatBox("45", "RUANGAN\nDIKELOLA", card, green)
            StatBox("98%", "TINGKAT\nRESPON", card, green)
        }

        Spacer(Modifier.height(32.dp))

        SectionTitle("PENGATURAN AKUN")
        MenuItem(Icons.Default.Lock, "Keamanan Akun", "Kata sandi, 2FA")
        MenuItem(Icons.Default.Notifications, "Notifikasi", "Push, Email, SMS")

        Spacer(Modifier.height(24.dp))

        SectionTitle("PENGATURAN APLIKASI")
        ToggleItem(Icons.Default.DarkMode, "Tampilan", "Mode Gelap Aktif")
        MenuItem(Icons.Default.Language, "Bahasa", "Bahasa Indonesia")

        Spacer(Modifier.height(24.dp))

        SectionTitle("DUKUNGAN")
        MenuItem(Icons.Default.Help, "Pusat Bantuan")
        MenuItem(Icons.Default.BugReport, "Laporkan Bug")

        Spacer(Modifier.height(28.dp))

        OutlinedButton(
            onClick = onSignOut,
            modifier = Modifier.fillMaxWidth().height(54.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = red)
        ) {
            Icon(Icons.AutoMirrored.Filled.Logout, null)
            Spacer(Modifier.width(8.dp))
            Text("Keluar", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(16.dp))

        Text(
            "Versi 1.0.2 (Build 240)",
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

/* ================= COMPONENTS ================= */

@Composable
private fun StatBox(value: String, label: String, bg: Color, accent: Color) {
    Column(
        modifier = Modifier
            .width(110.dp)
            .background(bg, RoundedCornerShape(18.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text(label, color = accent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, color = Color(0xFF2DFF8F), fontSize = 13.sp, fontWeight = FontWeight.Bold)
    Spacer(Modifier.height(12.dp))
}

@Composable
private fun MenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF163828), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = Color(0xFF2DFF8F))
        Spacer(Modifier.width(12.dp))
        Column {
            Text(title, color = Color.White, fontWeight = FontWeight.Bold)
            subtitle?.let { Text(it, color = Color.Gray, fontSize = 12.sp) }
        }
        Spacer(Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, null, tint = Color.Gray)
    }
    Spacer(Modifier.height(12.dp))
}

@Composable
private fun ToggleItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String
) {
    var enabled by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF163828), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = Color(0xFF2DFF8F))
        Spacer(Modifier.width(12.dp))
        Column {
            Text(title, color = Color.White, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Color.Gray, fontSize = 12.sp)
        }
        Spacer(Modifier.weight(1f))
        Switch(checked = enabled, onCheckedChange = { enabled = it })
    }
    Spacer(Modifier.height(12.dp))
}
