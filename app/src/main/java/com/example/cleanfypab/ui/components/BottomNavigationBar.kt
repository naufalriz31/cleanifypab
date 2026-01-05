package com.example.cleanfypab.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.automirrored.filled.List
import com.example.cleanfypab.ui.navigation.Routes

@Composable
fun BottomNavigationBar(
    navController: NavController
) {

    val currentRoute = navController.currentBackStackEntry
        ?.destination
        ?.route

    /* ===== CLEANIFY LIGHT PALET (SAMA DENGAN ADMIN) ===== */
    val bg = Color.White
    val green = Color(0xFF2ECC71)
    val inactive = Color(0xFF6B7C75)
    val borderTop = Color(0xFFE0E0E0)

    Surface(
        color = bg,
        shadowElevation = 8.dp,
        tonalElevation = 0.dp,
        border = BorderStroke(1.dp, borderTop)
    ) {
        NavigationBar(
            containerColor = bg
        ) {

            NavigationBarItem(
                selected = currentRoute?.startsWith(Routes.HOME) == true,
                onClick = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        Icons.Default.Home,
                        contentDescription = "Dashboard"
                    )
                },
                label = { Text("Dashboard") },
                colors = navItemColors(green, inactive)
            )

            NavigationBarItem(
                selected = currentRoute?.startsWith(Routes.HISTORY) == true,
                onClick = {
                    navController.navigate(Routes.HISTORY) {
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        Icons.AutoMirrored.Filled.List,
                        contentDescription = "Reports"
                    )
                },
                label = { Text("Reports") },
                colors = navItemColors(green, inactive)
            )

            NavigationBarItem(
                selected = currentRoute?.startsWith(Routes.SCAN) == true,
                onClick = {
                    navController.navigate(Routes.SCAN) {
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        Icons.Default.QrCodeScanner,
                        contentDescription = "Scan"
                    )
                },
                label = { Text("Scan") },
                colors = navItemColors(green, inactive)
            )

            NavigationBarItem(
                selected = currentRoute?.startsWith(Routes.PROFILE) == true,
                onClick = {
                    navController.navigate(Routes.PROFILE) {
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Profile"
                    )
                },
                label = { Text("Profile") },
                colors = navItemColors(green, inactive)
            )
        }
    }
}

/* ================= HELPER ================ */

@Composable
private fun navItemColors(
    active: Color,
    inactive: Color
) = NavigationBarItemDefaults.colors(
    indicatorColor = Color.Transparent,   // ❌ matikan background tab
    selectedIconColor = active,
    selectedTextColor = active,
    unselectedIconColor = inactive,
    unselectedTextColor = inactive
)
