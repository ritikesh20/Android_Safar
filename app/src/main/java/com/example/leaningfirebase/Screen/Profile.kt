package com.example.leaningfirebase.Screen

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.leaningfirebase.Routes.Screen
import com.example.leaningfirebase.ViewModel.AuthViewModel


@Composable
fun Profile(navController: NavController) {

    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)



    ElevatedButton(onClick = {
        authViewModel.logout()
        navController.navigate(Screen.LogIn.screen)
    },
        modifier = Modifier.size(100.dp)

    ) {
        Text(text = "Logout")
    }

}

