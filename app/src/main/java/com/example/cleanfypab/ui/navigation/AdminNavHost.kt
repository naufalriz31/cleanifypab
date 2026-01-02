package com.example.cleanfypab.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.cleanfypab.ui.admin.*
import com.example.cleanfypab.ui.components.admin.BottomNavigationAdmin

@Composable
fun AdminNavHost(
    rootNavController: NavHostController
) {

    val adminNavController = rememberNavController()
    val navBackStackEntry by adminNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigationAdmin(
                navController = adminNavController,
                currentRoute = currentRoute
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = adminNavController,
            startDestination = AdminRoutes.DASHBOARD,
            modifier = Modifier.padding(innerPadding)
        ) {

            /* ================= DASHBOARD ================= */
            composable(AdminRoutes.DASHBOARD) {
                AdminDashboardScreen(
                    onNotificationClick = {
                        adminNavController.navigate(AdminRoutes.NOTIFICATIONS)
                    }
                )
            }

            /* ================= ROOMS ================= */
            composable(AdminRoutes.ROOMS) {
                AdminRoomScreen(
                    onAddTask = {
                        adminNavController.navigate(AdminRoutes.CREATE_TASK)
                    }
                )
            }

            /* ================= REPORTS ================= */
            composable(AdminRoutes.REPORTS) {
                AdminReportScreen(
                    onAssignClick = {
                        adminNavController.navigate(AdminRoutes.CREATE_TASK)
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
                        adminNavController.navigate(AdminRoutes.EDIT_PROFILE)
                    },
                    onSignOut = {
                        // 🔥 LOGOUT ADMIN → LOGIN
                        rootNavController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.ADMIN_ROOT) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            /* ================= EDIT PROFILE ================= */
            composable(AdminRoutes.EDIT_PROFILE) {
                AdminEditProfileScreen(
                    onBack = { adminNavController.popBackStack() },
                    onSave = { adminNavController.popBackStack() }
                )
            }

            /* ================= NOTIFICATIONS ================= */
            composable(AdminRoutes.NOTIFICATIONS) {
                AdminNotificationScreen(
                    onBack = { adminNavController.popBackStack() },
                    onAssignTask = {
                        adminNavController.navigate(AdminRoutes.CREATE_TASK)
                    }
                )
            }

            /* ================= CREATE TASK ================= */
            composable(AdminRoutes.CREATE_TASK) {
                AdminCreateTaskScreen(
                    onCancel = { adminNavController.popBackStack() },
                    onAssign = {
                        adminNavController.popBackStack(AdminRoutes.REPORTS, false)
                    }
                )
            }
        }
    }
}
