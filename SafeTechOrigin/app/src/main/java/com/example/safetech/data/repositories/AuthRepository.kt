package com.example.safetech.data.repositories

import android.util.Log
import com.example.safetech.data.network.services.AuthData
import com.example.safetech.data.network.services.AuthResponse
import com.example.safetech.data.network.services.AuthService
import okhttp3.Credentials
import retrofit2.Response

interface AuthRepository {

    suspend fun auth(
        data: AuthData
    ): Response<AuthResponse>

}

class AuthRepositoryImpl(
    private val authService: AuthService
): AuthRepository {

    override suspend fun auth(
        data: AuthData
    ): Response<AuthResponse> {
        //Log.v("TamziF", "hello")
        return authService.auth(
            Credentials.basic("Администратор", "", Charsets.UTF_8),
            data
        )
    }

}