package com.example.safetech.view.theme.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.safetech.MainActivity
import com.example.safetech.R
import com.example.safetech.data.database.models.UiObjectChecklistDb
import com.example.safetech.data.database.models.ViolationsOnObjectDb
import com.example.safetech.data.network.models.Employee
import com.example.safetech.data.network.models.ObjectChecklist
import com.example.safetech.data.network.models.ViolationItem
import com.example.safetech.data.network.models.ViolationsOnObject
import com.example.safetech.data.repositories.ChecklistsRepository
import com.example.safetech.data.repositories.DatabaseRepository
import com.example.safetech.view.theme.models.UiControlItem
import com.example.safetech.view.theme.models.UiObjectChecklist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun ChecklistsListScreen(
    viewModel: ChecklistsListViewModel,
    choseItem: (UiObjectChecklist) -> Unit
) {
    val checklistItems by viewModel.checkListItems.collectAsState()

    LazyColumn {
        Log.v("TamziF", checklistItems.size.toString())
        items(checklistItems) { item ->
            ObjectChecklistItemScreen(item) {
                choseItem(item)
                viewModel.openChecklist()
            }
        }
    }
}

class ChecklistsListViewModel(
    private val navHostController: NavHostController,
    private val checklistsRepository: ChecklistsRepository,
    private val activity: MainActivity,
    private val databaseRepository: DatabaseRepository
): ViewModel() {

    private val _checkListItems = MutableStateFlow<List<UiObjectChecklist>>(emptyList())
    val checkListItems: StateFlow<List<UiObjectChecklist>> = _checkListItems

    init {
        if (!isRequestSent) {
            getChecklistsList()
            isRequestSent = true
        } else {
            _checkListItems.value = checklistsList
        }
    }

    private fun getChecklistsList() {
        viewModelScope.launch {
            Log.v("TamziF", "getChecklistsList()")
            val email = getInspectorEmail()
            var response: List<UiObjectChecklist> = checklistsRepository.getChecklistsList(email)
            if (response.isEmpty()) {
                response = loadFromDb()
            }
            _checkListItems.value = response
            checklistsList = response
        }
    }

    private suspend fun loadFromDb(): List<UiObjectChecklist> {
        val list: MutableList<UiObjectChecklist> = mutableListOf()

        val uiObjects = databaseRepository.getAllUiObjectChecklistDb()
        for (uiObject in uiObjects) {
            val controlItemsDb = databaseRepository.getItemsForChecklist(uiObject.number)
            val violationItemsDb = databaseRepository.getViolationOnObjectItemsForChecklist(uiObject.number)
            val employees = databaseRepository.getEmployeesByChecklistNumber(uiObject.number)


            list.add(
                UiObjectChecklist(
                    number = uiObject.number,
                    structuralUnit = uiObject.structuralUnit,
                    responsibleForObject = uiObject.responsibleForObject,
                    checklist = controlItemsDb.map { UiControlItem(
                        code = it.code,
                        controlIndicator = it.controlIndicator,
                        controlGroup = it.controlGroup,
                        description = it.description,
                        violationDescription = it.violationDescription,
                        isViolation = it.isViolation,
                        violationIndicator = it.violationIndicator,
                        photoUri = it.photoUri.toUri(),
                        responsibleForViolation = it.responsibleForViolation
                    ) },
                    employees = employees.map {
                        Employee(
                            fullName = it.fullName,
                            code = it.code
                        )
                    },
                    violationsList = violationItemsDb.map {
                        ViolationsOnObject(
                            number = it.number,
                            inspector = it.inspector,
                            violationChecklist = databaseRepository.getViolationItemsForChecklist(it.number).map {
                                ViolationItem(
                                    controlIndicator = it.controlIndicator,
                                    violationDescription = it.violationDescription,
                                    responsibleForTheViolation = it.responsibleForTheViolation,
                                    photo = it.photo
                                )
                            }
                        )
                    }.toMutableList(),
                )
            )
        }

        return list
    }

    private fun getInspectorEmail(): String {
        val sharedPreferences = activity.applicationContext.getSharedPreferences(
            activity.applicationContext.getString(R.string.SharedPrefs),
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getString(
            activity.applicationContext.getString(R.string.SharedPrefs_Email),
            ""
        )!!
    }

    fun openChecklist() {
        navHostController.navigate("checklist")
    }

    private companion object {
        var isRequestSent: Boolean = false
        var checklistsList: List<UiObjectChecklist> = emptyList()
    }

}

//@Composable
//fun PreviewChecklistsListScreen(
//    controlItems: List<ControlItem>
//) {
//    LazyColumn {
//        items(controlItems) { item ->
//            ChecklistItemScreen(item) {  }
//        }
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun TestPreviewChecklistsListScreen() {
//    PreviewChecklistsListScreen(controlItems =
//        listOf(
//            ControlItem(
//                "Сигнализация и система оповещения",
//                "Пожарная безопасность",
//                "Пожарная сигнализация и система оповещения должны быть исправны",
//                "Неисправная сигнализация или отсутствие тестирования"
//            ),
//            ControlItem(
//                "Сигнализация и система оповещения",
//                "Пожарная безопасность",
//                "Пожарная сигнализация и система оповещения должны быть исправны",
//                "Неисправная сигнализация или отсутствие тестирования"
//            ),
//            ControlItem(
//                "Сигнализация и система оповещения",
//                "Пожарная безопасность",
//                "Пожарная сигнализация и система оповещения должны быть исправны",
//                "Неисправная сигнализация или отсутствие тестирования"
//            ),
//            ControlItem(
//                "Сигнализация и система оповещения",
//                "Пожарная безопасность",
//                "Пожарная сигнализация и система оповещения должны быть исправны",
//                "Неисправная сигнализация или отсутствие тестирования"
//            ),
//        )
//    )
//}