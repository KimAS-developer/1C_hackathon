package com.example.safetech.view.theme.models

import android.net.Uri

data class UiControlItem(
    val code: String,
    val controlIndicator: String,
    val controlGroup: String,
    val description: String,
    val violationIndicator: String,
    var isViolation: Boolean,
    var violationDescription: String,
    var photoUri: Uri,
    var responsibleForViolation: String
)
