package com.example.unihub.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unihub.data.api.ServiceBuilder
import com.example.unihub.data.model.ChangePasswordRequest
import com.example.unihub.data.model.MessageResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class ProfileViewModel(): ViewModel() {
    private var _getProfileResponse: MutableLiveData<MessageResponse> = MutableLiveData()
    val getProfileResponse: LiveData<MessageResponse> = _getProfileResponse

    private var _changePasswordResponse: MutableLiveData<MessageResponse> = MutableLiveData()
    val changePasswordResponse: LiveData<MessageResponse> = _changePasswordResponse

    private var _errorMessage: MutableLiveData<MessageResponse> = MutableLiveData()
    val errorMessage: LiveData<MessageResponse> = _errorMessage

    fun changePassword(
        currentPassword: String,
        newPassword: String,
        token: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.changePassword(
                    token = token,
                    password = ChangePasswordRequest(
                        currentPassword = currentPassword,
                        newPassword = newPassword
                    )
                )
            }.fold(
                onSuccess = {
                    _changePasswordResponse.postValue(it)
                },
                onFailure = { throwable ->
                    val errorMessage = if (throwable is HttpException) {
                        val errorBody = throwable.response()?.errorBody()?.string()
                        try {
                            val json = JSONObject(errorBody ?: "")
                            json.getString("message")
                        } catch (e: Exception) {
                            "Something went wrong."
                        }
                    } else {
                        throwable.message ?: "An unknown error occurred."
                    }

                    _errorMessage.postValue(MessageResponse(errorMessage))
                }
            )
        }
    }
}