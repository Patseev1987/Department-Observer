package ui.common.loading

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import ui.theme.Emerald

@Composable
fun AppLoading(
    paths: List<Path>,
    modifier: Modifier = Modifier
) {
//    val offset = Animatable(0f)
//
//    LaunchedEffect(Unit) {
//
//        offset.animateTo(targetValue = 100f, animationSpec = tween(durationMillis = 1000,))
//    }
    Box(modifier = modifier.background(color = Color.Red).size(200.dp)) {
        Canvas(
            modifier = Modifier,
        ) {
            paths.forEach { drawPath(it, color = Emerald, style = Stroke(width = 2.dp.toPx())) }
            //  drawCircle(color = Emerald, radius = 30f , center = Offset(center.x, center.y))
        }
    }
}