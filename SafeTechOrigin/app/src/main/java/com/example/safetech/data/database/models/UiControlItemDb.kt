package com.example.safetech.data.database.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = UiObjectChecklistDb::class,
        parentColumns = ["number"],
        childColumns = ["checklistNumber"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class UiControlItemDb (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val checklistNumber: String,
    val code: String,
    val controlIndicator: String,
    val controlGroup: String,
    val description: String,
    val violationIndicator: String,
    var isViolation: Boolean,
    var violationDescription: String,
    var photoUri: String, // Uri as String
    var responsibleForViolation: String
)
