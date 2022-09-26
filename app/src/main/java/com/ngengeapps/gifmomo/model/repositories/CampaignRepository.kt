package com.ngengeapps.gifmomo.model.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngengeapps.gifmomo.model.Campaign
import javax.inject.Inject

class CampaignRepository @Inject constructor() {
    private val campaignCollection =  Firebase.firestore.collection("campaign")
    fun createCampaign(campaign: Campaign): Task<Void> = campaignCollection
        .document(campaign.id)
        .set(campaign)

    fun deleteCampaignRequest(id:String):Task<DocumentSnapshot> = campaignCollection.document(id).get()

    fun updateCampaignImageUrl(url:String,docId:String): Task<Void> = getCampaign(docId)
            .update("imageUrl",url)

    fun getCampaign(docId:String): DocumentReference = campaignCollection.document(docId)

    fun getAllCampaigns() = campaignCollection.get()

}