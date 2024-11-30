package com.example.safetech

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.safetech.data.network.Network
import com.example.safetech.data.network.models.Employee
import com.example.safetech.data.network.models.ViolationsOnObject
import com.example.safetech.data.repositories.AuthRepository
import com.example.safetech.data.repositories.AuthRepositoryImpl
import com.example.safetech.data.repositories.ChecklistsRepository
import com.example.safetech.data.repositories.ChecklistsRepositoryImpl
import com.example.safetech.data.repositories.ChecklistsRepositoryTest
import com.example.safetech.view.theme.models.UiControlItem
import com.example.safetech.view.theme.models.UiObjectChecklist
import com.example.safetech.view.theme.style.SafeTechTheme
import com.example.safetech.view.theme.ui.AuthViewModel
import com.example.safetech.view.theme.ui.AuthScreen
import com.example.safetech.view.theme.ui.CheckListScreen
import com.example.safetech.view.theme.ui.ChecklistViewModel
import com.example.safetech.view.theme.ui.ChecklistsListScreen
import com.example.safetech.view.theme.ui.ChecklistsListViewModel
import com.example.safetech.view.theme.ui.ControlIndicatorInfoScreen
import com.example.safetech.view.theme.ui.ControlIndicatorInfoScreenViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SafeTechTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen(
                        authRepository,
                        checklistsRepository,
//                        databaseRepository,
                        this
                    )
                }
            }
        }
    }

    val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            if (result.data != null) {
                Log.v("TamziF", result.data!!.data!!.toString())
                emitUri(result.data!!.data!!)
            }
        }
    }

    private val _uriFlow: MutableSharedFlow<Uri> = MutableSharedFlow(replay = 0)
    val uriFlow: SharedFlow<Uri> = _uriFlow

    fun getPhotoFromGallery() {
        val intent  = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    private fun emitUri(uri: Uri) {
        lifecycleScope.launch {
            _uriFlow.emit(uri)
        }
    }

//    private val database: DatabaseSource by lazy { DatabaseSource(applicationContext) }
//    private val databaseRepository: DatabaseRepository by lazy { DatabaseRepositoryImpl(database.dao) }

    companion object {
        val network: Network = Network()
        val authRepository: AuthRepository = AuthRepositoryImpl(network.authApi)
        val checklistsRepository: ChecklistsRepository = ChecklistsRepositoryImpl(network.checklistsApi)
    }
}

@Composable
fun MainScreen(
    authRepository: AuthRepository,
    checklistsRepository: ChecklistsRepository,
    //databaseRepository: DatabaseRepository,
    activity: MainActivity
) {
    val navController = rememberNavController()

    val mockRepository: ChecklistsRepository =
        ChecklistsRepositoryTest(MockData.checklistsList)

    var chosenChecklist = UiObjectChecklist("", "", "", emptyList(), emptyList(), false, mutableListOf())
    var chosenControlItem = UiControlItem("", "", "", "", "", false, "", Uri.EMPTY, "")

    NavHost(navController = navController, startDestination = "auth") {
        composable("auth") { AuthScreen(AuthViewModel(navController, authRepository)) }
        composable("checklists") {
            ChecklistsListScreen(
                ChecklistsListViewModel(
                    navController,
                    checklistsRepository,
//                    databaseRepository
                )
            ) {
                chosenChecklist = it
            }
        }
        composable("checklist") { CheckListScreen(ChecklistViewModel(navController, checklistsRepository), chosenChecklist, activity) {
            chosenControlItem = it
            navController.navigate("controlInfo")
        } }
        composable("controlInfo") {
            ControlIndicatorInfoScreen(
                chosenControlItem,
                chosenChecklist.employees,
                chosenChecklist.responsibleForObject,
                ControlIndicatorInfoScreenViewModel(
                    activity.uriFlow,
                    navHostController = navController
                ) {
                    activity.getPhotoFromGallery()
                }
            ) { violation ->
                //chosenChecklist.violationsList.add(violation)
            }
        }
    }
}

object MockData {

    val uiControlItem = UiControlItem(
        "2",
        "Сигнализация и система оповещения",
        "Пожарная безопасность",
        "Пожарная сигнализация и система оповещения должны быть исправны",
        "Неисправная сигнализация или отсутствие тестирования",
        false,
        violationDescription = "",
        Uri.EMPTY,
        responsibleForViolation = ""
    )

    val checklist1 = listOf(
        UiControlItem(
            "1",
            "Сигнализация и система оповещения",
            "Пожарная безопасность",
            "Пожарная сигнализация и система оповещения должны быть исправны",
            "Неисправная сигнализация или отсутствие тестирования",
            false,
            violationDescription = "",
            Uri.EMPTY,
            responsibleForViolation = ""
        ),
        UiControlItem(
            "2",
            "Сигнализация и система оповещения",
            "Пожарная безопасность",
            "Пожарная сигнализация и система оповещения должны быть исправны",
            "Неисправная сигнализация или отсутствие тестирования",
            false,
            violationDescription = "",
            Uri.EMPTY,
            responsibleForViolation = ""
        ),
        UiControlItem(
            "3",
            "Сигнализация и система оповещения",
            "Пожарная безопасность",
            "Пожарная сигнализация и система оповещения должны быть исправны",
            "Неисправная сигнализация или отсутствие тестирования",
            false,
            violationDescription = "",
            Uri.EMPTY,
            responsibleForViolation = ""
        ),
        UiControlItem(
            "4",
            "Сигнализация и система оповещения",
            "Пожарная безопасность",
            "Пожарная сигнализация и система оповещения должны быть исправны",
            "Неисправная сигнализация или отсутствие тестирования",
            false,
            violationDescription = "",
            Uri.EMPTY,
            responsibleForViolation = ""
        ),
    )

    val checklist2 = listOf(
        UiControlItem(
            "1",
            "Сигнализация и система оповещения",
            "Пожарная безопасность",
            "Пожарная сигнализация и система оповещения должны быть исправны",
            "Неисправная сигнализация или отсутствие тестирования",
            false,
            violationDescription = "",
            Uri.EMPTY,
            responsibleForViolation = ""
        ),
        UiControlItem(
            "2",
            "Сигнализация и система оповещения",
            "Пожарная безопасность",
            "Пожарная сигнализация и система оповещения должны быть исправны",
            "Неисправная сигнализация или отсутствие тестирования",
            false,
            violationDescription = "",
            Uri.EMPTY,
            responsibleForViolation = ""
        ),
    )

    val employees = listOf(
        Employee(
            fullName = "Шербустанов Андрей Евгеньевич",
            code = "1"
        ),
        Employee(
            fullName = "Быстров Ярослав Сергеевич",
            code = "2"
        )
    )

    val checklistsList = listOf(
        UiObjectChecklist(
            number = "1",
            structuralUnit = "Цех металлопроката",
            responsibleForObject = "Некрасов Максим Кириллович",
            checklist = checklist1,
            employees = employees,
            isChecked = false,
            violationsList = mutableListOf()
        ),
        UiObjectChecklist(
            number = "2",
            structuralUnit = "Щекиноазот",
            responsibleForObject = "Некрасов Максим Кириллович",
            checklist = checklist2,
            employees = employees,
            isChecked = false,
            violationsList = mutableListOf()
        )
    )
}