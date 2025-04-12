package com.example.unihub.presentation.home.club

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unihub.data.api.ServiceBuilder
import com.example.unihub.data.model.Club
import com.example.unihub.data.model.ClubsResponse
import com.example.unihub.data.model.EventsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClubViewModel(): ViewModel() {
    private var _clubResponse: MutableLiveData<Club> = MutableLiveData()
    val clubResponse: LiveData<Club> = _clubResponse

    private var _clubsResponse: MutableLiveData<ClubsResponse> = MutableLiveData()
    val clubsResponse: LiveData<ClubsResponse> = _clubsResponse

    private var _eventsResponse: MutableLiveData<EventsResponse> = MutableLiveData()
    val eventsResponse: LiveData<EventsResponse> = _eventsResponse

    private var _errorMessage: MutableLiveData<String?> = MutableLiveData()
    val errorMessage: LiveData<String?> = _errorMessage

    fun getClubById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getClubById(id)
            }.fold(
                onSuccess = {
                    _clubResponse.postValue(it)
                },
                onFailure = {
                    _errorMessage.postValue(it.localizedMessage)
                }
            )
        }
    }

    fun getClubs() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getClubs()
            }.fold(
                onSuccess = {
                    _clubsResponse.postValue(it)
                },
                onFailure = {
                    _errorMessage.postValue(it.localizedMessage)
                }
            )
        }
    }

    fun getEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.getEvents()
            }.fold(
                onSuccess = {
                    _eventsResponse.postValue(it)
                },
                onFailure = {
                    _errorMessage.postValue(it.localizedMessage)
                }
            )
        }
    }
}