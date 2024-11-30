package com.example.safetech.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.safetech.data.network.models.ViolationsOnObject

//@Dao
//interface ViolationsOnObjectDao {
//    @Query("SELECT * FROM ViolationsOnObject WHERE checklistNumber = :checklistNumber")
//    suspend fun getViolationsForChecklist(checklistNumber: String): List<ViolationsOnObject>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(violation: ViolationsOnObject)
//}
