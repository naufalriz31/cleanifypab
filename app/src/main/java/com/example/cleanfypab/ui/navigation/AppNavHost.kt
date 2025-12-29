package com.example.cleanfypab.ui.navigation

import com.example.cleanfypab.ui.screen.NotificationScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cleanfypab.ui.components.BottomNavigationBar
import com.example.cleanfypab.ui.screen.EditProfileScreen
import com.example.cleanfypab.ui.screen.EditReportScreen
import com.example.cleanfypab.ui.screen.HomeScreen
import com.example.cleanfypab.ui.screen.LoginScreen
import com.example.cleanfypab.ui.screen.ProfileScreen
import com.example.cleanfypab.ui.screen.ReportHistoryScreen
import com.example.cleanfypab.ui.screen.RoomDetailScreen
import com.example.cleanfypab.ui.screen.ScanScreen
import com.example.cleanfypab.ui.screen.TaskTodayScreen
import com.example.cleanfypab.ui.screen.UpdateStatusScreen
import com.example.cleanfypab.viewmodel.RoomViewModel

@Composable
fun AppNavHost(
    nav: NavHostController,
    vm: RoomViewModel
) {

    val navBackStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Bottom bar hanya tampil di flow PETUGAS
    val showBottomBar = currentRoute in listOf(
        Routes.HOME,
        Routes.HISTORY,
        Routes.TASK_TODAY,
        Routes.PROFILE,
        Routes.SCAN
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController = nav)
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = nav,
            startDestination = Routes.LOGIN,
            modifier = Modifier.padding(innerPadding)
        ) {

            /* ================= LOGIN ================= */
            composable(Routes.LOGIN) {
                LoginScreen(
                    onLoginSuccess = { role ->
                        if (role == "admin") {
                            nav.navigate(Routes.ADMIN_ROOT) {
                                popUpTo(Routes.LOGIN) { inclusive = true }
                                launchSingleTop = true
                            }
                        } else {
                            nav.navigate(Routes.PETUGAS_ROOT) {
                                popUpTo(Routes.LOGIN) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }

            /* ================= ADMIN ROOT ================= */
            composable(Routes.ADMIN_ROOT) {
                AdminNavHost()
            }

            /* ================= PETUGAS ROOT ================= */
            composable(Routes.PETUGAS_ROOT) {
                LaunchedEffect(Unit) {
                    nav.navigate(Routes.HOME) {
                        popUpTo(Routes.PETUGAS_ROOT) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }

            /* ================= PETUGAS FLOW ================= */
            composable(Routes.HOME) {
                HomeScreen(nav)
            }

            composable(Routes.HISTORY) {
                val rooms = vm.roomList.collectAsState().value
                ReportHistoryScreen(nav = nav, rooms = rooms)
            }

            composable(Routes.TASK_TODAY) {
                TaskTodayScreen(nav, vm)
            }

            composable(Routes.PROFILE) {
                ProfileScreen(nav)
            }

            composable(Routes.SCAN) {
                ScanScreen(nav)
            }

            composable(Routes.NOTIFICATION) {
                NotificationScreen(nav)
            }


            /* ================= DETAIL & UPDATE ================= */
            composable("detail/{id}") { backStack ->
                val id = backStack.arguments?.getString("id")?.toIntOrNull() ?: 0
                RoomDetailScreen(nav, vm, id)
            }

            composable("update_status/{id}") { backStack ->
                val id = backStack.arguments?.getString("id")?.toIntOrNull() ?: 0
                UpdateStatusScreen(nav, vm, id)
            }

            /* ================= EDIT ================= */
            composable(Routes.EDIT_PROFILE) {
                EditProfileScreen(nav)
            }

            composable("edit_report/{id}") { backStack ->
                val id = backStack.arguments?.getString("id")?.toIntOrNull() ?: 0
                EditReportScreen(nav, id)
            }
        }
    }
}
