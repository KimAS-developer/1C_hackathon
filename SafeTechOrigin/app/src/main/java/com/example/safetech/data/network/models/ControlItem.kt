package com.example.safetech.data.network.models

import android.net.Uri
import com.example.safetech.view.theme.models.UiControlItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ControlItem(
    @SerialName("Код")
    val code: String,
    @SerialName("ПоказательКонтроля")
    val controlIndicator: String,
    @SerialName("ГруппаПоказателейКонтроля")
    val controlGroup: String,
    @SerialName("Описание")
    val description: String,
    @SerialName("ПризнакНарушения")
    val violationIndicator: String
)

fun ControlItem.toUiControlItem(): UiControlItem {
    return UiControlItem(
        code = this.code,
        controlIndicator = this.controlIndicator,
        controlGroup = this.controlGroup,
        description = this.description,
        violationIndicator = this.violationIndicator,
        isViolation = false,
        violationDescription = "",
        photoUri = Uri.EMPTY,
        responsibleForViolation = ""
    )
}