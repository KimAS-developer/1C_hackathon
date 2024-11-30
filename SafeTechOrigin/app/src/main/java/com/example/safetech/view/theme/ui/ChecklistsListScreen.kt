package com.example.safetech.view.theme.ui

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.safetech.data.network.models.ObjectChecklist
import com.example.safetech.data.repositories.ChecklistsRepository
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
    //private val databaseRepository: DatabaseRepository
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
            val response: List<UiObjectChecklist> = checklistsRepository.getChecklistsList()
            _checkListItems.value = response
            checklistsList = response
            //databaseRepository.insert()
        }
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