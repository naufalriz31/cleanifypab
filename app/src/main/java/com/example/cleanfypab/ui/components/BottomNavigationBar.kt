package com.example.cleanfypab.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import com.example.cleanfypab.ui.navigation.Routes

@Composable
fun BottomNavigationBar(navController: NavController) {

    val currentRoute = navController.currentBackStackEntry
        ?.destination
        ?.route

    NavigationBar(
        containerColor = Color(0xFF07120D)
    ) {

        NavigationBarItem(
            selected = currentRoute == Routes.HOME,
            onClick = { navController.navigate(Routes.HOME) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
            label = { Text("Dashboard") },

            // ⭐ Warna saat selected / unselected
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color(0xFF00E676) // bubble hijau neon
            )
        )

        NavigationBarItem(
            selected = currentRoute == Routes.HISTORY,
            onClick = { navController.navigate(Routes.HISTORY) },
            icon = { Icon(Icons.Default.List, contentDescription = "Reports") },
            label = { Text("Reports") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color(0xFF00E676)
            )
        )

        NavigationBarItem(
            selected = currentRoute == Routes.SCAN,
            onClick = { navController.navigate(Routes.SCAN) },
            icon = { Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan") },
            label = { Text("Scan") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color(0xFF00E676)
            )
        )

        NavigationBarItem(
            selected = currentRoute == Routes.PROFILE,
            onClick = { navController.navigate(Routes.PROFILE) },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Profile") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color(0xFF00E676)
            )
        )
    }
}
