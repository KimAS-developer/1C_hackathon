package com.example.safetech.view.theme.ui.custom_composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.safetech.R
import com.example.safetech.data.network.models.Employee
import com.example.safetech.view.theme.style.SafeTechColors
import com.example.safetech.view.theme.ui.ControlIndicatorItemScreen

//@Composable
//fun SafeTechExpanded(
//    employees: List<Employee>
//) {
//
//    var isOpen by remember { mutableStateOf(false) }
//    val animatedRotation = animateFloatAsState(targetValue = if (!isOpen) 0f else 180f)
//    var chosenResponsibleCharacter by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier.padding(8.dp)
//    ) {
//
//        Card(
//            colors = CardDefaults.cardColors(
//                containerColor = SafeTechColors.Orange,
//                contentColor = Color.White
//            ),
//            modifier = Modifier
//                .clickable {
//                    isOpen = !isOpen
//                }
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                Text(
//                    text = controlGroupName,
//                    style = MaterialTheme.typography.titleLarge,
//                    modifier = Modifier.weight(1f)
//                )
//
//                Icon(
//                    painter = if (isOpen)
//                        painterResource(id = R.drawable.up)
//                    else
//                        painterResource(id = R.drawable.down),
//                    contentDescription = "arrow",
//                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                        .rotate(animatedRotation.value)
//                )
//            }
//        }
//
//        AnimatedVisibility(visible = isOpen) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .heightIn(max = (40 * (employees.size + 4)).dp),
//            ) {
//                LazyColumn {
//                    itemsIndexed(items) { index, item ->
//                        ControlIndicatorItemScreen(
//                            item = item,
//                            choseItemAction = choseItemAction
//                        )
//
//                        if (index < items.size - 1) { // Добавляем разделитель, кроме последнего элемента
//                            Divider(
//                                color = Color.Gray,
//                                thickness = 1.dp
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//}

