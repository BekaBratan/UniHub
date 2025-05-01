package com.example.unihub.data.api

import com.example.unihub.data.model.ChangePasswordRequest
import com.example.unihub.data.model.Club
import com.example.unihub.data.model.ClubResponse
import com.example.unihub.data.model.ClubsResponse
import com.example.unihub.data.model.CreateClubRequest
import com.example.unihub.data.model.CreateClubResponse
import com.example.unihub.data.model.CreateEventRequest
import com.example.unihub.data.model.EventsResponse
import com.example.unihub.data.model.EventsResponseItem
import com.example.unihub.data.model.GetProfileResponse
import com.example.unihub.data.model.LoginRequest
import com.example.unihub.data.model.LoginResponse
import com.example.unihub.data.model.MessageResponse
import com.example.unihub.data.model.SignUpRequest
import com.example.unihub.data.model.SimpleMessageResponse
import com.example.unihub.data.model.VerificationRequest
import com.example.unihub.data.model.VerificationResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getEvents(
    ): EventsResponse

    @GET("events/{id}")
    suspend fun getEventById(
        @Path("id") id: String
    ): EventsResponseItem

    @POST("events")
    suspend fun createEvent(
        @Body event: CreateEventRequest
    ): EventsResponseItem

    @PUT("events/{id}")
    suspend fun updateEventById(
        @Path("id") id: String,
        @Body event: CreateEventRequest
    ): EventsResponseItem

    @DELETE("events/{id}")
    suspend fun deleteEventById(
        @Path("id") id: String
    ): SimpleMessageResponse


    @GET("clubs")
    suspend fun getClubs(): ClubsResponse

    @GET("clubs/{id}")
    suspend fun getClubById(
        @Path("id") id: String
    ): ClubResponse

    @POST("clubs")
    suspend fun createClub(
        @Body club: CreateClubRequest
    ): CreateClubResponse

    @POST("auth/login")
    suspend fun login(
        @Body loginBody: LoginRequest
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body registerBody: SignUpRequest
    ): MessageResponse

    @POST("auth/verify")
    suspend fun verify(
        @Body verifyBody: VerificationRequest
    ): VerificationResponse

    @POST("users/change-password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body password: ChangePasswordRequest
    ): MessageResponse

    @GET("users/profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): GetProfileResponse
}