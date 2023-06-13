package com.c23ps323.bitesense.ui.editProfile

import androidx.lifecycle.ViewModel
import com.c23ps323.bitesense.data.Repository

class EditProfileViewModel(private val repository: Repository) : ViewModel() {
    fun editUsername(username: String) = repository.editUsername(username)

    fun editEmail(email: String) = repository.editEmail(email)

    fun editTelepon(telepon: String) = repository.editTelepon(telepon)
}