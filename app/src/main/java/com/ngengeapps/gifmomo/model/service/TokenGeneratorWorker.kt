package com.ngengeapps.gifmomo.model.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters

class TokenGeneratorWorker(private val context: Context,
workparams:WorkerParameters): CoroutineWorker(context,workparams) {
    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
    }

}