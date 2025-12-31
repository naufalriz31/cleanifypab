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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cleanfypab.ui.navigation.Routes

@Composable
fun EditProfileScreen(nav: NavHostController) {

    var fullName by remember { mutableStateOf("John Doe") }
    var phone by remember { mutableStateOf("+1 (555) 000-0000") }
    var password by remember { mutableStateOf("password") }

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

        /* ===== BAR ATAS ===== */
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Kembali",
                tint = darkText,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { nav.popBackStack() }
            )

            Spacer(Modifier.width(12.dp))

            Text(
                "Edit Profil",
                color = darkText,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.weight(1f))

            Text(
                "Simpan",
                color = primaryGreen,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { nav.popBackStack() }
            )
        }

        Spacer(Modifier.height(28.dp))

        /* ===== FOTO ===== */
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(cardSoft)
                    .border(3.dp, primaryGreen, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, null, tint = grayText, modifier = Modifier.size(70.dp))
            }

            Box(
                modifier = Modifier
                    .offset(x = 42.dp, y = 42.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(primaryGreen)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.CameraAlt, null, tint = Color.White, modifier = Modifier.size(18.dp))
            }
        }

        Spacer(Modifier.height(10.dp))
        Text(
            "Ganti Foto",
            color = primaryGreen,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(28.dp))

        /* ===== INFORMASI PRIBADI ===== */
        EditProfileSectionTitleLight("INFORMASI PRIBADI", grayText)

        EditProfileInputLight(
            label = "Nama Lengkap",
            value = fullName,
            onValueChange = { fullName = it },
            trailingIcon = Icons.Default.CheckCircle,
            primaryGreen = primaryGreen,
            darkText = darkText
        )

        EditProfileDisabledLight(
            label = "Alamat Email",
            value = "john.doe@company.com",
            note = "Hubungi admin untuk memperbarui alamat email.",
            darkText = darkText,
            grayText = grayText,
            cardSoft = cardSoft
        )

        EditProfileInputLight(
            label = "Nomor Telepon",
            value = phone,
            onValueChange = { phone = it },
            primaryGreen = primaryGreen,
            darkText = darkText
        )

        EditProfileDisabledLight(
            label = "Jabatan",
            value = "Manajer Ruangan",
            leadingIcon = Icons.Default.Badge,
            darkText = darkText,
            grayText = grayText,
            cardSoft = cardSoft
        )

        Spacer(Modifier.height(28.dp))

        /* ===== KEAMANAN ===== */
        EditProfileSectionTitleLight("KEAMANAN", grayText)

        EditProfileInputLight(
            label = "Kata Sandi",
            value = password,
            onValueChange = { password = it },
            isPassword = true,
            trailingIcon = Icons.Default.Visibility,
            primaryGreen = primaryGreen,
            darkText = darkText
        )

        Text("Ubah Kata Sandi", color = primaryGreen, fontSize = 14.sp)

        Spacer(Modifier.height(32.dp))

        /* ===== TOMBOL SIMPAN ===== */
        Button(
            onClick = {
                nav.navigate(Routes.PROFILE) {
                    popUpTo(Routes.PROFILE) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryGreen),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Simpan Perubahan", fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(Modifier.height(24.dp))
    }
}

/* ===== KOMPONEN (LIGHT) ===== */

@Composable
fun EditProfileSectionTitleLight(text: String, grayText: Color) {
    Text(text, color = grayText, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    Spacer(Modifier.height(12.dp))
}

@Composable
fun EditProfileInputLight(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    trailingIcon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    primaryGreen: Color,
    darkText: Color
) {
    Text(label, color = darkText, fontSize = 14.sp)
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
            trailingIcon?.let { Icon(it, null, tint = primaryGreen) }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = darkText,
            unfocusedTextColor = darkText
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
    )

    Spacer(Modifier.height(16.dp))
}

@Composable
fun EditProfileDisabledLight(
    label: String,
    value: String,
    note: String? = null,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    darkText: Color,
    grayText: Color,
    cardSoft: Color
) {
    Text(label, color = darkText, fontSize = 14.sp)
    Spacer(Modifier.height(6.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(cardSoft, RoundedCornerShape(14.dp))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon?.let {
            Icon(it, null, tint = grayText)
            Spacer(Modifier.width(8.dp))
        }
        Text(value, color = grayText)
    }

    note?.let {
        Spacer(Modifier.height(6.dp))
        Text(it, color = grayText, fontSize = 12.sp)
    }

    Spacer(Modifier.height(16.dp))
}
