package ru.example.myapplication.datalayer

import retrofit2.http.GET
import ru.example.myapplication.objects.ApiListResult
import ru.example.myapplication.objects.Item

interface IGiphyApi {
    @GET("v1/gifs/trending")
    suspend fun trending(): ApiListResult<Item>
}