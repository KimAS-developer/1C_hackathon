package com.example.safetech.data.database.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["number"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = UiObjectChecklistDb::class,
        parentColumns = ["number"],
        childColumns = ["checklistNumber"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ViolationsOnObjectDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val checklistNumber: String,
    val number: String,
    val inspector: String
)
