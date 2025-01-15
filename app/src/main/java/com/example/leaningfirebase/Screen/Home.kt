package com.example.leaningfirebase.Screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.leaningfirebase.ViewModel.HomeViewModel
import com.example.leaningfirebase.item_view.ThreadItem
import com.google.firebase.auth.FirebaseAuth


@Composable
fun Home(navController: NavController) {
    val homeViewModel: HomeViewModel = viewModel()
    val threadAndUser by homeViewModel.threadsAnduser.observeAsState(emptyList())


    if (threadAndUser.isEmpty()){
        Text(text = "No Data Available")
    }
    else{
        LazyColumn {
            items(threadAndUser) { pair ->
                ThreadItem(
                    thread = pair.first,
                    users = pair.second,
                    navController = navController,
                    userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                )
            }
        }
    }
}


























//
//@Composable
//fun Home(navController: NavController) {
//
//    val context = LocalContext.current
//    val homeViewModel: HomeViewModel = viewModel()
//    val threadAnduser by homeViewModel.threadsAnduser.observeAsState(emptyList())
//
//    val currentUser = FirebaseAuth.getInstance().currentUser
//
//
//    LazyColumn {
//        items(threadAnduser) { pair ->
//            ThreadItem(
//                thread = pair.first,
//                users = pair.second,
//                navController = navController,
//                userId = currentUser?.uid? ""
//            )
//        }
//    }
//
//
//}

