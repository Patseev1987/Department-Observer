package ru.bogdan.core_ui.ui.common.loadingModifier

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.bogdan.core_ui.ui.theme.Emerald


fun Modifier.loadingShimmer(
    showShimmer: Boolean = true,
    color: Color = Emerald,
    gradientWidth: Float = 100f
): Modifier = composed {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = screenHeight.toPx(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    if (showShimmer) {
        Modifier.drawBehind {
            val brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    color.copy(alpha = 0.5f),
                    Color.Transparent
                ),
                startY = translateAnimation.value - gradientWidth,
                endY = translateAnimation.value + gradientWidth,
            )
            drawRect(brush = brush)
        }
    } else {
        Modifier
    }
}

@Preview
@Composable
private fun LoadingShimmerPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .loadingShimmer(
                color = Emerald
            )
    )
}
@Composable
fun Dp.toPx(): Float {
   return this.value * LocalDensity.current.density
}