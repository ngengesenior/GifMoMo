package com.ngengeapps.gifmomo.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class PaymentRequestBody(
    val amount: Long,
    @SerializedName("from") val number: String,
    val currency: String = "XAF", val description: String = "GifMoMO donation",
    @SerializedName("external_reference") val reference:String = UUID.randomUUID().toString()
    )

