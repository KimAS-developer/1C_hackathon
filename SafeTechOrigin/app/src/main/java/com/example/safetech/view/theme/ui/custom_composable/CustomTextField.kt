package com.example.safetech.view.theme.ui.custom_composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SafeTechTextField(
    label: String,
    textColor: Color = Color.Black,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    text: String = "",
    onValueChanged: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {

        Text(
            text = label,
            color = textColor,
            style = MaterialTheme.typography.bodyLarge
        )

        BasicTextField(
            value = text,
            onValueChange = onValueChanged,
            textStyle = TextStyle(color = textColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp)
                .height(IntrinsicSize.Min),
            visualTransformation = visualTransformation
        )

        Divider(
            modifier = Modifier
                .padding(top = 4.dp)
                .height(2.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomTextField() {
    val (text, onTextChanged) = remember { mutableStateOf("Полозкова Полина") }

    Box(modifier = Modifier.padding(24.dp)) {
        SafeTechTextField(
            label = "Логин",
            text = text,
            onValueChanged = onTextChanged
        )
    }
}