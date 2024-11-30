//package com.example.safetech.view.theme.ui
//
//import android.net.Uri
//import android.util.Log
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Checkbox
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.lifecycle.viewmodel.compose.viewModel
//import coil.compose.AsyncImage
//import com.example.safetech.MainActivity
//import com.example.safetech.R
//import com.example.safetech.view.theme.models.UiControlItem
//import com.example.safetech.view.theme.style.SafeTechColors
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.flow.MutableSharedFlow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.SharedFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//@Composable
//fun ControlItemScreen(
//    item: UiControlItem,
//    viewModel: ControlItemScreenViewModel
//) {
//    var violationDescription by remember { mutableStateOf("") }
//
//    var isChecked by remember { mutableStateOf(item.isViolation) }
//
//    val imageUri by viewModel.uri.collectAsState()
//
//    if (item.photoUri != Uri.EMPTY) viewModel.setInitUri(item.photoUri)
//
//    Card(
//        elevation = CardDefaults.cardElevation(4.dp),
//        modifier = Modifier.padding(8.dp),
//        colors = CardDefaults.cardColors(containerColor = SafeTechColors.LightBlue)
//    ) {
//        Column {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(16.dp)
//                ) {
//                    Text(text = item.controlIndicator, fontSize = 20.sp)
//                    Text(text = "Группа: ${item.controlGroup}", fontSize = 16.sp)
//                    Text(text = "Описание: ${item.description}", fontSize = 16.sp)
//                    Text(text = "Признак нарушения: ${item.violationIndicator}", fontSize = 16.sp)
//                }
//                Checkbox(
//                    checked = isChecked,
//                    onCheckedChange = { checked ->
//                        isChecked = checked
//                        item.isViolation = checked
//                    }
//                )
//            }
//            if (isChecked) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                ) {
//                    TextField(
//                        value = violationDescription,
//                        onValueChange = {
//                            violationDescription = it
//                        },
//                        label = { Text("Описание нарушения") },
//                        visualTransformation = PasswordVisualTransformation(),
//                        modifier = Modifier.fillMaxWidth(),
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_camera),
//                        contentDescription = "Фотография нарушения",
//                        modifier = Modifier
//                            .size(48.dp)
//                            .clickable {
//                                viewModel.getPhoto()
//                            }
//
//                    )
//                    if (imageUri != Uri.EMPTY) {
//                        item.photoUri = imageUri
//                        AsyncImage(
//                            model = imageUri,
//                            contentDescription = "Загруженное изображение",
//                            modifier = Modifier
//                                .height(200.dp)
//                                .fillMaxWidth()
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun ControlItemScreenPreview() {
//
//    val item = UiControlItem(
//        "2",
//        "Сигнализация и система оповещения",
//        "Пожарная безопасность",
//        "Пожарная сигнализация и система оповещения должны быть исправны",
//        "Неисправная сигнализация или отсутствие тестирования",
//        false,
//        violationDescription = "",
//        Uri.EMPTY,
//        responsibleForViolation = ""
//    )
//
//    ControlItemScreen(
//        item = item,
//        viewModel = ControlItemScreenViewModel(
//            MutableSharedFlow(replay = 0)
//        ) {
//
//        }
//    )
//
//}
//
//class ControlItemScreenViewModel(
//    private val uriFlow: SharedFlow<Uri>,
//    private val loadUri: () -> Unit
//): ViewModel() {
//
//    var job: Job? = null
//
//    private val _uri: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)
//    val uri: StateFlow<Uri> = _uri
//
//    fun getPhoto() {
//        subscribeToUriFlow()
//        loadUri()
//    }
//
//    fun setInitUri(uri: Uri) {
//        _uri.value = uri
//    }
//
//    private fun subscribeToUriFlow() {
//        job = viewModelScope.launch(Dispatchers.IO) {
//            uriFlow.collect {
//                _uri.value = it
//                unsubscribeToUriFlow()
//            }
//        }
//    }
//
//    private fun unsubscribeToUriFlow() {
//        job?.cancel()
//    }
//
//}