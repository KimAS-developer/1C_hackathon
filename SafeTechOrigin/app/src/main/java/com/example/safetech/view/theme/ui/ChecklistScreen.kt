package com.example.safetech.view.theme.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.safetech.MainActivity
import com.example.safetech.view.theme.models.UiControlItem
import androidx.compose.material3.Text
import androidx.lifecycle.viewModelScope
import com.example.safetech.data.network.models.ViolationItem
import com.example.safetech.data.network.models.ViolationsOnObject
import com.example.safetech.data.repositories.ChecklistsRepository
import com.example.safetech.view.theme.models.UiObjectChecklist
import com.example.safetech.view.theme.style.SafeTechColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException
import com.example.safetech.R
import com.example.safetech.data.database.models.UiObjectChecklistDb
import com.example.safetech.data.repositories.DatabaseRepository


@Composable
fun CheckListScreen(
    viewModel: ChecklistViewModel,
    uiObjectChecklist: UiObjectChecklist,
    activity: MainActivity,
    choseItemAction: (UiControlItem) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(viewModel.getControlGroupList(uiObjectChecklist.checklist)) { items ->
                ControlGroupItemScreen(
                    controlGroupName = items[0].controlGroup,
                    items = items,
                    choseItemAction = choseItemAction
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.sendDataToServer(uiObjectChecklist, activity.applicationContext)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SafeTechColors.LightBlue)
        ) {
            Text("Отправить на сервер")
        }

    }

}

class ChecklistViewModel(
    private val navHostController: NavHostController,
    private val checklistsRepository: ChecklistsRepository,
    private val databaseRepository: DatabaseRepository
): ViewModel() {

    private var controlGroupsList: List<List<UiControlItem>>? = null

    fun getControlGroupList(checklist: List<UiControlItem>): List<List<UiControlItem>> {
        if (controlGroupsList == null)
            controlGroupsList = buildControlGroupsMap(checklist)

        return controlGroupsList!!
    }

    private fun buildControlGroupsMap(list: List<UiControlItem>): List<List<UiControlItem>> {
        val map: HashMap<String, MutableList<UiControlItem>> = hashMapOf()
        for (item in list) {
            if (map[item.controlGroup] == null)
                map[item.controlGroup] = mutableListOf()
            map[item.controlGroup]!!.add(item)
        }

        return map.values.toList()
    }

    fun sendDataToServer(uiObjectChecklist: UiObjectChecklist, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            uiObjectChecklist.isChecked = true
            checklistsRepository.sendViolationList(buildViolationsList(uiObjectChecklist, context))
            launch(Dispatchers.Main) {
                saveToDb(uiObjectChecklist)
                Toast.makeText(context, "Данные успешно отправлены", Toast.LENGTH_SHORT).show()
                navHostController.navigate("checklists")
            }
        }
    }

    private suspend fun saveToDb(item: UiObjectChecklist) {
        val uiObjectChecklistDb = UiObjectChecklistDb(
            number = item.number,
            structuralUnit = item.structuralUnit,
            responsibleForObject = item.responsibleForObject,
            isChecked = item.isChecked
        )
        databaseRepository.insertUiObjectChecklistDb(uiObjectChecklistDb)
    }

    private fun buildViolationsList(uiObjectChecklist: UiObjectChecklist, context: Context): ViolationsOnObject {
        return ViolationsOnObject(
            number = uiObjectChecklist.number,
            inspector = getInspectorUsernameFromSharedPrefs(context),
            violationChecklist = getViolationsFromChecklist(uiObjectChecklist.checklist, context)
        )
    }

    private fun getInspectorUsernameFromSharedPrefs(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(
            context.getString(R.string.SharedPrefs),
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getString(
            context.getString(R.string.SharedPrefs_Username),
            ""
        )!!
    }

    private fun getViolationsFromChecklist(checklist: List<UiControlItem>, context: Context): List<ViolationItem> {
        val list: MutableList<ViolationItem> = mutableListOf()
        for (item in checklist) {
            if (item.isViolation) {
                list.add(
                    ViolationItem(
                        controlIndicator = item.controlIndicator,
                        violationDescription = item.violationDescription,
                        responsibleForTheViolation = "",
                        photo = UriToBitmapConverter.photoUriToBase64(item.photoUri, context)
                    )
                )
            }
        }
        return list
    }
}

object UriToBitmapConverter {

    fun photoUriToBase64(uri: Uri, context: Context): String {
        val photoBitmap = uriToBitmap(uri, context)
        return photoBitmap?.let { bitmapToBase64(it) } ?: ""
    }

    private fun uriToBitmap(uri: Uri, context: Context): Bitmap? {
        return try {
            val parcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")
            val fileDescriptor = parcelFileDescriptor?.fileDescriptor
            val bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor?.close()
            bitmap
        } catch (e: IOException) {
            //Log.e("TamziF", "${e.message}")
            e.printStackTrace()
            null
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        ByteArrayOutputStream().apply {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
            val byteArray = toByteArray()
            return Base64.encodeToString(byteArray, Base64.DEFAULT)
        }
    }

}