package com.ayselibuyuk.clothingsalesapp.Api

import retrofit2.Call
import retrofit2.http.GET

 public interface TimeApi {
    @GET("Europe/Istanbul")
    fun getTime(): Call<TimeTurkey>
}