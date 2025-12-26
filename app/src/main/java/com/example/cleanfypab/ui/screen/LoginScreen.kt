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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cleanfypab.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: (role: String) -> Unit,
    vm: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val state by vm.uiState.collectAsState()

    // ✅ kalau login sukses, lempar role ke AppNavHost
    LaunchedEffect(state.loggedIn) {
        if (state.loggedIn) {
            val role = state.role ?: "petugas"
            vm.consumeLogin()
            onLoginSuccess(role)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B1E14))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(60.dp))

        // ICON
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
            "CLEANFY",
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

        // EMAIL FIELD
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Enter your email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF15261D),
                unfocusedContainerColor = Color(0xFF15261D),
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLabelColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            enabled = !state.loading
        )

        Spacer(Modifier.height(16.dp))

        // PASSWORD FIELD
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Enter your password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                Text(
                    if (showPassword) "Hide" else "Show",
                    color = Color(0xFF00E676),
                    modifier = Modifier.clickable { showPassword = !showPassword }
                )
            },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF15261D),
                unfocusedContainerColor = Color(0xFF15261D),
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            enabled = !state.loading
        )

        Spacer(Modifier.height(10.dp))

        Text(
            "Forgot Password?",
            color = Color(0xFF00E676),
            modifier = Modifier
                .align(Alignment.End)
                .clickable { /* optional */ }
        )

        // ✅ ERROR MESSAGE (tambah tanpa mengubah layout besar)
        if (state.error != null) {
            Spacer(Modifier.height(10.dp))
            Text(
                text = state.error!!,
                color = Color(0xFFFF6B6B),
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(20.dp))

        // LOGIN BUTTON (✅ sekarang validasi via Firebase)
        Button(
            onClick = {
                // email bisa diisi: admin / user / admin@gmail.com / user@gmail.com
                vm.login(email, password)
            },
            enabled = !state.loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E676)),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = if (state.loading) "Checking..." else "Log In",
                fontSize = 18.sp,
                color = Color.Black
            )
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

        // APPLE LOGIN (dummy UI)
        LoginOptionButton(label = "Sign in with Apple", bg = Color.White, fg = Color.Black)

        Spacer(Modifier.height(12.dp))

        // GOOGLE LOGIN (dummy UI)
        LoginOptionButton(label = "Sign in with Google", bg = Color(0xFF1A2D23), fg = Color.White)
    }
}

@Composable
fun LoginOptionButton(label: String, bg: Color, fg: Color) {
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
