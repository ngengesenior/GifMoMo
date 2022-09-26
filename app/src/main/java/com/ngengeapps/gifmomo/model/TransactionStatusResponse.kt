package com.ngengeapps.gifmomo.model

import com.google.gson.annotations.SerializedName
import java.lang.ref.PhantomReference

data class TransactionStatusResponse(
    val amount: Long,
    val status:String,
    val reference: String,
    val currency:String,
    val operator:String,
    val code:String,
    @SerializedName("operator_reference")val operatorReference:String

)