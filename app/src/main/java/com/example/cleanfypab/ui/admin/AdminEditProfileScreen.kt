package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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

@Composable
fun AdminEditProfileScreen(
    onBack: () -> Unit = {},
    onSave: () -> Unit = {},
    onDelete: () -> Unit = {}
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

    var fullName by remember { mutableStateOf("Jane Doe") }
    var jobTitle by remember { mutableStateOf("Manajer Ruangan") }
    var email by remember { mutableStateOf("jane.doe@company.com") }
    var phone by remember { mutableStateOf("+1 (555) 123-4567") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        /* ================= HEADER ================= */
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = null,
                tint = darkText,
                modifier = Modifier.clickable { onBack() }
            )
            Spacer(Modifier.width(16.dp))
            Text(
                "Edit Profil",
                color = darkText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(24.dp))

        /* ================= AVATAR ================= */
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.size(120.dp), contentAlignment = Alignment.BottomEnd) {

                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE9F5EE)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        null,
                        tint = darkText,
                        modifier = Modifier.size(64.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(green),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.CameraAlt, null, tint = Color.White)
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = {},
            border = BorderStroke(1.dp, green),
            shape = RoundedCornerShape(50),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Ganti Foto", color = green, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(24.dp))

        /* ================= PERSONAL INFO ================= */
        ProfileSectionTitleLight("INFORMASI PRIBADI", darkText)
        Spacer(Modifier.height(12.dp))
        ProfileTextFieldLight("Nama Lengkap", fullName, darkText, grayText) {
            fullName = it
        }
        ProfileTextFieldLight("Jabatan", jobTitle, darkText, grayText) {
            jobTitle = it
        }

        Spacer(Modifier.height(20.dp))

        /* ================= CONTACT ================= */
        ProfileSectionTitleLight("DETAIL KONTAK", darkText)
        Spacer(Modifier.height(12.dp))
        ProfileTextFieldLight(
            "Alamat Email",
            email,
            darkText,
            grayText,
            Icons.Default.Email,
            enabled = false
        ) {}
        ProfileTextFieldLight(
            "Nomor Telepon",
            phone,
            darkText,
            grayText,
            Icons.Default.Phone
        ) { phone = it }

        Spacer(Modifier.height(20.dp))

        /* ================= SECURITY ================= */
        ProfileSectionTitleLight("KEAMANAN", darkText)
        Spacer(Modifier.height(12.dp))
        ProfileTextFieldLight(
            "Kata Sandi Baru",
            newPassword,
            darkText,
            grayText,
            Icons.Default.Lock,
            isPassword = true
        ) { newPassword = it }

        ProfileTextFieldLight(
            "Konfirmasi Kata Sandi",
            confirmPassword,
            darkText,
            grayText,
            Icons.Default.Lock,
            isPassword = true
        ) { confirmPassword = it }

        Spacer(Modifier.height(24.dp))

        TextButton(
            onClick = onDelete,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(Icons.Default.Delete, null, tint = red)
            Spacer(Modifier.width(6.dp))
            Text("Hapus Akun", color = red, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = green),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                "Simpan Perubahan",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/* ================= COMPONENTS ================= */

@Composable
private fun ProfileSectionTitleLight(text: String, color: Color) {
    Text(
        text,
        color = color,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun ProfileTextFieldLight(
    label: String,
    value: String,
    darkText: Color,
    grayText: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    isPassword: Boolean = false,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(label, color = darkText, fontSize = 13.sp)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            leadingIcon = { icon?.let { Icon(it, null) } },
            visualTransformation =
                if (isPassword) PasswordVisualTransformation()
                else VisualTransformation.None,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color(0xFF2ECC71),
                unfocusedBorderColor = Color(0xFFE0E0E0),
                cursorColor = Color(0xFF2ECC71),
                focusedTextColor = darkText,
                unfocusedTextColor = darkText
            )
        )
    }
    Spacer(Modifier.height(14.dp))
}
