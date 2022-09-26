package com.ngengeapps.gifmomo.ui.screens.donation_ui

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import com.ngengeapps.gifmomo.model.PaymentResponse
import com.ngengeapps.gifmomo.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.compay.android.CamPay
import net.compay.android.models.requests.CollectionRequest
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class DonationViewModel @Inject constructor(private val camPay: CamPay) :ViewModel() {
    private val TAG = DonationViewModel::class.java.simpleName
    private val _donationAmount:MutableStateFlow<String> = MutableStateFlow("")
    val donationAmount:StateFlow<String> get() = _donationAmount

    private val _paymentRequestResponse:MutableStateFlow<PaymentResponse> = MutableStateFlow(PaymentResponse.NotInitialized)
    val paymentRequestResponse:StateFlow<PaymentResponse> get() = _paymentRequestResponse

    val showDialog:MutableStateFlow<Boolean> = MutableStateFlow(false)


    fun onAmountChange(amount:String){
        _donationAmount.value = amount
    }

    fun makePayment(paymentBody:CollectionRequest){
        showDialog.value = true
        _paymentRequestResponse.value = PaymentResponse.Loading
        val req = camPay.collect(
            CollectionRequest
                .CollectionRequestBuilder
                .aCollectionRequest()
                .withAmount(paymentBody.amount)
                .withCurrency(paymentBody.currency)
                .withDescription(paymentBody.description)
                .withExternalReference(paymentBody.externalReference)
                .withFrom(paymentBody.from)
                .build())
            .delay(20,TimeUnit.SECONDS)
            .switchMap { collectionResponse->
                _paymentRequestResponse.value = PaymentResponse.PaymentInitialized
                camPay.transactionStatus(collectionResponse.reference)

                camPay.transactionStatus(collectionResponse.reference)
                    .subscribeOn(Schedulers.io())


                return@switchMap camPay.transactionStatus(collectionResponse.reference)

            }.subscribe {
                if (it.status.lowercase(Locale.getDefault()) == "failed"){
                    _paymentRequestResponse.value = PaymentResponse.PaymentFailed("Failed")
                } else if(it.status.lowercase() == "successful") {
                    showDialog.value = false
                    _paymentRequestResponse.value = PaymentResponse.PaymentSucceeded
                } else if (it.status.lowercase() == "pending"){
                    _paymentRequestResponse.value = PaymentResponse.PaymentInitialized
                }
                Log.d(TAG, "makePayment: $it")
            }
    }
}