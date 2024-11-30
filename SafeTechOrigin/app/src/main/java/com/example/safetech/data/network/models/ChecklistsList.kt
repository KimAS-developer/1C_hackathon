package com.example.safetech.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChecklistsList(
    @SerialName("requests")
    val checklists: List<ObjectChecklist>
)
