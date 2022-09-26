package com.ngengeapps.gifmomo.ui.screens.create_campain

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.places.api.model.Place
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ngengeapps.gifmomo.model.Campaign
import com.ngengeapps.gifmomo.model.Response
import com.ngengeapps.gifmomo.model.repositories.CampaignRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CampaignViewModel @Inject constructor(private val campaignRepo:CampaignRepository) : ViewModel() {

    init {
        getCampaigns()
    }
    val categories: MutableList<Pair<Int, String>> = mutableListOf(
        0 to "Medical",
        1 to "Emergency",
        2 to "Business",
        3 to "Events",
        4 to "Education",
        5 to "Community",
        6 to "Religious",
        7 to "Project",
        8 to "Sports",
        9 to "Memorial",
        10 to "Wedding",
        11 to "Others"
    )

    val createCampaignResponse:MutableStateFlow<Response> = MutableStateFlow(Response.NotInitialized)
    private val addCampaignAmountResponse:MutableStateFlow<Response> = MutableStateFlow(Response.NotInitialized)
    private val updateImageResponse:MutableStateFlow<Response> = MutableStateFlow(Response.NotInitialized)

    private val _showCreateDialog:MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showCreateDialog:StateFlow<Boolean> get() = _showCreateDialog

    fun  updateCreateDialog(value:Boolean){
        _showCreateDialog.value = value
    }

    private val _campaigns:MutableStateFlow<List<Campaign>> = MutableStateFlow(
        listOf()
    )
    val campaigns:StateFlow<List<Campaign>> get() = _campaigns

    private val _currentUser:MutableStateFlow<String> = MutableStateFlow( Firebase.auth.currentUser?.uid.orEmpty())
    val currentUser:StateFlow<String> get() = _currentUser
    private val _selectedCategoryIndex: MutableStateFlow<Pair<Int, String>> =
        MutableStateFlow(-1 to "")
    val selectedCategoryIndex: StateFlow<Pair<Int, String>> get() = _selectedCategoryIndex

    private val _title: MutableStateFlow<String> = MutableStateFlow("")
    val title: StateFlow<String> get() = _title

    private val _description: MutableStateFlow<String> = MutableStateFlow("")
    val description: StateFlow<String> get() = _description

    private val _amount: MutableStateFlow<String> = MutableStateFlow("")
    val amount: StateFlow<String> get() = _amount

    private val _place: MutableStateFlow<Place?> = MutableStateFlow(null)
    val place: StateFlow<Place?> get() = _place

    private val calendar = Calendar.getInstance(TimeZone.getDefault())

    private val _startDate:MutableStateFlow<String> = MutableStateFlow("${calendar.get(Calendar.DAY_OF_MONTH)}/${
        calendar.get(
            Calendar.MONTH
        )
    }/${calendar.get(Calendar.YEAR)}")
    val startDate:StateFlow<String> get() = _startDate

    private val _endDate:MutableStateFlow<String> = MutableStateFlow("${calendar.get(Calendar.DAY_OF_MONTH)}/${
        calendar.get(
            Calendar.MONTH
        )
    }/${calendar.get(Calendar.YEAR)}")
    val endDate:StateFlow<String> get() = _endDate

    private val _imageUri:MutableStateFlow<Uri?> = MutableStateFlow(null)
    val imageUri:StateFlow<Uri?> get() = _imageUri


    fun updateStartDate(newDate: String){
        _startDate.value = newDate
    }

    fun selectImageFromGallery(newUri:Uri) {
        _imageUri.value = newUri
    }

    fun updateEndDate(newDate: String){
        _endDate.value = newDate

    }


    fun onChangePlace(place: Place) {
        _place.value = place
    }

    fun onTitleChanged(title: String) {
        _title.value = title
    }

    fun onDescriptionChanged(description: String) {
        _description.value = description
    }

    fun onAmountChanged(amount: String) {
        _amount.value = amount
    }

    fun selectIndex(index: Int, value: String) {
        _selectedCategoryIndex.value = index to value
    }

    fun resetSelected() {
        selectIndex(-1, "")
        categories
    }

    fun createCampaign(campaign: Campaign) {
        if (currentUser.value.isNotEmpty()) {
            createCampaignResponse.value = Response.Loading(message = "Wait while we create your campaign")
            campaignRepo.createCampaign(campaign)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        createCampaignResponse.value = Response.Success(message = "Campaign successfully created")
                        updateCreateDialog(false)

                    } else {
                        val ex = it.exception
                        createCampaignResponse.value = Response.Error(ex)
                        updateCreateDialog(false)
                    }
                }
        } else {
            createCampaignResponse.value = Response.Error(Exception("No user is logged in"))
        }

    }

    fun getCampaigns() {
        viewModelScope.launch {
            val resp = campaignRepo.getAllCampaigns().await()
                .toObjects(Campaign::class.java)
            _campaigns.value = resp
        }
    }

    fun restoreCreationResponse() {
        createCampaignResponse.value = Response.NotInitialized
    }

    fun addCurrentRaisedAmount(docId:String,amountToAdd:Long) {
        addCampaignAmountResponse.value = Response.Loading("Updating amount raised")
        campaignRepo.getCampaign(docId)
            .get()
            .addOnCompleteListener { snapShot->

                if (snapShot.isSuccessful) {
                    addCampaignAmountResponse.value = Response.Loading("Campaign gotten")
                    val campaignAmount = snapShot.result.toObject<Campaign>()?.currentRaised?:0L

                    campaignRepo.getCampaign(docId)
                        .update("currentRaised",campaignAmount + amountToAdd)
                        .addOnCompleteListener { task->
                            if (task.isSuccessful) {
                                addCampaignAmountResponse.value = Response.Success("Raised amount Updated")
                            } else {
                                addCampaignAmountResponse.value = Response.Error(task.exception)
                                // Spin a worker to update the amount when there is maybe network
                            }
                        }
                } else {
                    addCampaignAmountResponse.value = Response.Error(Exception("Could not get campaign with Id: $docId"))

                }
            }
    }

    fun updateImageUrl(url:String,docId:String) {
        updateImageResponse.value = Response.Loading("Updating image")
        campaignRepo.updateCampaignImageUrl(url, docId)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateImageResponse.value = Response.Success("Updated image")
                } else {
                    updateImageResponse.value = Response.Error(it.exception)
                }
            }
    }

}