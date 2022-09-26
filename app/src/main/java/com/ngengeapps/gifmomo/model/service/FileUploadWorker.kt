/*
package com.ngengeapps.gifmomo.model.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.widget.ImageView
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class FileUploadWorker(ctx:Context,
                       workParams:WorkerParameters,val imageUri: Uri,
val collectionRefId:String,val fieldToUpdate:String,val path:String): Worker(ctx,workParams) {

    val collectionRefId = workParams.inputData.getString("")

    override fun doWork(): Result {
        val storageRef = Firebase.storage.reference
        val ref = Firebase.firestore.collection(path)
            .document(collectionRefId)
        val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")
        val appContext = applicationContext
        var result:Result? = null

        try {
             val stream = appContext.contentResolver.openInputStream(imageUri)
             if (stream != null){
                 val uploadTask = imageRef.putStream(stream)
                 val urlTask = uploadTask.continueWithTask { task->
                     if (!task.isSuccessful){
                         task.exception?.let {
                             throw  it
                         }
                     }
                     imageRef.downloadUrl

                 }.addOnCompleteListener {
                     if (it.isSuccessful){
                         result = Result.success()
                     } else {
                         it.result.toString()
                     }
                 }

                     .addOnFailureListener{
                     result = Result.retry()
                     return@addOnFailureListener

                 }.addOnSuccessListener { snapshot->
                     result = Result.success()


                     return@addOnSuccessListener

                 }
             }
             return result!!

         } catch (ex:Exception){
             return Result.failure()

         }



    }
}*/
