package com.jkou.tkd_app.network

import com.jkou.tkd_app.model.EventResponse
import com.jkou.tkd_app.model.Event
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EventApi {
    @POST("/events")
    fun enviarEvento(@Body event: Event): Call<EventResponse>
}