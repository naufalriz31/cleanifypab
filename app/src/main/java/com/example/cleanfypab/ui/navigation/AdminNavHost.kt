package com.example.cleanfypab.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cleanfypab.ui.admin.*
import com.example.cleanfypab.ui.components.admin.BottomNavigationAdmin

@Composable
fun AdminNavHost() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigationAdmin(
                navController = navController,
                currentRoute = currentRoute
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = AdminRoutes.DASHBOARD,
            modifier = Modifier.padding(innerPadding)
        ) {

            /* ================= DASHBOARD ================= */
            composable(AdminRoutes.DASHBOARD) {
                AdminDashboardScreen()
            }

            /* ================= ROOMS ================= */
            composable(AdminRoutes.ROOMS) {
                AdminRoomScreen()
            }

            /* ================= REPORTS ================= */
            composable(AdminRoutes.REPORTS) {
                AdminReportScreen()
            }

            /* ================= USERS ================= */
            composable(AdminRoutes.USERS) {
                AdminUserScreen()
            }

            /* ================= PROFILE ================= */
            composable(AdminRoutes.PROFILE) {
                AdminProfileScreen()
            }

            /* ================= ASSIGN ROOM ================= */
            composable(AdminRoutes.ASSIGN_ROOM) {
                AdminAssignRoomScreen()
            }
        }
    }
}
