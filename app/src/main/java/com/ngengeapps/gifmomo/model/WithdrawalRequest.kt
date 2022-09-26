package com.ngengeapps.gifmomo.model

import com.google.gson.annotations.SerializedName

data class WithdrawalRequest(
    val amount:Long,
    val to:String,
   @SerializedName("external_reference") val reference:String,
    val description:String
)
