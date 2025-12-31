package com.example.cleanfypab.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.ui.unit.dp
import com.example.cleanfypab.ui.navigation.Routes

@Composable
fun BottomNavigationBar(navController: NavController) {

    val currentRoute = navController.currentBackStackEntry
        ?.destination
        ?.route

    // 🎨 WARNA NETRAL
    val navBg = Color(0xFFF5F7F6)
    val activeColor = Color(0xFF1E2D28)     // teks/icon aktif
    val inactiveColor = Color(0xFF8A9A94)   // teks/icon non-aktif
    val indicatorColor = Color(0xFFE1E6E4)  // background tab aktif

    NavigationBar(
        containerColor = navBg,
        tonalElevation = 8.dp
    ) {

        NavigationBarItem(
            selected = currentRoute?.startsWith(Routes.HOME) == true,
            onClick = {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.HOME)
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
            label = { Text("Dashboard") },
            colors = navItemColors(activeColor, inactiveColor, indicatorColor)
        )

        NavigationBarItem(
            selected = currentRoute?.startsWith(Routes.HISTORY) == true,
            onClick = {
                navController.navigate(Routes.HISTORY) {
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Reports") },
            label = { Text("Reports") },
            colors = navItemColors(activeColor, inactiveColor, indicatorColor)
        )

        NavigationBarItem(
            selected = currentRoute?.startsWith(Routes.SCAN) == true,
            onClick = {
                navController.navigate(Routes.SCAN) {
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan") },
            label = { Text("Scan") },
            colors = navItemColors(activeColor, inactiveColor, indicatorColor)
        )

        NavigationBarItem(
            selected = currentRoute?.startsWith(Routes.PROFILE) == true,
            onClick = {
                navController.navigate(Routes.PROFILE) {
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Profile") },
            label = { Text("Profile") },
            colors = navItemColors(activeColor, inactiveColor, indicatorColor)
        )
    }
}

@Composable
private fun navItemColors(
    active: Color,
    inactive: Color,
    indicator: Color
) = NavigationBarItemDefaults.colors(
    selectedIconColor = active,
    selectedTextColor = active,
    unselectedIconColor = inactive,
    unselectedTextColor = inactive,
    indicatorColor = indicator
)
