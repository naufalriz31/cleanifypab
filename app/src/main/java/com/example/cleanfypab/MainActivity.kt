package com.example.cleanfypab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.cleanfypab.ui.navigation.AppNavHost
import com.example.cleanfypab.viewmodel.RoomViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanifyApp()
        }
    }
}

@Composable
fun CleanifyApp() {
    val navController = rememberNavController()

    // ✅ Cukup pakai 1 VM sesuai AppNavHost
    val vm: RoomViewModel = viewModel()

    MaterialTheme {
        AppNavHost(
            nav = navController,
            vm = vm
        )
    }
}
