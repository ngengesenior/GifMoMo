package com.ngengeapps.gifmomo.model

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

data class User(
    val name:String,
    val email:String,
    val phone:String,
    val id:String = Firebase.auth.currentUser?.uid.orEmpty()
)
