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
data class EmployeeDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val code: String,
    val fullName: String,
    val checklistNumber: String
)
