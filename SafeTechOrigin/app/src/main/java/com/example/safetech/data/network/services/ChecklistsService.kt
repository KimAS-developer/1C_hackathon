package com.example.safetech.data.network.services

import com.example.safetech.data.network.models.ChecklistsList
import com.example.safetech.data.network.models.GetChecklistsBody
import com.example.safetech.data.network.models.ViolationsOnObject
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ChecklistsService {

    @POST("requests_all")
    suspend fun getChecklistsList(
        @Header("Authorization") credentials: String,
        @Body email: GetChecklistsBody
    ): ChecklistsList

    @POST("send_list")
    suspend fun sendViolationList(
        @Header("Authorization") credentials: String,
        @Body violationsOnObject: ViolationsOnObject
    )

}
