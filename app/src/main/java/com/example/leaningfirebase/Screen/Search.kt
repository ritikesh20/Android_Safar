package com.example.leaningfirebase.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.leaningfirebase.R


@Composable
fun Search(navController: NavController) {


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Hello Search Screen")
        Image(
            painter = painterResource(id = R.drawable.twitter), contentDescription = "",
            modifier = Modifier.size(200.dp)
        )
    }
}
