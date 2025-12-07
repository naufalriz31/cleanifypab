package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cleanfypab.ui.navigation.Routes

@Composable
fun ProfileScreen(nav: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(28.dp)
                    .clickable { nav.popBackStack() }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text("Profil", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(20.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(Modifier.height(12.dp))

            Text("Raffi Pratama", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Petugas Kebersihan", color = Color.Gray)
        }

        Spacer(Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Informasi Shift", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text("Shift Aktif : 08.00 - 16.00", fontSize = 14.sp)
                Text("Total Ruangan Hari Ini : 20", fontSize = 14.sp)
            }
        }

        Spacer(Modifier.height(30.dp))

        Text("Pengaturan", fontSize = 16.sp, fontWeight = FontWeight.Medium)

        Spacer(Modifier.height(12.dp))

        ProfileMenuItem(
            title = "Edit Profil",
            icon = Icons.Default.Settings
        )

        ProfileMenuItem(
            title = "Riwayat Pembersihan",
            icon = Icons.Default.History,
            onClick = { nav.navigate(Routes.HISTORY) }
        )

        ProfileMenuItem(
            title = "Bantuan",
            icon = Icons.AutoMirrored.Filled.HelpOutline
        )

        ProfileMenuItem(
            title = "Logout",
            icon = Icons.Default.Logout,
            textColor = Color.Red,
            onClick = { /* TODO Logout */ }
        )
    }
}

@Composable
fun ProfileMenuItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    textColor: Color = Color.Black,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = Color(0xFF2962FF))
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontSize = 16.sp, color = textColor)
    }
}
