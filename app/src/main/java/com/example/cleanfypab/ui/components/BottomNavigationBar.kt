package com.example.cleanfypab.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
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
    // ✅ ini yang bikin recompose saat pindah page
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    /* ===== CLEANIFY LIGHT PALET (SAMA DENGAN ADMIN) ===== */
    val bg = Color.White
    val inactive = Color(0xFF6B7C75)
    val borderTop = Color(0xFFE0E0E0)

    // ✅ warna aktif ikut route/page yang sedang dibuka
    val active = activeColorByRoute(currentRoute)

    Surface(
        color = bg,
        shadowElevation = 8.dp,
        tonalElevation = 0.dp,
        border = BorderStroke(1.dp, borderTop)
    ) {
        NavigationBar(containerColor = bg) {

            NavigationBarItem(
                selected = currentRoute?.startsWith(Routes.HOME) == true,
                onClick = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
                label = { Text("Dashboard") },
                colors = navItemColors(active, inactive)
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
                colors = navItemColors(active, inactive)
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
                colors = navItemColors(active, inactive)
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
                colors = navItemColors(active, inactive)
            )
        }
    }
}

/* ================= ROUTE -> COLOR ================= */

@Composable
private fun activeColorByRoute(currentRoute: String?): Color {
    // Kamu bebas ganti warna per halaman
    val homeColor = Color(0xFF2ECC71)
    val historyColor = Color(0xFF2ECC71)
    val scanColor = Color(0xFF2ECC71)
    val profileColor = Color(0xFF2ECC71)
    val defaultColor = homeColor

    return when {
        currentRoute?.startsWith(Routes.HOME) == true -> homeColor
        currentRoute?.startsWith(Routes.HISTORY) == true -> historyColor
        currentRoute?.startsWith(Routes.SCAN) == true -> scanColor
        currentRoute?.startsWith(Routes.PROFILE) == true -> profileColor

        // kalau ada route lain yang masih tampil bottom bar (misal TASK_TODAY),
        // set warna default atau warna khusus:
        currentRoute?.startsWith(Routes.TASK_TODAY) == true -> Color(0xFF9B59B6) // ungu (opsional)

        else -> defaultColor
    }
}

/* ================= HELPER ================= */

@Composable
private fun navItemColors(
    active: Color,
    inactive: Color
) = NavigationBarItemDefaults.colors(
    indicatorColor = Color.Transparent,
    selectedIconColor = active,
    selectedTextColor = active,
    unselectedIconColor = inactive,
    unselectedTextColor = inactive
)
