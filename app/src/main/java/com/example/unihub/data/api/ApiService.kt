package com.example.unihub.data.api

import com.example.unihub.data.model.Club
import com.example.unihub.data.model.ClubsResponse
import com.example.unihub.data.model.CreateClubRequest
import com.example.unihub.data.model.CreateClubResponse
import com.example.unihub.data.model.CreateEventRequest
import com.example.unihub.data.model.EventsResponse
import com.example.unihub.data.model.EventsResponseItem
import com.example.unihub.data.model.SimpleMessageResponse
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
    suspend fun getEvents(): EventsResponse

    @GET("events/{id}")
    suspend fun getEventById(@Path("id") id: Int): EventsResponseItem

    @POST("events")
    suspend fun createEvent(@Body event: CreateEventRequest): EventsResponseItem

    @PUT("events/{id}")
    suspend fun updateEventById(@Path("id") id: Int, @Body event: CreateEventRequest): EventsResponseItem

    @DELETE("events/{id}")
    suspend fun deleteEventById(@Path("id") id: Int): SimpleMessageResponse


    @GET("clubs")
    suspend fun getClubs(): ClubsResponse

    @GET("clubs/{id}")
    suspend fun getClubById(@Path("id") id: Int): Club

    @POST("clubs")
    suspend fun createClub(@Body club: CreateClubRequest): CreateClubResponse
}