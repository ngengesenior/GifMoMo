package com.ngengeapps.gifmomo

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import com.google.firebase.auth.FirebaseUser
import com.ngengeapps.gifmomo.model.User
import java.text.NumberFormat
import java.util.*


// Find the closest Activity
tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    else -> (this as? ContextWrapper)?.baseContext?.findActivity()
}

fun getXAFFormatter():NumberFormat {
    val format = NumberFormat.getInstance()
    format.currency = Currency.getInstance("XAF")
    return format
}

fun String.formatIntoThousands():String {
    try {
        return if (this.isEmpty()) "" else String.format("%,d",this.toLong())
    }catch (ex:NumberFormatException){
        throw ex
    }

}

fun FirebaseUser.toUser(): User {
    return User(
        id = uid,
        name = displayName.orEmpty(),
        email = email.orEmpty(),
        phone = phoneNumber.orEmpty()
    )
}
