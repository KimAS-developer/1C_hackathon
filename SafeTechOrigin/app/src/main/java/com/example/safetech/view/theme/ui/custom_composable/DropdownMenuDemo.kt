package com.example.safetech.view.theme.ui.custom_composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.safetech.MockData
import com.example.safetech.R
import com.example.safetech.data.network.models.Employee
import com.example.safetech.view.theme.style.SafeTechColors

@Composable
fun DropDownDemo(
    responsibleForViolation: String,
    employees: List<Employee>,
    modifier: Modifier = Modifier,
    onChoseAction: (String) -> Unit
) {

    val index = employees.indexOfFirst { it.fullName == responsibleForViolation }

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val itemPosition = remember {
        mutableStateOf(if (index == -1) 0 else index)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        Box {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(SafeTechColors.Orange)
                    .clickable {
                        isDropDownExpanded.value = true
                    }
                    .padding(8.dp)
            ) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = employees[itemPosition.value].fullName,
                    color = Color.White
                )
                Image(
                    painter = painterResource(id = R.drawable.down),
                    contentDescription = "DropDown Icon",
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                }) {
                employees.forEachIndexed { index, employee ->
                    DropdownMenuItem(text = {
                        Text(text = employee.fullName)
                    },
                        onClick = {
                            isDropDownExpanded.value = false
                            itemPosition.value = index
                            onChoseAction(employees[index].fullName)
                        })
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DropDownDemoPreview() {

    val employees = MockData.employees

//    DropDownDemo(
//        employees
//    ) {
//
//    }
}