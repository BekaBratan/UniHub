package com.example.unihub.data.api

import com.example.unihub.data.model.club.ClubsResponse
import com.example.unihub.data.model.auth.LoginRequest
import com.example.unihub.data.model.auth.LoginResponse
import com.example.unihub.data.model.auth.SignUpRequest
import com.example.unihub.data.model.club.ClubDetailsResponse
import com.example.unihub.data.model.club.ClubEventsResponse
import com.example.unihub.data.model.event.EventDetailsResponse
import com.example.unihub.data.model.event.EventsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body loginBody: LoginRequest
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body registerBody: SignUpRequest
    ): LoginResponse




    @GET("clubs")
    suspend fun getClubs(
        @Header("Authorization") token: String
    ): ClubsResponse

    @GET("clubs/{clubId}")
    suspend fun getClubDetails(
        @Header("Authorization") token: String,
        @Path("clubId") clubId: Int
    ): ClubDetailsResponse




    @GET("events")
    suspend fun getEvents(
        @Header("Authorization") token: String
    ): EventsResponse

    @GET("events/{eventId}")
    suspend fun getEventDetails(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: Int
    ): EventDetailsResponse

    @GET("events/club/{clubId}")
    suspend fun getEventsByClub(
        @Header("Authorization") token: String,
        @Path("clubId") clubId: Int
    ): ClubEventsResponse



}