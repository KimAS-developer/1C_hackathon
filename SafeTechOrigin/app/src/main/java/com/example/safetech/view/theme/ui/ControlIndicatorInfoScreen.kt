package com.example.safetech.view.theme.ui

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.safetech.MockData
import com.example.safetech.R
import com.example.safetech.data.network.models.Employee
import com.example.safetech.data.network.models.ViolationItem
import com.example.safetech.view.theme.models.UiControlItem
import com.example.safetech.view.theme.style.SafeTechColors
import com.example.safetech.view.theme.ui.custom_composable.DropDownDemo
import com.example.safetech.view.theme.ui.custom_composable.SafeTechButton
import com.example.safetech.view.theme.ui.custom_composable.SafeTechButtonStyle
import com.example.safetech.view.theme.ui.custom_composable.SafeTechTextField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun ControlIndicatorInfoScreen(
    item: UiControlItem,
    employees: List<Employee>,
    mainResponsibleForObject: String,
    viewModel: ControlIndicatorInfoScreenViewModel,
    addViolationAction: (ViolationItem) -> Unit
) {

    var isViolation: Boolean by remember { mutableStateOf(item.isViolation) }
    var violationDescription: String by remember { mutableStateOf(item.violationDescription) }
    val imageUri by viewModel.uri.collectAsState()
    var responsibleForViolation: String by remember { mutableStateOf(item.responsibleForViolation) }

    if (item.photoUri != Uri.EMPTY) viewModel.setInitUri(item.photoUri)

    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        item {
            Text(
                text = item.controlIndicator,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth()
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = SafeTechColors.Purple
                ),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Описание показателя: ",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }

            Column {
                if (isViolation) {
                    SafeTechButton(
                        text = "Нарушений не выявлено",
                        style = SafeTechButtonStyle.Orange,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    ) {
                        viewModel.closeScreen()
                    }
                }

                SafeTechButton(
                    text = if (isViolation) {
                        "Убрать нарушение"
                    } else {
                        "Зарегистрировать нарушение"
                    },
                    style = SafeTechButtonStyle.Orange,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    isViolation = !isViolation
                }
            }

            AnimatedVisibility (
                visible = isViolation,
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = SafeTechColors.Purple
                    ),
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                    ) {
                        SafeTechTextField(
                            label = "Описание нарушения",
                            textColor = Color.White,
                            text = violationDescription,
                        ) {
                            violationDescription = it
                            item.violationDescription = it
                        }

                        if (imageUri == Uri.EMPTY)
                            Image(
                                painter = painterResource(id = R.drawable.photo_skeleton),
                                contentDescription = "Image skeleton",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp)
                                    .clickable {
                                        viewModel.getPhoto()
                                    }
                            )
                        else
                            AsyncImage(
                                model = imageUri,
                                contentDescription = "Загруженное изображение",
                                modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth()
                                    .padding(top = 20.dp)
                            )

                        Text(
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium,
                            text = "Ответственный за нарушение:",
                            modifier = Modifier.padding(top = 32.dp)
                        )

                        Row {
                            DropDownDemo(
                                responsibleForViolation,
                                listOf(Employee(mainResponsibleForObject, "1")) + employees,
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                responsibleForViolation = it
                            }

                            Icon(
                                painter = painterResource(id = R.drawable.check),
                                contentDescription = "save",
                                tint = Color.White,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .clickable {
                                        item.isViolation = isViolation
                                        item.violationDescription = violationDescription
                                        item.photoUri = imageUri



                                        viewModel.closeScreen()
                                    }
                            )
                        }


                    }
                }
            }
        }

    }

    fun saveItem() {
        item.isViolation = isViolation
        item.violationDescription = violationDescription
        item.photoUri = imageUri
    }

    fun buildViolation(): ViolationItem {
        return ViolationItem(
            controlIndicator = item.controlIndicator,
            violationDescription = item.violationDescription,
            responsibleForTheViolation = responsibleForViolation,
            photo = imageUri.toString()
        )
    }
}

class ControlIndicatorInfoScreenViewModel(
    private val uriFlow: SharedFlow<Uri>,
    private val navHostController: NavHostController,
    private val loadUri: () -> Unit,
): ViewModel() {

    private val _uri: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)
    val uri: StateFlow<Uri> = _uri

    var job: Job? = null

    fun getPhoto() {
        subscribeToUriFlow()
        loadUri()
    }

    fun closeScreen() {
        navHostController.navigate("checklist")
    }

    private fun subscribeToUriFlow() {
        job = viewModelScope.launch(Dispatchers.IO) {
            uriFlow.collect {
                _uri.value = it
                unsubscribeToUriFlow()
            }
        }
    }

    private fun unsubscribeToUriFlow() {
        job?.cancel()
    }

    fun setInitUri(uri: Uri) {
        _uri.value = uri
    }

}

@Preview
@Composable
private fun ControlIndicatorInfoScreenPreview() {

    val item = MockData.uiControlItem

    val employees = MockData.employees

//    ControlIndicatorInfoScreen(
//        item = item,
//        employees,
//        ControlIndicatorInfoScreenViewModel(
//            MutableStateFlow(Uri.EMPTY),
//            {  }
//        )
//    )
}