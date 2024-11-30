package com.example.safetech.view.theme.ui.custom_composable

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.safetech.view.theme.style.SafeTechColors

enum class SafeTechButtonStyle {
    Orange,
    Purple
}

@Composable
fun SafeTechButton(
    text: String,
    style: SafeTechButtonStyle,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {

    val containerColor = getButtonStyle(style)

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Text(
            text = text.uppercase()
        )
    }

}

private fun getButtonStyle(style: SafeTechButtonStyle): Color {
    return when (style) {
        SafeTechButtonStyle.Orange -> SafeTechColors.Orange
        SafeTechButtonStyle.Purple -> SafeTechColors.Purple
    }
}

@Preview
@Composable
fun SafeTechButtonPreview() {

    SafeTechButton(
        text = "Войти",
        style = SafeTechButtonStyle.Orange) {
        
    }

}