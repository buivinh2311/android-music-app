package com.example.core_ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AppButton (
    modifier: Modifier = Modifier,
    title: String,
    color: Color,
    border: BorderStroke? = null,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        border = border,
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge
        )
    }
}