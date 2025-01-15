package com.example.leaningfirebase.Routes

sealed class Screen(val screen: String) {

    object Home : Screen("home")
    object Notification : Screen("notification")
    object Profile : Screen("profile")
    object Splash : Screen("splash")
    object Search : Screen("search")
    object AddThread : Screen("addThread")
    object BottomNav : Screen("bottomNav")
    object LogIn : Screen("logIn")
    object SingUp : Screen("singUp")
    object PostView :Screen("postView")
}
