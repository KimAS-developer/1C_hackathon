package com.example.safetech.data.repositories

import com.example.safetech.data.network.models.GetChecklistsBody
import com.example.safetech.data.network.models.ViolationItem
import com.example.safetech.data.network.models.ViolationsOnObject
import com.example.safetech.data.network.models.toUiObjectChecklist
import com.example.safetech.data.network.services.ChecklistsService
import com.example.safetech.view.theme.models.UiObjectChecklist
import okhttp3.Credentials

interface ChecklistsRepository {

    suspend fun getChecklistsList(): List<UiObjectChecklist>

    suspend fun sendViolationList(violationsOnObject: ViolationsOnObject)

}

class ChecklistsRepositoryImpl(
    private val checklistsService: ChecklistsService
): ChecklistsRepository {

    override suspend fun getChecklistsList(): List<UiObjectChecklist> {
        return checklistsService.getChecklistsList(
            Credentials.basic("Администратор", "", Charsets.UTF_8),
            GetChecklistsBody("korotkov@mail.ru")
        ).checklists.map { it.toUiObjectChecklist() }
    }

    override suspend fun sendViolationList(violationsOnObject: ViolationsOnObject) {
        checklistsService.sendViolationList(
            Credentials.basic("Администратор", "", Charsets.UTF_8),
            violationsOnObject
        )
    }

}

class ChecklistsRepositoryTest(
    private val data: List<UiObjectChecklist>
): ChecklistsRepository {

    override suspend fun getChecklistsList(): List<UiObjectChecklist> {
        return data
    }

    override suspend fun sendViolationList(violationsOnObject: ViolationsOnObject) {

    }

}