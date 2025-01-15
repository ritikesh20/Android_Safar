package com.example.leaningfirebase.ViewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leaningfirebase.model.ThreadModel
import com.example.leaningfirebase.model.UserModel
import com.example.leaningfirebase.utils.SharedPref
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.storage
import java.util.UUID


class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    private val userRef = db.getReference("users")

    //    Creating firebase datastore for saving images of the users
    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("users/${UUID.randomUUID()}.jpg")

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val firebaseUser: LiveData<FirebaseUser?> = _firebaseUser

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        _firebaseUser.value = auth.currentUser
    }

    fun login(email: String, password: String, context: Context) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    getDat(auth.currentUser!!.uid, context) // fb ke user ka id
                    _firebaseUser.postValue(auth.currentUser)
                } else {
//                    _error.postValue("Something Went wrong...")
                    _error.postValue(it.exception!!.message)
                }
            }
    }

    private fun getDat(uid: String, context: Context) {
        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue(UserModel::class.java)
                SharedPref.storeData(
                    userData!!.name,
                    userData!!.email,
                    userData!!.bio,
                    userData!!.userName,
                    userData!!.imageUrl,
                    context
                )
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun register(
        email: String,
        password: String,
        name: String,
        bio: String,
        userName: String,
        imageUri: Uri,
        context: Context
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _firebaseUser.postValue(auth.currentUser)
                    saveImage(
                        email = email,
                        password = password,
                        name = name,
                        bio = bio,
                        userName = userName,
                        imageUri = imageUri,
                        uid = auth.currentUser?.uid,
                        context = context
                    )
                } else {
                    _error.postValue("Something went wrong")
                }
            }
    }

    private fun saveImage(
        email: String,
        password: String,
        name: String,
        bio: String,
        userName: String,
        imageUri: Uri,
        uid: String?,
        context: Context
    ) {
        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                saveData(
                    email = email, password = password, name = name, bio = bio, userName = userName,
                    imageUrl = it.toString(), uid = uid, context = context
                )
            }
        }
    }

    private fun saveData(
        email: String,
        password: String,
        name: String,
        bio: String,
        userName: String,
        imageUrl: String,
        uid: String?,
        context: Context
    ) {

        val userData = UserModel(
            email = email, password = password, name = name, bio = bio,
            userName = userName, imageUrl = imageUrl, uid = uid!!
        )

        userRef.child(uid).setValue(userData).addOnSuccessListener {
            SharedPref.storeData(
                name = name, email = email, bio = bio,
                userName = userName, imageUrl = imageUrl, context = context
            )
        }.addOnFailureListener {

        }
    }

    //    Logout Function
    fun logout() {
        auth.signOut()
        _firebaseUser.postValue(null)
    }


}
























