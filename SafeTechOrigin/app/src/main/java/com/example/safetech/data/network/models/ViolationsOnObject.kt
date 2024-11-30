package com.example.safetech.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ViolationsOnObject(
    @SerialName("Номер")
    val number: String,
    @SerialName("Проверяющий")
    val inspector: String,
    @SerialName("ЧекЛист")
    val violationChecklist: List<ViolationItem>
)
