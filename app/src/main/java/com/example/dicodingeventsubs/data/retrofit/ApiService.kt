package com.example.dicodingeventsubs.data.retrofit

import com.example.dicodingeventsubs.data.response.EventsResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/events")
    fun getListEvents(
        @Query("active") activeStatus : Int
    ) : Call<EventsResponse>

    @GET("/events/{id}")
    fun getDetailEvent(
        @Path("id") id: Int
    ) : Call<EventsResponse>
}