package ru.bogdan.core_ui.ui.common.box

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.bogdan.core_ui.R
import ru.bogdan.core_ui.ui.theme.Emerald
import ru.bogdan.core_ui.ui.theme.MainGradient

@Composable
fun BoxWithBackgroundPhoto(
    drawableId: Int,
    modifier: Modifier = Modifier,
    brush: Brush = MainGradient,
    content: @Composable BoxScope.() -> Unit

) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawContent()
                    drawRect(brush = brush, size = size)
                },
            painter = painterResource(drawableId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        content()
    }
}

@Preview
@Composable
private fun BoxWithBackgroundPhotoPreview(
) {
    BoxWithBackgroundPhoto(
        modifier = Modifier.fillMaxSize(),
        drawableId = R.drawable.machines_background
    ) {
        Text(
            text = "Hello!",
            color = Emerald
        )
    }
}