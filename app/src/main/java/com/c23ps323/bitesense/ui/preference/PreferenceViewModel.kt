package com.c23ps323.bitesense.ui.preference

import androidx.lifecycle.ViewModel
import com.c23ps323.bitesense.data.Repository
import com.google.gson.JsonElement

class PreferenceViewModel(private val repository: Repository) : ViewModel() {
    val getUserHealthCondition = repository.getUserHealthCondition()

    fun updatePreference(preference: JsonElement) = repository.updatePreference(preference)
}