package com.ngengeapps.gifmomo.model

sealed class Response {
    object NotInitialized : Response()
    class Loading(val message: String?) : Response()
    class Success(val message: String?) : Response()
    class Error(val exception: Throwable?) : Response()
}