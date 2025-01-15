package com.example.leaningfirebase.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.leaningfirebase.R
import com.example.leaningfirebase.Routes.Screen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay


@Composable
fun Splash(navController: NavController) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.firebaselogo), contentDescription = "",
            modifier = Modifier.size(200.dp)
        )
    }

    LaunchedEffect(true) {
        delay(3000)

        navController.navigate(Screen.LogIn.screen) {
            popUpTo(navController.graph.startDestinationId)
            launchSingleTop = true
        }

    }
}