package com.ngengeapps.gifmomo.model

import com.google.gson.annotations.SerializedName


data class Token( @SerializedName("expires_in") val expiresIn: Long,val token:String)