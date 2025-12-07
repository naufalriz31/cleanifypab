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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SuccessUpdatePopup(
    onFinished: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        delay(1200)    // tampil sebentar
        visible = false
        delay(250)
        onFinished()
    }

    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(tween(250)) + fadeIn(tween(250)),
        exit = fadeOut(tween(200))
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x55000000))
        ) {

            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color(0xFF0E0F0E), RoundedCornerShape(20.dp))
                    .padding(26.dp)
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(230.dp)
                ) {

                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = Color(0xFF00E676),
                        modifier = Modifier.size(80.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "Status Updated!",
                        color = Color.White,
                        fontSize = 22.sp
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = "Saving your report...",
                        color = Color(0xFFB8C3BD),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
