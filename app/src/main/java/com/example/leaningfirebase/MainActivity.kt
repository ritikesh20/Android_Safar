package com.example.leaningfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.leaningfirebase.Routes.NavGraph
import com.example.leaningfirebase.ui.theme.LeaningFireBaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeaningFireBaseTheme {


                val navController = rememberNavController()

                NavGraph(navController = navController)

            }
        }
    }
}

