package com.ngengeapps.gifmomo.rest

import com.ngengeapps.gifmomo.model.CamPayUser
import com.ngengeapps.gifmomo.model.PaymentRequestBody
import com.ngengeapps.gifmomo.model.WithdrawalRequest
import net.compay.android.models.WithdrawalResponse
import net.compay.android.models.requests.CollectionRequest
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface CamPayRestService {
    companion object {
        private const val CAM_PAY_BASE_URL = "https://demo.campay.net/api/"
        fun create(httpUrl: HttpUrl):CamPayRestService {

            val client = OkHttpClient.Builder()
                .build()

            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(CamPayRestService::class.java)

        }

        fun create():CamPayRestService = create(CAM_PAY_BASE_URL.toHttpUrlOrNull()!!)
    }
    @POST("token/")
    suspend fun retrieveToken(@Body user: CamPayUser): com.ngengeapps.gifmomo.model.Token

    @POST("collect/")
    suspend fun collect(
        @Body request: PaymentRequestBody,
        @Header("Authorization") authorizationHeader: String
    ): com.ngengeapps.gifmomo.model.PaymentRequestResponse

    @GET("transaction/{id}/")
    suspend fun requestTransactionStatus(
        @Path("id") reference: String,
        @Header("Authorization") authorizationHeader: String
    ): com.ngengeapps.gifmomo.model.TransactionStatusResponse

    @POST("api/withdraw/")
    suspend fun withdraw(
        @Body request: WithdrawalRequest,
        @Header("Authorization") authorizationHeader: String,
    ): WithdrawalResponse

}
