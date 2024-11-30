package com.example.safetech.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.safetech.data.database.models.EmployeeDb

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(employee: EmployeeDb)

    @Delete
    suspend fun delete(employee: EmployeeDb)

    @Query("SELECT * FROM EmployeeDb WHERE checklistNumber = :checklistNumber")
    suspend fun getEmployeesByChecklistNumber(checklistNumber: String): List<EmployeeDb>

    @Query("DELETE FROM EmployeeDb WHERE checklistNumber = :checklistNumber")
    suspend fun deleteEmployeesByChecklistNumber(checklistNumber: String)

    @Query("DELETE FROM EmployeeDb")
    suspend fun deleteAllEmployees()
}