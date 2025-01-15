package com.example.leaningfirebase.Routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.leaningfirebase.Screen.AddThread
import com.example.leaningfirebase.Screen.BottomNav
import com.example.leaningfirebase.Screen.Home
import com.example.leaningfirebase.Screen.LogInfoPage
import com.example.leaningfirebase.Screen.Notification
import com.example.leaningfirebase.Screen.Profile
import com.example.leaningfirebase.Screen.Search
import com.example.leaningfirebase.Screen.SingUpPage
import com.example.leaningfirebase.Screen.Splash
import com.example.leaningfirebase.item_view.ThreadItem

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.Splash.screen) {

        composable(Screen.Splash.screen) {
            Splash(navController)
        }

        composable(Screen.Home.screen) {
            Home(navController)
        }

        composable(Screen.Notification.screen) {
            Notification(navController)
        }

        composable(Screen.Search.screen) {
            Search(navController)
        }

        composable(Screen.AddThread.screen) {
            AddThread(navController)
        }

        composable(Screen.BottomNav.screen) {
            BottomNav(navController)
        }

        composable(Screen.Profile.screen) {
            Profile(navController)
        }

        composable(Screen.LogIn.screen) {
            LogInfoPage(navController)
        }

        composable(Screen.SingUp.screen) {
            SingUpPage(navController)
        }



    }
}


