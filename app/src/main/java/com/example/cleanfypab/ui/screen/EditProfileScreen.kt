package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cleanfypab.ui.navigation.Routes

// ===== WARNA EDIT PROFIL =====
private val EP_BG = Color(0xFF071A12)
private val EP_CARD = Color(0xFF0F2A1D)
private val EP_GREEN = Color(0xFF00E676)
private val EP_GRAY = Color(0xFF9BA5A0)
private val EP_WHITE = Color.White

@Composable
fun EditProfileScreen(nav: NavHostController) {

    var fullName by remember { mutableStateOf("John Doe") }
    var phone by remember { mutableStateOf("+1 (555) 000-0000") }
    var password by remember { mutableStateOf("password") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EP_BG)
            .padding(16.dp)
    ) {

        // ===== BAR ATAS =====
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Kembali",
                tint = EP_WHITE,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { nav.popBackStack() }
            )

            Spacer(Modifier.width(12.dp))

            Text(
                "Edit Profil",
                color = EP_WHITE,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.weight(1f))

            Text(
                "Simpan",
                color = EP_GREEN,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { nav.popBackStack() }
            )
        }

        Spacer(Modifier.height(28.dp))

        // ===== FOTO =====
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(EP_CARD)
                    .border(3.dp, EP_GREEN, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, null, tint = EP_GRAY, modifier = Modifier.size(70.dp))
            }

            Box(
                modifier = Modifier
                    .offset(x = 42.dp, y = 42.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(EP_GREEN)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.CameraAlt, null, tint = EP_BG, modifier = Modifier.size(18.dp))
            }
        }

        Spacer(Modifier.height(10.dp))
        Text(
            "Ganti Foto",
            color = EP_GREEN,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(28.dp))

        // ===== INFORMASI PRIBADI =====
        EditProfileSectionTitle("INFORMASI PRIBADI")

        EditProfileInput(
            label = "Nama Lengkap",
            value = fullName,
            onValueChange = { fullName = it },
            trailingIcon = Icons.Default.CheckCircle
        )

        EditProfileDisabled(
            label = "Alamat Email",
            value = "john.doe@company.com",
            note = "Hubungi admin untuk memperbarui alamat email."
        )

        EditProfileInput(
            label = "Nomor Telepon",
            value = phone,
            onValueChange = { phone = it }
        )

        EditProfileDisabled(
            label = "Jabatan",
            value = "Manajer Ruangan",
            leadingIcon = Icons.Default.Badge
        )

        Spacer(Modifier.height(28.dp))

        // ===== KEAMANAN =====
        EditProfileSectionTitle("KEAMANAN")

        EditProfileInput(
            label = "Kata Sandi",
            value = password,
            onValueChange = { password = it },
            isPassword = true,
            trailingIcon = Icons.Default.Visibility
        )

        Text("Ubah Kata Sandi", color = EP_GREEN, fontSize = 14.sp)

        Spacer(Modifier.weight(1f))

        // ===== TOMBOL SIMPAN =====
        Button(
            onClick = {
                nav.navigate(Routes.PROFILE) {
                    popUpTo(Routes.PROFILE) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EP_GREEN),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Simpan Perubahan", fontWeight = FontWeight.Bold, color = EP_BG)
        }
    }
}

// ===== KOMPONEN =====

@Composable
fun EditProfileSectionTitle(text: String) {
    Text(text, color = EP_GRAY, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    Spacer(Modifier.height(12.dp))
}

@Composable
fun EditProfileInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    trailingIcon: androidx.compose.ui.graphics.vector.ImageVector? = null
) {
    Text(label, color = EP_WHITE, fontSize = 14.sp)
    Spacer(Modifier.height(6.dp))

    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        visualTransformation = if (isPassword)
            PasswordVisualTransformation()
        else
            VisualTransformation.None,
        trailingIcon = {
            trailingIcon?.let { Icon(it, null, tint = EP_GREEN) }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = EP_CARD,
            unfocusedContainerColor = EP_CARD,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = EP_WHITE,
            unfocusedTextColor = EP_WHITE
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
    )

    Spacer(Modifier.height(16.dp))
}

@Composable
fun EditProfileDisabled(
    label: String,
    value: String,
    note: String? = null,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector? = null
) {
    Text(label, color = EP_WHITE, fontSize = 14.sp)
    Spacer(Modifier.height(6.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(EP_CARD, RoundedCornerShape(14.dp))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon?.let {
            Icon(it, null, tint = EP_GRAY)
            Spacer(Modifier.width(8.dp))
        }
        Text(value, color = EP_GRAY)
    }

    note?.let {
        Spacer(Modifier.height(6.dp))
        Text(it, color = EP_GRAY, fontSize = 12.sp)
    }

    Spacer(Modifier.height(16.dp))
}
