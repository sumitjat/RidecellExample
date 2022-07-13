package com.example.ridecellexample.data

import com.example.ridecellexample.data.model.Characters
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/api/character")
    suspend fun getCharacters(
        @Query("page") query: Int
    ): Response<Characters>

    @GET("/api/character")
    suspend fun  filterCharacters(
        @Query("name") name: String,
    ): Response<Characters>

}