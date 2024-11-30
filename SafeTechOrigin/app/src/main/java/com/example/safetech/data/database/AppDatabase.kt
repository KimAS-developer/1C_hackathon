package com.example.safetech.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.safetech.data.database.models.EmployeeDb
import com.example.safetech.data.database.models.UiControlItemDb
import com.example.safetech.data.database.models.UiObjectChecklistDb
import com.example.safetech.data.database.models.ViolationItemDb
import com.example.safetech.data.database.models.ViolationsOnObjectDb

@Database(
    entities = [
        EmployeeDb::class,
        UiControlItemDb::class,
        UiObjectChecklistDb::class,
        ViolationsOnObjectDb::class,
        ViolationItemDb::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun uiObjectChecklistDao(): UiObjectChecklistDao
    abstract fun uiControlItemDao(): UiControlItemDao
    abstract fun violationsOnObjectDao(): ViolationsOnObjectDao
    abstract fun employeeDao(): EmployeeDao
}