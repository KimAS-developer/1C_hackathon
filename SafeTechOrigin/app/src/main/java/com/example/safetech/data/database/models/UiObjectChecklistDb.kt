package com.example.safetech.data.database.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["number"], unique = true)]
)
data class UiObjectChecklistDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val number: String,
    val structuralUnit: String,
    val responsibleForObject: String,
    var isChecked: Boolean
)
