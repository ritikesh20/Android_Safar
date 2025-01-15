package com.example.leaningfirebase.item_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.leaningfirebase.R
import com.example.leaningfirebase.model.ThreadModel
import com.example.leaningfirebase.model.UserModel
import com.example.leaningfirebase.utils.SharedPref
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun ThreadItem(
    thread: ThreadModel,
    users: UserModel,
    navController: NavController,
    userId: String
) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Logo Image
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Image(
                    painter = painterResource(id = R.drawable.firebaselogo),
                    contentDescription = "HomeLogo",
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // User Profile and Post Time
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                Image( // User profile image
                    painter = rememberAsyncImagePainter(model = users.imageUrl),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text( // User name
                    text = users.name,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Spacer(modifier = Modifier.width(80.dp))

                Column {
                    Text(text = "10:10 AM")
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "06 Jan 25")
                }
            }

            // Thread content
            Text(
                text = thread.thread,
                modifier = Modifier.padding(start = 20.dp, end = 10.dp)
            )

            // Post Image (if available)
            if (thread.image.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(model = thread.image),
                    contentDescription = "Post Image",
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                        .clip(RoundedCornerShape(40.dp))
                )
            }

            // Divider
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        }
    }
}



//@Composable
//fun ThreadItem(
//    thread: ThreadModel,
//    users: UserModel,
//    navController: NavController,
//    userId: String
//) {
//
//    val context = LocalContext.current
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        Column(
//            modifier = Modifier.fillMaxSize()
//        ) {
//
//            // logo image
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Spacer(modifier = Modifier.height(10.dp))
//
//                Image(
//                    painter = painterResource(id = R.drawable.firebaselogo),
//                    contentDescription = "HomeLogo",
//                    modifier = Modifier.size(50.dp)
//                )
//            }
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 20.dp, end = 20.dp)
//            ) {
//                Image( // userprofileImage
//                    painter = rememberAsyncImagePainter(model = users.imageUrl),
//                    contentDescription = "Profile Image",
//                    modifier = Modifier
//                        .size(50.dp)
//                        .clip(CircleShape)
//                )
//
//                Spacer(modifier = Modifier.width(10.dp))
//
//                Text( // UserName
//                    text = users.name,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(top = 10.dp)
//                )
//
//                Spacer(modifier = Modifier.width(80.dp))
//
//                Column {
//                    Text(text = "10:10 Am")
//                    Spacer(modifier = Modifier.height(5.dp))
//                    Text(text = "06 Jan 25")
//                }
//            }
//
//            // userContent
//            Text(
//                text = thread.thread,
//                modifier = Modifier.padding(start = 20.dp, end = 10.dp)
//            )
//
//            // userPostImage
//            if (thread.image != "") {
//                Image(
//                    painter = rememberAsyncImagePainter(model = thread.image),
//                    contentDescription = "post Image",
//                    modifier = Modifier
//                        .padding(start = 20.dp, end = 20.dp)
//                        .clip(
//                            RoundedCornerShape(40.dp)
//                        )
//                )
//            }
//
//            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
//        }
//    }
//}
