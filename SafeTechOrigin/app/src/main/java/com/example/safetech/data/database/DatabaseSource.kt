package com.example.safetech.data.database

import android.content.Context
import androidx.room.Room

class DatabaseSource(context: Context) {
    private val dataBase = Room
        .databaseBuilder(
            context,
            AppDatabase::class.java,
            "ToDoItemsDatabase"
        )
        .build()

    val uiObjectChecklistDao = dataBase.uiObjectChecklistDao()
    val uiControlItemDao = dataBase.uiControlItemDao()
    val violationsOnObjectDao = dataBase.violationsOnObjectDao()
    val employeeDao = dataBase.employeeDao()
}