package com.example.leaningfirebase.Screen

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.leaningfirebase.R
import com.example.leaningfirebase.Routes.Screen
import com.example.leaningfirebase.ViewModel.AuthViewModel

@Composable
fun SingUpPage(navController: NavHostController) {

    val context = LocalContext.current

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var name by remember {
        mutableStateOf("")
    }

    var userName by remember {
        mutableStateOf("")
    }
    var bio by remember {
        mutableStateOf("")
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

//    Launches permission dialogue box
    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permission Not Granted", Toast.LENGTH_SHORT).show()
            }
        }
//    Defines which permissions we have to request
    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }
    LaunchedEffect(firebaseUser) {
        if(firebaseUser!= null){
            navController.navigate(Screen.BottomNav.screen) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }




    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "SingUp Page",
            fontSize = 24.sp
        )

        Image(
            painter = if (imageUri == null) painterResource(id = R.drawable.firebaselogo) else rememberAsyncImagePainter(
                model = imageUri
            ), contentDescription = "person",
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable {
//                    Checking if the permission is granted
                    val isGranted = ContextCompat.checkSelfPermission(
                        context, permissionToRequest
                    ) == PackageManager.PERMISSION_GRANTED
                    if (isGranted) {
                        launcher.launch("image/*")
                    } else {
                        permissionLauncher.launch(permissionToRequest)
                    }
                }, contentScale = ContentScale.Crop
        )


        Spacer(modifier = Modifier.height(100.dp))

        OutlinedTextField(value = name, onValueChange = { name = it },
            label = { Text(text = "Enter Name") })

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = userName, onValueChange = { userName = it },
            label = { Text(text = "Enter UserName") })

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = bio, onValueChange = { bio = it },
            label = { Text(text = "Enter Bio") })

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = email, onValueChange = { email = it },
            label = { Text(text = "Enter Email") })

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = password, onValueChange = { password = it },
            label = { Text(text = "Enter password") })

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(onClick = {

            navController.navigate(Screen.LogIn.screen) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }

        }) {
            Text(text = "Already have Account")
        }

        Spacer(modifier = Modifier.height(10.dp))

        ElevatedButton(
            onClick = {
                if (name.isEmpty() || email.isEmpty() || bio.isEmpty() || password.isEmpty() || imageUri == null) {
                    Toast.makeText(context, "Please fill all the details", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    authViewModel.register(email, password, name, bio, userName, imageUri!!, context = context)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Text(text = "SingUp", fontSize = 24.sp)
        }

        TextButton(
            onClick = {
                navController.navigate(Screen.LogIn.screen) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Already Registered? Login Here",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}