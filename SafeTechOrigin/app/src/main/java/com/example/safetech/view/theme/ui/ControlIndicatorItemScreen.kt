package com.example.safetech.view.theme.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.safetech.MockData
import com.example.safetech.R
import com.example.safetech.view.theme.models.UiControlItem

private enum class OkState {
    NONE,
    OK,
    NOT_OK
}

@Composable
fun ControlIndicatorItemScreen(
    item: UiControlItem,
    choseItemAction: (item: UiControlItem) -> Unit
) {

    var okState by remember { mutableStateOf(OkState.NONE) }

    Row(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = item.controlIndicator,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        )

        Row(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                painter = if (item.isViolation)
                    painterResource(id = R.drawable.not_ok)
                else
                    painterResource(id = R.drawable.ok),
                contentDescription = "ok",
                tint = if (item.isViolation)
                    Color.Red
                else
                    Color.Green,
                modifier = Modifier
                    .clickable {
                        okState = OkState.OK
                    }
            )

            Icon(
                painter = painterResource(id = R.drawable.forward),
                contentDescription = "forward",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        okState = OkState.OK
                        choseItemAction(item)
                    }
            )
        }
    }
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun ControlIndicatorItemScreenPreview() {
    
    val item = MockData.uiControlItem
    
    ControlIndicatorItemScreen(item = item) {

    }
    
}