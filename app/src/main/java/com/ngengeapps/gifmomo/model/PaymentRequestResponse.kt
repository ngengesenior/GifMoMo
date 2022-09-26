package com.ngengeapps.gifmomo.model

import com.google.gson.annotations.SerializedName

data class PaymentRequestResponse(
    val reference: String,
    @SerializedName("ussd_code") val ussdCode: String,
    val operator: String)
