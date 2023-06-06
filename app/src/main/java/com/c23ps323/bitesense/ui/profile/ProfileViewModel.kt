package com.c23ps323.bitesense.ui.profile

import androidx.lifecycle.ViewModel
import com.c23ps323.bitesense.data.Repository

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    val getUserProfile = repository.getUserProfile()

    val getUserHealthCondition = repository.getUserHealthCondition()
}