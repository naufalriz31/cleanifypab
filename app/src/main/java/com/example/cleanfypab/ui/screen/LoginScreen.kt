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

    val primaryGreen = Color(0xFF2ECC71)
    val darkText = Color(0xFF1E2D28)
    val grayText = Color(0xFF6B7C75)

    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF6FBF8),
            Color(0xFFE9F5EE)
        )
    )

    LaunchedEffect(state.loggedIn) {
        if (state.loggedIn) {
            val role = state.role ?: "petugas"
            vm.consumeLogin()
            onLoginSuccess(role)
        }
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

            Spacer(Modifier.height(28.dp))

            /* ================= LOGO ================= */
            Image(
                painter = painterResource(id = R.drawable.logo_cleanify),
                contentDescription = "Logo Cleanify",
                modifier = Modifier.size(200.dp)
            )

            /* ===== TEXT DIGESER NAIK (MENEMPEL LOGO) ===== */
            Text(
                text = "CLEANIFY",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = darkText,
                modifier = Modifier.offset(y = (-12).dp)
            )

            Text(
                text = "Monitoring Petugas Kebersihan",
                fontSize = 14.sp,
                color = grayText,
                modifier = Modifier.offset(y = (-16).dp)
            )

            Spacer(Modifier.height(20.dp))

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
                        enabled = !state.loading
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
                        enabled = !state.loading
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "Forgot Password?",
                        color = primaryGreen,
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable { }
                    )

                    if (state.error != null) {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            state.error!!,
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
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryGreen
                        ),
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

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(modifier = Modifier.weight(1f))
                Text("  Or continue with  ", color = grayText)
                Divider(modifier = Modifier.weight(1f))
            }

            Spacer(Modifier.height(20.dp))

            LoginOptionButton("Sign in with Apple")
            Spacer(Modifier.height(12.dp))
            LoginOptionButton("Sign in with Google")
        }
    }
}

@Composable
fun LoginOptionButton(label: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.White, RoundedCornerShape(14.dp))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF1E2D28)
        )
    }
}
