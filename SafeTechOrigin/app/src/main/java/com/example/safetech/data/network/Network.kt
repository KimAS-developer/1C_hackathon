package com.example.safetech.data.network

import com.example.safetech.data.network.services.AuthService
import com.example.safetech.data.network.services.ChecklistsService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class Network {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofitObject: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val authApi: AuthService = retrofitObject.create(AuthService::class.java)
    val checklistsApi: ChecklistsService = retrofitObject.create(ChecklistsService::class.java)

    companion object {
        const val BASE_URL = "https://8ebb-2a00-1370-8188-3174-54cc-bb11-a9ea-73e8.ngrok-free.app/hakaton/hs/exchange/"
    }
}