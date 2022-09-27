package com.ngengeapps.gifmomo.ui.screens.donation_ui

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ngengeapps.gifmomo.R
import com.ngengeapps.gifmomo.databinding.ActivityDonationBinding
import com.ngengeapps.gifmomo.model.PaymentResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.compay.android.models.requests.CollectionRequest
import java.util.*

@AndroidEntryPoint
class DonationActivity : AppCompatActivity() {
    private val viewModel: DonationViewModel by viewModels()
    private lateinit var binding:ActivityDonationBinding
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonationBinding.inflate(layoutInflater)
        progressDialog = ProgressDialog(this)
        supportActionBar?.title = "How much are you donating?"
        setPayClickListener()
        setContentView(binding.root)

    }

    private fun setPayClickListener() {
        binding.buttonDonate.setOnClickListener{
            progressDialog.setMessage("Dial *126# or confirm payment")
            progressDialog.show()

            if (binding.amountField.text?.isNotEmpty() == false) {
                Toast.makeText(this,"Please provide an amount",Toast.LENGTH_LONG)
                    .show()
            } else {
                progressDialog.show()
                makePayment()
            }

        }
    }

    private fun makePayment() {
        val request = CollectionRequest.CollectionRequestBuilder.aCollectionRequest()
            .withAmount("100")
            .withCurrency("XAF")
            .withFrom(Firebase.auth.currentUser?.phoneNumber)
            .withExternalReference(UUID.randomUUID().toString())
            .withDescription("Sample description")
            .build()

        viewModel.makePayment(request)
        lifecycleScope.launch {
            viewModel.paymentRequestResponse.collect {
                when(it) {
                    is PaymentResponse.PaymentSucceeded,is PaymentResponse.PaymentFailed -> {
                        progressDialog.hide()
                    }
                    else -> {}
                }
            }
        }
    }
}