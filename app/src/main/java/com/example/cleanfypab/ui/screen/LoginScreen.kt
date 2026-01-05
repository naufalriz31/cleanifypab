package com.example.cleanfypab.ui.screen

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cleanfypab.R
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

    /* ===== WARNA ===== */
    val primaryGreen = Color(0xFF2ECC71)
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF6FBF8),
            Color(0xFFE9F5EE)
        )
    )

    // ✅ Redirect setelah berhasil login (role sudah didapat dari Firestore)
    LaunchedEffect(state.role) {
        val role = state.role ?: return@LaunchedEffect
        onLoginSuccess(role)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(24.dp))

            /* ================= LOGO ================= */
            Image(
                painter = painterResource(id = R.drawable.logo_cleanify),
                contentDescription = "Logo Cleanify",
                modifier = Modifier
                    .size(250.dp)
                    .padding(bottom = 4.dp)
            )

            Text(
                text = "CLEANIFY",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = darkText,
                modifier = Modifier.offset(y = (-20).dp)
            )

            Text(
                text = "Monitoring Petugas Kebersihan",
                fontSize = 14.sp,
                color = grayText,
                modifier = Modifier.offset(y = (-24).dp)
            )

            Spacer(Modifier.height(12.dp))

            /* ================= CARD LOGIN ================= */
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("Enter your email") },
                        leadingIcon = {
                            Icon(Icons.Default.Email, null, tint = primaryGreen)
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = primaryGreen,
                            unfocusedIndicatorColor = Color.LightGray,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = primaryGreen,
                            focusedTextColor = darkText,
                            unfocusedTextColor = darkText
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        enabled = !state.loading,
                        singleLine = true
                    )

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Enter your password") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, null, tint = primaryGreen)
                        },
                        trailingIcon = {
                            Text(
                                if (showPassword) "Hide" else "Show",
                                color = primaryGreen,
                                modifier = Modifier.clickable {
                                    showPassword = !showPassword
                                }
                            )
                        },
                        visualTransformation =
                            if (showPassword) VisualTransformation.None
                            else PasswordVisualTransformation(),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = primaryGreen,
                            unfocusedIndicatorColor = Color.LightGray,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = primaryGreen,
                            focusedTextColor = darkText,
                            unfocusedTextColor = darkText
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        enabled = !state.loading,
                        singleLine = true
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "Forgot Password?",
                        color = primaryGreen,
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable { /* TODO: reset password */ }
                    )

                    if (state.error != null) {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = state.error!!,
                            color = Color.Red,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = { vm.login(email, password) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = primaryGreen),
                        enabled = !state.loading
                    ) {
                        Text(
                            text = if (state.loading) "Checking..." else "Log In",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
