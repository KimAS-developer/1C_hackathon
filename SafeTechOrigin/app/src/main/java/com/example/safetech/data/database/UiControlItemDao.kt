package com.example.safetech.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.safetech.data.database.models.UiControlItemDb
import com.example.safetech.view.theme.models.UiControlItem

@Dao
interface UiControlItemDao {
    @Query("SELECT * FROM UiControlItemDb WHERE checklistNumber = :checklistNumber")
    suspend fun getItemsForChecklist(checklistNumber: String): List<UiControlItemDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: UiControlItemDb)

    @Query("DELETE FROM UiControlItemDb WHERE checklistNumber = :checklistNumber")
    suspend fun deleteItemsByChecklistNumber(checklistNumber: String)

}
