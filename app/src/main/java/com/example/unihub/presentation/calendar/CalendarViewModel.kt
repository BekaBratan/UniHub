package com.example.unihub.presentation.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unihub.data.api.ServiceBuilder
import com.example.unihub.data.model.MessageResponse
import com.example.unihub.data.model.calendar.CombinedCalendarResponse
import com.example.unihub.data.model.calendar.CreatePersonalEvent
import com.example.unihub.data.model.calendar.PersonalCalendarResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class CalendarViewModel(): ViewModel() {
    private var _combinedCalendarResponse: MutableLiveData<CombinedCalendarResponse> = MutableLiveData()
    val combinedCalendarResponse: LiveData<CombinedCalendarResponse> = _combinedCalendarResponse

    private var _personalCalendarResponse: MutableLiveData<PersonalCalendarResponse> = MutableLiveData()
    val personalCalendarResponse: LiveData<PersonalCalendarResponse> = _personalCalendarResponse

    private var _messageResponse: MutableLiveData<MessageResponse> = MutableLiveData()
    val messageResponse: LiveData<MessageResponse> = _messageResponse

    private var _errorMessage: MutableLiveData<MessageResponse> = MutableLiveData()
    val errorMessage: LiveData<MessageResponse> = _errorMessage

    fun getCombinedCalendar(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getCombinedCalendar(token = token)
            }.fold(
                onSuccess = {
                    _combinedCalendarResponse.postValue(it)
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

    fun getPersonalCalendar(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getPersonalCalendar(token = token)
            }.fold(
                onSuccess = {
                    _personalCalendarResponse.postValue(it)
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

    fun deleteEvent(token: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.deletePersonalEvent(token = token, eventId = id)
            }.fold(
                onSuccess = {
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

    fun createPersonalEvent(token: String, event: CreatePersonalEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.createPersonalEvent(token = token, event = event)
            }.fold(
                onSuccess = {
                    _messageResponse.postValue(it)
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