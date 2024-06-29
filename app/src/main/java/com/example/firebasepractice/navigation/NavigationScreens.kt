package com.example.firebasepractice.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.firebasepractice.nav.NavItem
import com.example.firebasepractice.screens.AddPage
import com.example.firebasepractice.screens.HomePage
import com.example.firebasepractice.screens.NotificationPage
import com.example.firebasepractice.screens.SettingsPage


@Composable
fun NavigationScreens(navController: NavHostController) {
    NavHost(navController, startDestination = NavItem.Home.path) {
        composable(NavItem.Home.path) { HomePage() }
        composable(NavItem.Notification.path) { NotificationPage() }
        composable(NavItem.List.path) { AddPage() }
        composable(NavItem.Profile.path) { SettingsPage() }
    }
}