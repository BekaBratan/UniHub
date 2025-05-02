package com.example.unihub.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unihub.data.api.ServiceBuilder
import com.example.unihub.data.model.MessageResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class ProfileViewModel(): ViewModel() {

    private var _changePasswordResponse: MutableLiveData<MessageResponse> = MutableLiveData()
    val changePasswordResponse: LiveData<MessageResponse> = _changePasswordResponse

    private var _errorMessage: MutableLiveData<MessageResponse> = MutableLiveData()
    val errorMessage: LiveData<MessageResponse> = _errorMessage




}