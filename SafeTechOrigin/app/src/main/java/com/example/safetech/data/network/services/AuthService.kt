package com.example.safetech.data.network.services

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {

    @POST("authorize_via_email")
    suspend fun auth(
        @Header("Authorization") credentials: String,
        @Body data: AuthData
    ): Response<AuthResponse>

}

@Serializable
data class AuthData (
    @SerialName("email")
    val login: String,
    @SerialName("Пароль")
    val password: String
)

@Serializable
data class AuthResponse(
    @SerialName("Код")
    val token: String,
    @SerialName("Наименование")
    val username: String,
    @SerialName("Сообщение")
    val message: String
)