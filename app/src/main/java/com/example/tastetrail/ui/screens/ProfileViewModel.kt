package com.example.tastetrail.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tastetrail.data.UserData
import com.example.tastetrail.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val profileState: StateFlow<ProfileState> = _profileState

    fun fetchProfile() {
        viewModelScope.launch {
            _profileState.value = ProfileState.Loading
            try {
                val response = RetrofitInstance.api.getProfile()
                if (response.isSuccessful) {
                    response.body()?.data?.user?.let {
                        _profileState.value = ProfileState.Success(it)
                    }
                } else {
                    _profileState.value = ProfileState.Error("Failed to fetch profile")
                }
            } catch (e: Exception) {
                _profileState.value = ProfileState.Error("An error occurred")
            }
        }
    }
}

sealed class ProfileState {
    object Loading : ProfileState()
    data class Success(val user: UserData) : ProfileState()
    data class Error(val message: String) : ProfileState()
}
