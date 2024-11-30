package com.example.safetech.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.safetech.view.theme.models.UiControlItem

//@Dao
//interface UiControlItemDao {
//    @Query("SELECT * FROM UiControlItem WHERE checklistNumber = :checklistNumber")
//    suspend fun getItemsForChecklist(checklistNumber: String): List<UiControlItem>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(item: UiControlItem)
//}
