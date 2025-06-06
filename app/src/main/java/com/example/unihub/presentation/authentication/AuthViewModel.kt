package com.example.unihub.presentation.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unihub.data.api.ServiceBuilder
import com.example.unihub.data.model.auth.LoginRequest
import com.example.unihub.data.model.auth.LoginResponse
import com.example.unihub.data.model.MessageResponse
import com.example.unihub.data.model.auth.SignUpErrorMessage
import com.example.unihub.data.model.auth.SignUpRequest
import com.example.unihub.data.model.auth.SignUpResponse
import com.example.unihub.data.model.auth.VerifyEmailRequest
import com.example.unihub.data.model.auth.VerifyEmailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class AuthViewModel(): ViewModel() {
    private var _loginResponse: MutableLiveData<LoginResponse> = MutableLiveData()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private var _signupResponse: MutableLiveData<SignUpResponse> = MutableLiveData()
    val signupResponse: LiveData<SignUpResponse> = _signupResponse

    private var _verifyEmailResponse: MutableLiveData<VerifyEmailResponse> = MutableLiveData()
    val verifyEmailResponse: LiveData<VerifyEmailResponse> = _verifyEmailResponse

    private var _errorMessage: MutableLiveData<MessageResponse> = MutableLiveData()
    val errorMessage: LiveData<MessageResponse> = _errorMessage

    private var _signUpErrorMessage: MutableLiveData<SignUpErrorMessage> = MutableLiveData()
    val signUpErrorMessage: LiveData<SignUpErrorMessage> = _signUpErrorMessage

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.login(loginBody = LoginRequest(email = email, password = password))
            }.fold(
                onSuccess = {
                    _loginResponse.postValue(it)
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

    fun signup(name: String, surname: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.register(SignUpRequest(name = name, surname = surname, email = email, password = password))
            }.fold(
                onSuccess = {
                    _signupResponse.postValue(it)
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

                    _signUpErrorMessage.postValue(SignUpErrorMessage(
                        error = errorMessage,
                        message = errorMessage
                    ))
                }
            )
        }
    }

    fun verifyEmail(email: String, code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.verifyEmail(VerifyEmailRequest(email = email, code = code))
            }.fold(
                onSuccess = {
                    _verifyEmailResponse.postValue(it)
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