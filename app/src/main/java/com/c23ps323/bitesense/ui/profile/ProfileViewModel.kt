package com.c23ps323.bitesense.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c23ps323.bitesense.data.Repository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    val getUserProfile = repository.getUserProfile()

    val getUserHealthCondition = repository.getUserHealthCondition()

    fun deleteAllData() {
        viewModelScope.launch {
            repository.deleteAllData()
        }
    }

    fun saveAuthToken(token: String) {
        viewModelScope.launch {
            repository.saveAuthToken(token)
        }
    }

}