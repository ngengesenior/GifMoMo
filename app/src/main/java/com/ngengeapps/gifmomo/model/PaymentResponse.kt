package com.ngengeapps.gifmomo.model

import net.compay.android.models.PaymentRequestResponse

sealed class PaymentResponse {
    object NotInitialized:PaymentResponse()
    object Loading:PaymentResponse()
    class PaymentInitialized(val paymentResponse:PaymentRequestResponse):PaymentResponse()
    object PaymentSucceeded:PaymentResponse()
    class PaymentFailed(val reason:String):PaymentResponse()
}