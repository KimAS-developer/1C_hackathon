package com.example.safetech.data.repositories

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.safetech.data.database.EmployeeDao
import com.example.safetech.data.database.UiControlItemDao
import com.example.safetech.data.database.UiObjectChecklistDao
import com.example.safetech.data.database.ViolationsOnObjectDao
import com.example.safetech.data.database.models.EmployeeDb
import com.example.safetech.data.database.models.UiControlItemDb
import com.example.safetech.data.database.models.UiObjectChecklistDb
import com.example.safetech.data.database.models.ViolationItemDb
import com.example.safetech.data.database.models.ViolationsOnObjectDb
import com.example.safetech.data.network.models.ViolationItem

interface DatabaseRepository {

    suspend fun getAllUiObjectChecklistDb(): List<UiObjectChecklistDb>

    suspend fun insertUiObjectChecklistDb(uiObjectChecklistDb: UiObjectChecklistDb)

    suspend fun deleteUiObjectChecklistDb(uiObjectChecklistDb: UiObjectChecklistDb)

    suspend fun getItemsForChecklist(checklistNumber: String): List<UiControlItemDb>

    suspend fun insertUiControlItemDb(uiControlItemDb: UiControlItemDb)

    suspend fun deleteUiControlItemsByChecklistNumber(checklistNumber: String)

    suspend fun insertViolationOnObject(violation: ViolationsOnObjectDb): Long

    suspend fun insertViolationItem(violationItem: ViolationItemDb)

    suspend fun getViolationItemsForChecklist(checklistNumber: String): List<ViolationItemDb>

    suspend fun getViolationOnObjectItemsForChecklist(checklistNumber: String): List<ViolationsOnObjectDb>

    suspend fun insertCompleteViolation(violationOnObject: ViolationsOnObjectDb, items: List<ViolationItemDb>) {
        val id = insertViolationOnObject(violationOnObject)
        items.forEach { item ->
            insertViolationItem(item.copy(violationsOnObjectNumber = violationOnObject.number))
        }
    }

    suspend fun insert(employee: EmployeeDb)

    suspend fun delete(employee: EmployeeDb)

    suspend fun getEmployeesByChecklistNumber(checklistNumber: String): List<EmployeeDb>

    suspend fun deleteEmployeesByChecklistNumber(checklistNumber: String)

    suspend fun deleteAllEmployees()

}

class DatabaseRepositoryImpl(
    private val uiObjectChecklistDao: UiObjectChecklistDao,
    private val uiControlItemDao: UiControlItemDao,
    private val violationsOnObjectDao: ViolationsOnObjectDao,
    private val employeeDao: EmployeeDao
): DatabaseRepository {

    override suspend fun getAllUiObjectChecklistDb(): List<UiObjectChecklistDb> {
        return uiObjectChecklistDao.getAll()
    }

    override suspend fun insertUiObjectChecklistDb(violation: UiObjectChecklistDb) {
        uiObjectChecklistDao.insert(violation)
    }

    override suspend fun deleteUiObjectChecklistDb(violation: UiObjectChecklistDb) {
        uiObjectChecklistDao.delete(violation)
    }

    override suspend fun getItemsForChecklist(checklistNumber: String): List<UiControlItemDb> {
        return uiControlItemDao.getItemsForChecklist(checklistNumber)
    }

    override suspend fun insertUiControlItemDb(uiControlItemDb: UiControlItemDb) {
        uiControlItemDao.insert(uiControlItemDb)
    }

    override suspend fun deleteUiControlItemsByChecklistNumber(checklistNumber: String) {
        uiControlItemDao.deleteItemsByChecklistNumber(checklistNumber)
    }

    override suspend fun insertViolationOnObject(violation: ViolationsOnObjectDb): Long {
        return violationsOnObjectDao.insertViolationOnObject(violation)
    }

    override suspend fun insertViolationItem(violationItem: ViolationItemDb) {
        violationsOnObjectDao.insertViolationItem(violationItem)
    }

    override suspend fun insertCompleteViolation(
        violationOnObject: ViolationsOnObjectDb,
        items: List<ViolationItemDb>
    ) {
        super.insertCompleteViolation(violationOnObject, items)
    }

    override suspend fun insert(employee: EmployeeDb) {
        employeeDao.insert(employee)
    }

    override suspend fun delete(employee: EmployeeDb) {
        employeeDao.delete(employee)
    }

    override suspend fun getEmployeesByChecklistNumber(checklistNumber: String): List<EmployeeDb> {
        return employeeDao.getEmployeesByChecklistNumber(checklistNumber)
    }

    override suspend fun deleteEmployeesByChecklistNumber(checklistNumber: String) {
        employeeDao.deleteEmployeesByChecklistNumber(checklistNumber)
    }

    override suspend fun deleteAllEmployees() {
        employeeDao.deleteAllEmployees()
    }

    override suspend fun getViolationItemsForChecklist(checklistNumber: String): List<ViolationItemDb> {
        return violationsOnObjectDao.getViolationItemsForChecklist(checklistNumber)
    }

    override suspend fun getViolationOnObjectItemsForChecklist(checklistNumber: String): List<ViolationsOnObjectDb> {
        return violationsOnObjectDao.getViolationOnObjectItemsForChecklist(checklistNumber)
    }
}