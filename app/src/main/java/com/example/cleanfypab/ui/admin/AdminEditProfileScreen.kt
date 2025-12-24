package com.example.cleanfypab.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

    val bg = Color(0xFF0F2A1D)
    val card = Color(0xFF163828)
    val green = Color(0xFF2DFF8F)
    val red = Color(0xFFFF5C5C)

    var fullName by remember { mutableStateOf("Jane Doe") }
    var jobTitle by remember { mutableStateOf("Room Manager") }
    var email by remember { mutableStateOf("jane.doe@company.com") }
    var phone by remember { mutableStateOf("+1 (555) 123-4567") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        /* HEADER */
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable { onBack() }
            )
            Spacer(Modifier.width(16.dp))
            Text("Edit Profile", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(24.dp))

        /* AVATAR */
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Box(modifier = Modifier.size(120.dp), contentAlignment = Alignment.BottomEnd) {

                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE5E7EB)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, null, modifier = Modifier.size(64.dp))
                }

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(green),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.CameraAlt, null, tint = Color.Black)
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = card),
            shape = RoundedCornerShape(50),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Change Photo", color = green)
        }

        Spacer(Modifier.height(24.dp))

        /* PERSONAL INFO */
        ProfileSectionTitle("PERSONAL INFORMATION")
        Spacer(Modifier.height(12.dp))
        ProfileTextField("Full Name", fullName) { fullName = it }
        ProfileTextField("Job Title", jobTitle) { jobTitle = it }

        Spacer(Modifier.height(20.dp))

        /* CONTACT */
        ProfileSectionTitle("CONTACT DETAILS")
        Spacer(Modifier.height(12.dp))
        ProfileTextField("Email Address", email, Icons.Default.Email, enabled = false) {}
        ProfileTextField("Phone Number", phone, Icons.Default.Phone) { phone = it }

        Spacer(Modifier.height(20.dp))

        /* SECURITY */
        ProfileSectionTitle("SECURITY")
        Spacer(Modifier.height(12.dp))
        ProfileTextField("New Password", newPassword, Icons.Default.Lock, true) { newPassword = it }
        ProfileTextField("Confirm Password", confirmPassword, Icons.Default.Lock, true) { confirmPassword = it }

        Spacer(Modifier.height(24.dp))

        TextButton(onClick = onDelete, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Icon(Icons.Default.Delete, null, tint = red)
            Spacer(Modifier.width(6.dp))
            Text("Delete Account", color = red)
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = green),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Save Changes", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

/* ================= COMPONENTS ================= */

@Composable
private fun ProfileSectionTitle(text: String) {
    Text(text, color = Color(0xFF2DFF8F), fontSize = 13.sp, fontWeight = FontWeight.Bold)
}

@Composable
private fun ProfileTextField(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    isPassword: Boolean = false,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(label, color = Color.White, fontSize = 13.sp)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            leadingIcon = { icon?.let { Icon(it, null) } },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF163828),
                unfocusedContainerColor = Color(0xFF163828),
                focusedBorderColor = Color(0xFF2DFF8F),
                unfocusedBorderColor = Color(0xFF245C3A),
                cursorColor = Color(0xFF2DFF8F)
            )
        )
    }
    Spacer(Modifier.height(14.dp))
}
