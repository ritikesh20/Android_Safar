package com.example.leaningfirebase.ViewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leaningfirebase.model.ThreadModel
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.storage
import java.util.UUID


class AddThreadViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("threads")

    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("threads/${UUID.randomUUID()}.jpg")

    private val _isPosted = MutableLiveData<Boolean>()
    val isPosted: LiveData<Boolean> = _isPosted

    fun saveImage(thread: String, userId: String, imageUri: Uri) {
        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                saveData(thread, userId, it.toString())
            }
        }
    }

    fun saveData(thread: String, userId: String, image: String) {
        val threadData = ThreadModel(thread, userId, System.currentTimeMillis().toString())

        userRef.child(userRef.push().key!!).setValue(threadData)
            .addOnSuccessListener {
                _isPosted.postValue(true)
            }
            .addOnFailureListener {
                _isPosted.postValue(false)
            }
    }

}