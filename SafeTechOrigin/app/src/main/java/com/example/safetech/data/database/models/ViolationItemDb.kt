package com.example.safetech.data.database.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = ViolationsOnObjectDb::class,
        parentColumns = ["number"],
        childColumns = ["violationsOnObjectNumber"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ViolationItemDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val violationsOnObjectNumber: String,
    val violationId: Long,
    val controlIndicator: String,
    val violationDescription: String,
    val responsibleForTheViolation: String,
    val photo: String
)
