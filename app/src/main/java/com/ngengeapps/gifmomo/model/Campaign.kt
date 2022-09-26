package com.ngengeapps.gifmomo.model

import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.ktx.Firebase
import java.util.*


data class Campaign(
    val id:String = UUID.randomUUID().toString(),
    val title:String,
    val description:String,
    val latitude:Double = 0.0,
    val longitude:Double = 0.0,
    val creatorId:String = Firebase.auth.currentUser?.uid?:"",
    @ServerTimestamp
    val startDate: Date? = null,
    val endDate: String,
    val geoHash:String = GeoFireUtils.getGeoHashForLocation(GeoLocation(latitude, longitude)),
    val targetAmount:Long,
    val currentRaised:Long = 0,
    val placeName:String = "",
    val imageUrl:String = "",
    val category:String,
)

val fakeCampaign = Campaign(
    title = "Roger's Hospital bill",
    creatorId = UUID.randomUUID().toString(),
    targetAmount = 230000,
    currentRaised = 100000,
    category = "Education",
    endDate = "20/02/2022",
    description = "Random description of whatever campaign"

)
