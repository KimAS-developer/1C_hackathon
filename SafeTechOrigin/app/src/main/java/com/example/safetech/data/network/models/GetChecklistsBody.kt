package com.example.safetech.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetChecklistsBody(
    @SerialName("email")
    val email: String
)
