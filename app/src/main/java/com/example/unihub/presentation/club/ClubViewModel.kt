package com.example.unihub.presentation.club

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unihub.data.api.ServiceBuilder
import com.example.unihub.data.model.MessageResponse
import com.example.unihub.data.model.auth.PasswordChangeRequest
import com.example.unihub.data.model.club.ClubsResponse
import com.example.unihub.data.model.club.ClubsResponseItem
import com.example.unihub.data.model.post.CreatePostRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class ClubViewModel(): ViewModel() {
    private var _clubResponse: MutableLiveData<ClubsResponseItem> = MutableLiveData()
    val clubResponse: LiveData<ClubsResponseItem> = _clubResponse

    private var _createPostResponse: MutableLiveData<MessageResponse> = MutableLiveData()
    val createPostResponse: LiveData<MessageResponse> = _createPostResponse

    private var _clubsResponse: MutableLiveData<ClubsResponse> = MutableLiveData()
    val clubsResponse: LiveData<ClubsResponse> = _clubsResponse

    private var _errorMessage: MutableLiveData<MessageResponse> = MutableLiveData()
    val errorMessage: LiveData<MessageResponse> = _errorMessage


    fun createPost(token: String, content: String, image: String, title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                ServiceBuilder.api.createPost(
                    token = token,
                    post = CreatePostRequest(
                        content = content,
                        image = image,
                        title = title
                    )
                )
            }.fold(
                onSuccess = {
                    _createPostResponse.postValue(it)
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