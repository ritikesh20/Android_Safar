package com.example.leaningfirebase.Screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.leaningfirebase.Routes.Screen
import com.example.leaningfirebase.model.BottomNavItem

//data class BottomNavItem(
//
//    val title: String = "",
//    val screen: String = "",
//    val icon: ImageVector
//
//)

@Composable
fun MyBottomBar(navController1: NavController) {

    val backStackEntry = navController1.currentBackStackEntryAsState()

    val list = listOf(
        BottomNavItem("Home", Screen.Home.screen, Icons.Default.Home),
        BottomNavItem("Search", Screen.Search.screen, Icons.Default.Search),
        BottomNavItem("Add Thread", Screen.AddThread.screen, Icons.Default.Add),
        BottomNavItem("Notification", Screen.Notification.screen, Icons.Default.Notifications),
        BottomNavItem("Profile", Screen.Profile.screen, Icons.Default.Person)
    )


    BottomAppBar {
        list.forEach {
            val select = it.screen == backStackEntry.value?.destination?.route

            NavigationBarItem(selected = select,
                onClick = { navController1.navigate(it.screen) },
                icon = {
                    Icon(imageVector = it.icon, contentDescription = "")
                }
            )
        }
    }
}
