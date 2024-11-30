package com.example.safetech.view.theme.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.safetech.R
import com.example.safetech.view.theme.models.UiObjectChecklist
import com.example.safetech.view.theme.style.SafeTechColors

@Composable
fun ObjectChecklistItemScreen(
    item: UiObjectChecklist,
    onClick: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(
                    if (item.isChecked)
                        Color.Gray
                    else
                        SafeTechColors.Orange
                )
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = item.structuralUnit,
                fontSize = 20.sp,
                color = Color.White
            )

            Image(
                painter = painterResource(id = R.drawable.arrow_up),
                contentDescription = "Переход на объект",
                modifier = Modifier
                    .clickable {
                        onClick()
                    }
                    .align(Alignment.CenterVertically)
                    .padding(8.dp)
                    .size(48.dp)
            )
        }
    }
}

@Preview
@Composable
fun ObjectChecklistItemPreview(
) {
    ObjectChecklistItemScreen(
        item = UiObjectChecklist(
            number = "2",
            structuralUnit = "Цех металлопроката",
            responsibleForObject = "Некрасов Максим Кириллович",
            checklist = emptyList(),
            employees = emptyList(),
            isChecked = false,
            violationsList = mutableListOf()
        )
    ) {

    }
}