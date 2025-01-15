package com.example.leaningfirebase.Screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leaningfirebase.Routes.Screen


@Composable
fun BottomNav(navController: NavController) {

    val navController1 = rememberNavController()

    Scaffold(
        topBar = {
            Text(
                text = "Thread App",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
            )
        },

        bottomBar = { MyBottomBar(navController1 = navController1) }) { innerPadding ->

        NavHost(
            navController = navController1, startDestination = Screen.Home.screen,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(route = Screen.Home.screen) {
                Home(navController)
            }

            composable(route = Screen.Notification.screen) {
                Notification(navController)
            }

            composable(route = Screen.Search.screen) {
                Search(navController)
            }

            composable(route = Screen.Splash.screen) {
                Splash(navController)
            }

            composable(route = Screen.AddThread.screen) {
                AddThread(navController1)
            }

            composable(route = Screen.Profile.screen) {
                Profile(navController)
            }

        }
    }
}

