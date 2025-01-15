package com.example.leaningfirebase.Screen

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.leaningfirebase.R
import com.example.leaningfirebase.Routes.Screen
import com.example.leaningfirebase.ViewModel.AddThreadViewModel
import com.example.leaningfirebase.utils.SharedPref
import com.google.firebase.auth.FirebaseAuth


// View -> UI part
@Composable
fun AddThread(navController: NavController) {

    val context = LocalContext.current

    val threadViewModel: AddThreadViewModel = viewModel() // inherent class AddThreadViewModel
    val isPost by threadViewModel.isPosted.observeAsState(false)

    var postText by remember {
        mutableStateOf("")
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcherX =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent())
        { uri: Uri? ->
            imageUri = uri
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {

                isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permission Not Granted", Toast.LENGTH_SHORT).show()
            }

        }

    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    LaunchedEffect(isPost) {
        if (isPost!!) {
            postText = ""
            imageUri = null
            Toast.makeText(context, "Thread Added Successful", Toast.LENGTH_SHORT).show()

            navController.navigate(Screen.Home.screen) {
                popUpTo(Screen.AddThread.screen) {
                    inclusive = true
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .size(height = 100.dp, width = 450.dp)
                .padding(20.dp),
        ) {

            Icon(imageVector = Icons.Default.Close, contentDescription = null,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.Home.screen) {
                        popUpTo(Screen.AddThread.screen) {
                            inclusive = true
                        }
                    }
                })

            Spacer(modifier = Modifier.width(30.dp))

            Text(
                text = "New Thread",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 60.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = SharedPref.getImage(context)),
//                painter = painterResource(id = R.drawable.apple), // user profile image
                contentDescription = "",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = SharedPref.getUserName(context),
//                text = "ritikesh_singh", // user Name
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp)
            )

        }


        if (imageUri == null) {
            Image(painter = painterResource(id = R.drawable.baseline_attachment_24),
                contentDescription = "Add Image",
                modifier = Modifier.clickable {
                    val isGranted = ContextCompat.checkSelfPermission(
                        context,
                        permissionToRequest
                    ) == PackageManager.PERMISSION_GRANTED
                    if (isGranted) {
                        launcherX.launch("image/*")
                    } else {
                        permissionLauncher.launch(permissionToRequest)
                    }
                }
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(model = imageUri),
                contentDescription = "AddImage",
                modifier = Modifier.size(200.dp)
            )
            Icon(imageVector = Icons.Default.Close, contentDescription = "Remove Image",
                modifier = Modifier.clickable {
                    imageUri = null
                }
            )
        }


        OutlinedTextField(
            value = postText, onValueChange = { postText = it },
            label = { Text(text = "Write Thread") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )


        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Anyone can reply",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            TextButton(
                onClick = {
                    if (imageUri == null) {
                        threadViewModel.saveData(
                            postText,
                            FirebaseAuth.getInstance().currentUser!!.uid,
                            ""
                        )
                    } else {
                        threadViewModel.saveImage(
                            postText,
                            FirebaseAuth.getInstance().currentUser!!.uid,
                            imageUri!!
                        )
                    }
                },
            ) {
                Text(
                    text = "Post",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}