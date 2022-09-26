package com.ngengeapps.gifmomo.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode


class MapLauncherContract : ActivityResultContract<Context, Place?>() {
    override fun createIntent(context: Context, input: Context): Intent {
        val fields = listOf(
            Place.Field.ID, Place.Field.NAME,
            Place.Field.LAT_LNG, Place.Field.UTC_OFFSET
        )
        return Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Place? {
        return when (resultCode) {
            Activity.RESULT_OK -> {
                val place = Autocomplete.getPlaceFromIntent(intent!!)
                Log.i("Autocomplete", "${place.id}")
                place ?: null
            }
            else -> null

        }
    }
}




