package com.example.safetech.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.safetech.view.theme.models.UiObjectChecklist

//@Dao
//interface UiObjectChecklistDao {
//    @Query("SELECT * FROM UiObjectChecklist")
//    suspend fun getAll(): List<UiObjectChecklist>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(violation: UiObjectChecklist)
//
//    @Delete
//    suspend fun delete(violation: UiObjectChecklist)
//}
