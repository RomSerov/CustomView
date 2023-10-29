package com.example.customview.data

import com.example.customview.data.model.Result
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("aggs/ticker/AAPL/range/{timeframe}/2022-01-09/2023-01-09?adjusted=true&sort=desc&limit=50000&apiKey=7MqxyyYVac8etAbE0sbXhZK2KMudhvgr")
    suspend fun loadBars(
        @Path("timeframe") timeframe: String
    ): Result
}