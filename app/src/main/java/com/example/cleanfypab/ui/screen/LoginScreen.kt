package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit   // 🔐 ROLE AWARE
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B1E14))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(60.dp))

        Box(
            modifier = Modifier
                .size(90.dp)
                .background(Color(0xFF0F3D23), RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("▣", fontSize = 42.sp, color = Color(0xFF00E676))
        }

        Spacer(Modifier.height(24.dp))

        Text(
            "Welcome Back",
            fontSize = 32.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Text(
            "Log in to manage your rooms.",
            color = Color(0xFF8FA69B),
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(Modifier.height(34.dp))

        /* EMAIL */
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Enter your email") },
            leadingIcon = { Icon(Icons.Default.Email, null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF15261D),
                unfocusedContainerColor = Color(0xFF15261D),
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(Modifier.height(16.dp))

        /* PASSWORD */
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Enter your password") },
            leadingIcon = { Icon(Icons.Default.Lock, null) },
            trailingIcon = {
                Text(
                    if (showPassword) "Hide" else "Show",
                    color = Color(0xFF00E676),
                    modifier = Modifier.clickable { showPassword = !showPassword }
                )
            },
            visualTransformation = if (showPassword)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF15261D),
                unfocusedContainerColor = Color(0xFF15261D),
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(Modifier.height(20.dp))

        /* LOGIN BUTTON */
        Button(
            onClick = {
                // 🔐 DUMMY ROLE (UNTUK DEMO DOSEN)
                val role = if (email.contains("admin", ignoreCase = true)) {
                    "admin"
                } else {
                    "petugas"
                }
                onLoginSuccess(role)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E676)),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Log In", fontSize = 18.sp, color = Color.Black)
        }

        Spacer(Modifier.height(28.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(color = Color(0xFF2A3A33), modifier = Modifier.weight(1f))
            Text("  Or continue with  ", color = Color.Gray)
            Divider(color = Color(0xFF2A3A33), modifier = Modifier.weight(1f))
        }

        Spacer(Modifier.height(24.dp))

        LoginOptionButton("Sign in with Apple", Color.White, Color.Black)
        Spacer(Modifier.height(12.dp))
        LoginOptionButton("Sign in with Google", Color(0xFF1A2D23), Color.White)
    }
}

@Composable
fun LoginOptionButton(
    label: String,
    bg: Color,
    fg: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(bg, RoundedCornerShape(12.dp))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = fg, fontSize = 16.sp)
    }
}
