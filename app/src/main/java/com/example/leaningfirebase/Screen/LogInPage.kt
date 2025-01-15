package com.example.leaningfirebase.Screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.leaningfirebase.Routes.Screen
import com.example.leaningfirebase.ViewModel.AuthViewModel

@Composable
fun LogInfoPage(navController: NavHostController) {


    var context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState()
    val error by authViewModel.error.observeAsState()

    var email by remember {
        mutableStateOf("")
    }


    var password by remember {
        mutableStateOf("")
    }


    LaunchedEffect(firebaseUser) {
        if (firebaseUser != null) {
            navController.navigate(Screen.BottomNav.screen) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

    error?.let {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }



    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "LogIn Page",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(100.dp))

        OutlinedTextField(value = email, onValueChange = { email = it },
            label = { Text(text = "Enter Email") })

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = password, onValueChange = { password = it },
            label = { Text(text = "Enter password") })

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(onClick = {
            navController.navigate(Screen.SingUp.screen) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }) {
            Text(text = "New User? Create Account")
        }

        Spacer(modifier = Modifier.height(10.dp))

        ElevatedButton(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Invalid Entry", Toast.LENGTH_SHORT).show()
                } else {
                    authViewModel.login(email, password, context)
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Text(text = "LogIn", fontSize = 24.sp)
        }

    }
}