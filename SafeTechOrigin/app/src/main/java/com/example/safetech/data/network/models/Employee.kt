package com.example.safetech.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Employee(
    @SerialName("ФИО")
    val fullName: String,
    @SerialName("Код")
    val code: String
)
