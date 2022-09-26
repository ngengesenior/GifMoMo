package com.ngengeapps.gifmomo.model.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngengeapps.gifmomo.model.User
import com.ngengeapps.gifmomo.toUser
import javax.inject.Inject

class UserRepository @Inject constructor() {
    private val usersCollection = Firebase.firestore.collection("users")
    fun createUser(user: User): Task<Void> {
        return usersCollection.document(user.id)
            .set(user)
    }

    fun getUserByPhone(number:String): Task<QuerySnapshot> = usersCollection.whereEqualTo("phone",number).limit(1)
            .get()
    fun createUserFromCurrent() {
        val current =Firebase.auth.currentUser
        if (current != null){
            getUserByPhone(current.phoneNumber!!)
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        createUser(current.toUser())
                    } else {
                        return@addOnCompleteListener
                    }
                }
        }
    }

}