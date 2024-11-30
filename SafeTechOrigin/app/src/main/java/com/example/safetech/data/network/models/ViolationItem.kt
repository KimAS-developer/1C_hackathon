package com.example.safetech.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ViolationItem (
    @SerialName("ПоказательКонтроля")
    val controlIndicator: String,
    @SerialName("ОписаниеНарушения")
    val violationDescription: String,
    @SerialName("ОтветственныйЗаНарушение")
    val responsibleForTheViolation: String,
    @SerialName("Фото")
    val photo: String
)