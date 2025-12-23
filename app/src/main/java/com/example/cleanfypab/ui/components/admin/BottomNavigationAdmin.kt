package com.example.cleanfypab.ui.components.admin

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
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

    val green = Color(0xFF2DFF8F)
    val inactive = Color.Gray

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
            route = AdminRoutes.REPORTS,
            icon = Icons.AutoMirrored.Filled.Assignment,
            label = "Reports"
        ),
        AdminBottomNavItem(
            route = AdminRoutes.PROFILE,
            icon = Icons.Default.Person,
            label = "Profile"
        )
    )

    NavigationBar(
        containerColor = Color(0xFF0F2A1D)
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(AdminRoutes.DASHBOARD) { inclusive = false }
                        launchSingleTop = true
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
                }
            )
        }
    }
}

/* ================= MODEL ================= */

data class AdminBottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)
