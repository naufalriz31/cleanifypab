package com.example.cleanfypab.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SuccessPopup(
    onDone: () -> Unit // callback setelah popup selesai
) {
    var visible by remember { mutableStateOf(false) }

    // Trigger animation
    LaunchedEffect(Unit) {
        visible = true
        delay(1200) // tampil 1.2 detik
        visible = false
        delay(300)
        onDone()
    }

    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(tween(300)) + fadeIn(tween(300)),
        exit = fadeOut(tween(200))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x66000000)) // overlay gelap
        ) {

            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF0E0F0E))
                    .padding(26.dp)
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(220.dp)
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = Color(0xFF00E676),
                        modifier = Modifier.size(80.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "Success!",
                        fontSize = 22.sp,
                        color = Color.White
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = "Loading room details...",
                        color = Color(0xFFB8C3BD),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
