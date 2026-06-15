package com.example.feature_player.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_utils.util.PlayerUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerProgress(
    currentPosition: Long,
    duration: Long,
    onPositionChange: (Float) -> Unit
) {
    var isDragging by remember {
        mutableStateOf(false)
    }

    var position by remember(currentPosition) {
        mutableStateOf(currentPosition.toFloat())
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Slider(
            value = if (isDragging) position else currentPosition.toFloat(),
            onValueChange = {
                isDragging = true
                position = it
            },
            onValueChangeFinished = {
                onPositionChange(position)
                isDragging = false
            },
            modifier = Modifier.height(AppDimens.Space.Xl),
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.Gray
            ),
            thumb = {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = Color.White,
                            shape = CircleShape
                        )
                )
            },
            track = { sliderState ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .clip(shape = CircleShape)
                        .background(Color.Gray.copy(alpha = 0.4f))
                ) {
                    val progress = if (sliderState.valueRange.endInclusive > 0)
                        sliderState.value / sliderState.valueRange.endInclusive
                    else 0f
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(progress)
                            .background(Color.White)
                    )
                }
            },
            valueRange = 0f..duration.toFloat()
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = PlayerUtil.durationToString(currentPosition/1000),
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = PlayerUtil.durationToString(duration/1000),
                color = Color.White
            )
        }
    }
}