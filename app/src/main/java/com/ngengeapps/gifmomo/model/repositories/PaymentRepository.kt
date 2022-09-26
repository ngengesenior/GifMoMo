package com.ngengeapps.gifmomo.model.repositories

import com.ngengeapps.gifmomo.model.CamPayUser
import com.ngengeapps.gifmomo.model.PaymentRequestBody
import com.ngengeapps.gifmomo.model.Token
import com.ngengeapps.gifmomo.rest.CamPayRestService
import javax.inject.Inject

class PaymentRepository @Inject constructor(private val restService: CamPayRestService) {

    suspend fun getToken(user: CamPayUser):Token = restService.retrieveToken(user)

    suspend fun createPayment(request:PaymentRequestBody, authHeader:String) = restService.collect(request,authHeader)

    suspend fun requestPaymentStatus(ref:String,authHeader:String) = restService.requestTransactionStatus(ref,authHeader)

}