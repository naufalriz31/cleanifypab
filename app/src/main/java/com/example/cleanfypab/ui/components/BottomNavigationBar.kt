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
            selected = currentRoute?.startsWith(Routes.HOME) == true,
            onClick = {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.HOME)
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
            label = { Text("Dashboard") },
            colors = navColors()
        )

        NavigationBarItem(
            selected = currentRoute?.startsWith(Routes.HISTORY) == true,
            onClick = {
                navController.navigate(Routes.HISTORY) {
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.List, contentDescription = "Reports") },
            label = { Text("Reports") },
            colors = navColors()
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
            colors = navColors()
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
            colors = navColors()
        )
    }
}

@Composable
private fun navColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = Color.Black,
    selectedTextColor = Color.Black,
    unselectedIconColor = Color.White,
    unselectedTextColor = Color.White,
    indicatorColor = Color(0xFF00E676)
)
