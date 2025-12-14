package com.example.cleanfypab.ui.navigation

import com.example.cleanfypab.ui.screen.EditProfileScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cleanfypab.ui.components.BottomNavigationBar
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
    // Route yang sedang aktif
    val navBackStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // Bottom nav hanya untuk halaman utama (bukan login)
            val showBottomBar = currentRoute in listOf(
                Routes.HOME,
                Routes.HISTORY,
                Routes.TASK_TODAY,
                Routes.PROFILE,
                Routes.SCAN
            )
            if (showBottomBar) {
                BottomNavigationBar(navController = nav)
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = nav,
            startDestination = Routes.LOGIN,          // Mulai dari login
            modifier = Modifier.padding(innerPadding)
        ) {

            // LOGIN
            composable(Routes.LOGIN) {
                LoginScreen(
                    onLoginSuccess = {
                        nav.navigate(Routes.HOME) {
                            // Hapus login dari backstack
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }
                )
            }

            // HOME / DASHBOARD
            composable(Routes.HOME) {
                HomeScreen(nav)
            }

            // REPORT HISTORY
            composable(Routes.HISTORY) {
                val rooms = vm.roomList.collectAsState().value
                ReportHistoryScreen(nav = nav, rooms = rooms)
            }

            // TASK TODAY
            composable(Routes.TASK_TODAY) {
                TaskTodayScreen(nav, vm)
            }

            // PROFILE
            composable(Routes.PROFILE) {
                ProfileScreen(nav)
            }

            // SCAN
            composable(Routes.SCAN) {
                ScanScreen(nav)
            }

            // DETAIL ROOM
            composable("detail/{id}") { backStack ->
                val id = backStack.arguments?.getString("id")?.toInt() ?: 0
                RoomDetailScreen(nav, vm, id)
            }

            // UPDATE STATUS
            composable("update_status/{id}") { backStack ->
                val id = backStack.arguments?.getString("id")?.toInt() ?: 0
                UpdateStatusScreen(nav, id)
            }

            composable(Routes.EDIT_PROFILE) {
                EditProfileScreen(nav)
            }


            // EDIT REPORT
            composable("edit_report/{id}") { backStack ->
                val id = backStack.arguments?.getString("id")?.toInt() ?: 0
                EditReportScreen(nav, id)
            }
        }
    }
}
