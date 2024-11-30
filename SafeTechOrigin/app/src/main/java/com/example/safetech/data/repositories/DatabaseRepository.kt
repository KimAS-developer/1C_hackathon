//package com.example.safetech.data.repositories
//
//import com.example.safetech.view.theme.models.UiObjectChecklist
//
//interface DatabaseRepository {
//
//    suspend fun getAll(): List<UiObjectChecklist>
//
//    suspend fun insert(violation: UiObjectChecklist)
//
//    suspend fun delete(violation: UiObjectChecklist)
//
//}
//
//class DatabaseRepositoryImpl(
//    private val dao: UiObjectChecklistDao
//): DatabaseRepository {
//
//    override suspend fun getAll(): List<UiObjectChecklist> {
//        return dao.getAll()
//    }
//
//    override suspend fun insert(violation: UiObjectChecklist) {
//        dao.insert(violation)
//    }
//
//    override suspend fun delete(violation: UiObjectChecklist) {
//        dao.delete(violation)
//    }
//
//}