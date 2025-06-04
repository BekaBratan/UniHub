package com.example.unihub.presentation.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unihub.data.api.ServiceBuilder
import com.example.unihub.data.model.MessageResponse
import com.example.unihub.data.model.auth.PasswordChangeRequest
import com.example.unihub.data.model.club.ClubsResponse
import com.example.unihub.data.model.post.PostsResponse
import com.example.unihub.data.model.post.PostsResponseItem
import com.example.unihub.data.model.ticket.MyTicketsResponse
import com.example.unihub.data.model.ticket.PendingTicketsResponse
import com.example.unihub.data.model.ticket.PendingTicketsResponseItem
import com.example.unihub.data.model.users.UpdateUserProfileRequest
import com.example.unihub.data.model.users.UserProfileResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class NotificationsViewModel(): ViewModel() {

    private var _getTicketsResponse: MutableLiveData<MyTicketsResponse> = MutableLiveData()
    val getTicketsResponse: LiveData<MyTicketsResponse> = _getTicketsResponse

    private var _getPendingTicketsResponse: MutableLiveData<PendingTicketsResponse> = MutableLiveData()
    val getPendingTicketsResponse: LiveData<PendingTicketsResponse> = _getPendingTicketsResponse

    private var _approveResponse: MutableLiveData<MessageResponse> = MutableLiveData()
    val approveResponse: LiveData<MessageResponse> = _approveResponse

    private var _rejectResponse: MutableLiveData<MessageResponse> = MutableLiveData()
    val rejectResponse: LiveData<MessageResponse> = _rejectResponse

    private var _errorMessage: MutableLiveData<MessageResponse> = MutableLiveData()
    val errorMessage: LiveData<MessageResponse> = _errorMessage

    fun getTickets(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getTickets(
                    token = token
                )
            }.fold(
                onSuccess = {
                    _getTicketsResponse.postValue(it)
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

    fun getPendingTickets(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getPendingTickets(
                    token = token
                )
            }.fold(
                onSuccess = {
                    _getPendingTicketsResponse.postValue(it)
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

    fun approveTicketRequest(token: String, ticketId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.approveTicket(
                    token = token,
                    ticketId = ticketId
                )
            }.fold(
                onSuccess = {
                    _approveResponse.postValue(it)
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

    fun rejectTicketRequest(token: String, ticketId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.rejectTicket(
                    token = token,
                    ticketId = ticketId
                )
            }.fold(
                onSuccess = {
                    _rejectResponse.postValue(it)
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