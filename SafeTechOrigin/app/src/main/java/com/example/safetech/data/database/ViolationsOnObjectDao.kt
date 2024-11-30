package com.example.safetech.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.safetech.data.database.models.UiControlItemDb
import com.example.safetech.data.database.models.ViolationItemDb
import com.example.safetech.data.database.models.ViolationsOnObjectDb
import com.example.safetech.data.network.models.ViolationItem
import com.example.safetech.data.network.models.ViolationsOnObject

//@Dao
//interface ViolationsOnObjectDao {
//    @Query("SELECT * FROM ViolationsOnObjectDb WHERE checklistNumber = :checklistNumber")
//    suspend fun getViolationsForChecklist(checklistNumber: String): List<ViolationsOnObject>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(violation: ViolationsOnObject)
//}

@Dao
interface ViolationsOnObjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertViolationOnObject(violation: ViolationsOnObjectDb): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertViolationItem(violationItem: ViolationItemDb)

    @Transaction
    suspend fun insertCompleteViolation(violationOnObject: ViolationsOnObjectDb, items: List<ViolationItemDb>) {
        val id = insertViolationOnObject(violationOnObject)
        items.forEach { item ->
            insertViolationItem(item.copy(violationsOnObjectNumber = violationOnObject.number))
        }
    }

    @Query("SELECT * FROM ViolationItemDb WHERE violationsOnObjectNumber = :violationsOnObjectNumber")
    suspend fun getViolationItemsForChecklist(violationsOnObjectNumber: String): List<ViolationItemDb>

    @Query("SELECT * FROM ViolationsOnObjectDb WHERE checklistNumber = :checklistNumber")
    suspend fun getViolationOnObjectItemsForChecklist(checklistNumber: String): List<ViolationsOnObjectDb>
}

