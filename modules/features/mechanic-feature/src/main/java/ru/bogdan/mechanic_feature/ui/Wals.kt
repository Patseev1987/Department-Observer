package ru.bogdan.mechanic_feature.ui

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp
import ru.bogdan.mechanic_feature.utils.paths

@Composable
fun Walls(modifier: Modifier = Modifier) {
    val thisPaths = paths.map { path -> PathParser().parsePathString(path).toPath() }

    Canvas(modifier = modifier){
        thisPaths.forEach {
            drawPath(
                it,
                Color.Black,
                style = Stroke(
                    width = 2.dp.toPx(),

                )
            )
        }
    }
}