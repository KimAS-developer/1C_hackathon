package com.example.safetech.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.safetech.data.database.models.UiObjectChecklistDb
import com.example.safetech.view.theme.models.UiObjectChecklist

@Dao
interface UiObjectChecklistDao {
    @Query("SELECT * FROM UiObjectChecklistDb")
    suspend fun getAll(): List<UiObjectChecklistDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(uiObjectChecklist: UiObjectChecklistDb)

    @Delete
    suspend fun delete(uiObjectChecklist: UiObjectChecklistDb)
}
