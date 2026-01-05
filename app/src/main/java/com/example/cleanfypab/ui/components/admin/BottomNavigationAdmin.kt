package com.example.cleanfypab.ui.components.admin

import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.cleanfypab.ui.navigation.AdminRoutes

@Composable
fun BottomNavigationAdmin(
    navController: NavController,
    currentRoute: String?
) {

    /* ===== CLEANIFY LIGHT PALET ===== */
    val bg = Color.White
    val green = Color(0xFF2ECC71)
    val inactive = Color(0xFF6B7C75)
    val borderTop = Color(0xFFE0E0E0)

    val items = listOf(
        AdminBottomNavItem(
            route = AdminRoutes.DASHBOARD,
            icon = Icons.Default.Home,
            label = "Dashboard"
        ),
        AdminBottomNavItem(
            route = AdminRoutes.ROOMS,
            icon = Icons.Default.MeetingRoom,
            label = "Rooms"
        ),
        AdminBottomNavItem(
            route = AdminRoutes.PETUGAS, // ✅ GANTI
            icon = Icons.Default.People, // 👥 ICON PETUGAS
            label = "Petugas"
        ),
        AdminBottomNavItem(
            route = AdminRoutes.PROFILE,
            icon = Icons.Default.Person,
            label = "Profile"
        )
    )

    Surface(
        color = bg,
        shadowElevation = 8.dp,
        tonalElevation = 0.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, borderTop)
    ) {
        NavigationBar(containerColor = bg) {
            items.forEach { item ->
                val selected = currentRoute == item.route

                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        if (!selected) {
                            navController.navigate(item.route) {
                                popUpTo(AdminRoutes.DASHBOARD) {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (selected) green else inactive
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            color = if (selected) green else inactive
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = green,
                        selectedTextColor = green,
                        unselectedIconColor = inactive,
                        unselectedTextColor = inactive
                    )
                )
            }
        }
    }
}

/* ================= MODEL ================= */

data class AdminBottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)
