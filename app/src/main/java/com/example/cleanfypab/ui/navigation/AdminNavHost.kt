package com.example.cleanfypab.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
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
                AdminDashboardScreen(
                    onNotificationClick = {
                        // 🔔 ICON LONCENG → NOTIFICATION
                        navController.navigate(AdminRoutes.NOTIFICATIONS)
                    }
                )
            }

            /* ================= ROOMS ================= */
            composable(AdminRoutes.ROOMS) {
                AdminRoomScreen(
                    onAddTask = {
                        navController.navigate(AdminRoutes.CREATE_TASK)
                    }
                )
            }

            /* ================= REPORTS ================= */
            composable(AdminRoutes.REPORTS) {
                AdminReportScreen(
                    onAssignClick = {
                        navController.navigate(AdminRoutes.CREATE_TASK)
                    }
                )
            }

            /* ================= USERS ================= */
            composable(AdminRoutes.USERS) {
                AdminUserScreen()
            }

            /* ================= PROFILE ================= */
            composable(AdminRoutes.PROFILE) {
                AdminProfileScreen(
                    onEdit = {
                        navController.navigate(AdminRoutes.EDIT_PROFILE)
                    },
                    onSignOut = {
                        // TODO: logout logic (FirebaseAuth.signOut dll)
                    }
                )
            }

            /* ================= EDIT PROFILE ================= */
            composable(AdminRoutes.EDIT_PROFILE) {
                AdminEditProfileScreen(
                    onBack = { navController.popBackStack() },
                    onSave = { navController.popBackStack() }
                )
            }

            /* ================= NOTIFICATIONS ================= */
            composable(AdminRoutes.NOTIFICATIONS) {
                AdminNotificationScreen(
                    onBack = { navController.popBackStack() },
                    onAssignTask = {
                        navController.navigate(AdminRoutes.CREATE_TASK)
                    }
                )
            }

            /* ================= CREATE TASK ================= */
            composable(AdminRoutes.CREATE_TASK) {
                AdminCreateTaskScreen(
                    onCancel = { navController.popBackStack() },
                    onAssign = {
                        navController.popBackStack(AdminRoutes.REPORTS, false)
                    }
                )
            }
        }
    }
}
