package com.example.leaningfirebase.ViewModel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leaningfirebase.model.ThreadModel
import com.example.leaningfirebase.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    val thread = db.getReference("threads")

    private var _threadsAndUser = MutableLiveData<List<Pair<ThreadModel, UserModel>>>()
    val threadsAnduser: LiveData<List<Pair<ThreadModel, UserModel>>> = _threadsAndUser

    init {
        fetchThreadsAndUser {
            _threadsAndUser.value = it
        }
    }

    private fun fetchThreadsAndUser(onResult: (List<Pair<ThreadModel, UserModel>>) -> Unit) {

        thread.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val result = mutableListOf<Pair<ThreadModel, UserModel>>()

                for (threadSnapshot in snapshot.children) {
                    val thread = threadSnapshot.getValue(ThreadModel::class.java)
                    thread.let {
                        fetchFromThread(it!!) { user ->
                            result.add(0, it to user)
                            if (result.size == snapshot.childrenCount.toInt()) {
                                onResult(result)
                                Log.d("HomeViewModel", "Fetched ${result.size} threads with users.")
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


    fun fetchFromThread(thread: ThreadModel, onResult: (UserModel) -> Unit) {
        db.getReference("users").child(thread.userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserModel::class.java)
                    user?.let {
                        onResult(it)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }
}

