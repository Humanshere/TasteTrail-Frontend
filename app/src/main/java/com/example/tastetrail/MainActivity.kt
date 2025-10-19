package com.example.tastetrail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tastetrail.data.SessionManager
import com.example.tastetrail.ui.screens.ChangePasswordScreen
import com.example.tastetrail.ui.screens.ConfirmPasswordResetScreen
import com.example.tastetrail.ui.screens.HomeScreen
import com.example.tastetrail.ui.screens.LoginScreen
import com.example.tastetrail.ui.screens.ProfileScreen
import com.example.tastetrail.ui.screens.RegisterScreen
import com.example.tastetrail.ui.screens.RequestPasswordResetScreen
import com.example.tastetrail.ui.theme.TasteTrailTheme
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

sealed class Screen(val route: String, val label: String, val icon: @Composable () -> Unit) {
    object Home : Screen("home", "Routes", { Icon(Icons.Default.Home, contentDescription = "Routes") })
    object Profile : Screen("profile", "Profile", { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") })
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SessionManager.init(this)
        enableEdgeToEdge()
        setContent {
            TasteTrailTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val items = listOf(Screen.Home, Screen.Profile)
    val startDestination = if (SessionManager.authToken != null) Screen.Home.route else "login"

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val showBottomBar = items.any { it.route == currentDestination?.route }

            if (showBottomBar) {
                NavigationBar {
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = { screen.icon() },
                            label = { Text(screen.label) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
            exitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) },
            popEnterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) },
            popExitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) }
        ) {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = { navController.navigate(Screen.Home.route) {
                        popUpTo("login") { inclusive = true }
                    }},
                    onNavigateToRegister = { navController.navigate("register") },
                    onNavigateToPasswordReset = { navController.navigate("requestPasswordReset") }
                )
            }
            composable("register") {
                RegisterScreen(
                    onRegisterSuccess = { navController.popBackStack() },
                    onNavigateToLogin = { navController.popBackStack() }
                )
            }
            composable("requestPasswordReset") {
                RequestPasswordResetScreen(onRequestSent = { navController.navigate("confirmPasswordReset") })
            }
            composable("confirmPasswordReset") {
                ConfirmPasswordResetScreen(onPasswordReset = { navController.navigate("login") })
            }
            composable(Screen.Home.route) {
                HomeScreen(onFindRestaurants = { /* TODO */ })
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    onNavigateToChangePassword = { navController.navigate("changePassword") },
                    onLogout = {
                        navController.navigate("login") {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable("changePassword") {
                ChangePasswordScreen(onChangePassword = { navController.popBackStack() })
            }
        }
    }
}