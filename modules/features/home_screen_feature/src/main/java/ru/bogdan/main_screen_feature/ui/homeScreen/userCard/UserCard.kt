package ru.bogdan.main_screen_feature.ui.homeScreen.userCard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.bogdan.core_ui.ui.theme.Emerald
import ru.bogdan.core_ui.ui.theme.LocalSpacing
import ru.bogdan.core_ui.ui.theme.Typography


@Composable
fun UserCard(
    dataContent: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    imageSize: Dp = 80.dp,
    strokeWidth: Dp = 10.dp,
    colorNearPhoto: Color = Color.Blue,
    photoContent: @Composable (BoxScope.() -> Unit)? = null,
    nameContent: @Composable (BoxScope.() -> Unit) = {},
) {
    val spacing = LocalSpacing.current

    val colorNearPhoto = remember {
        colorNearPhoto.copy(alpha = 0.5f)
    }
    val imageSize = remember {
        when {
            imageSize < 50.dp -> 50.dp
            imageSize > 250.dp -> 250.dp
            else -> imageSize
        }
    }

    val strokeWidth = remember {
        when {
            strokeWidth < 10.dp -> 10.dp
            strokeWidth > 30.dp -> 30.dp
            else -> strokeWidth
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                drawUserCardFrame(
                    imageSize = imageSize,
                    strokeWidth = strokeWidth,
                    colorNearPhoto = colorNearPhoto
                )
            }
    ) {
        Box(
            modifier = Modifier
                .size(imageSize - strokeWidth)
                .offset(y = strokeWidth)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .background(Color.Red),
            contentAlignment = Alignment.Center,
        ) {
            photoContent?.invoke(this)
        }

        Spacer(modifier = Modifier.height(spacing.large))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = spacing.extraLarge, max = 400.dp),
        ) {
            nameContent.invoke(this)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(
                    start = strokeWidth + spacing.medium,
                    end = strokeWidth + spacing.medium,
                    bottom = strokeWidth + spacing.medium,
                    top = spacing.medium,
                )
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(
                        topStart = spacing.medium,
                        topEnd = spacing.medium,
                        bottomEnd = 25.dp,
                        bottomStart = 25.dp
                    )
                )
                .padding(spacing.medium)

        ) {
            dataContent.invoke(this)
        }
    }
}

@Preview
@Composable
private fun UserCardPreview(

) {
    UserCard(
        modifier = Modifier.background(Color.Black),
        photoContent = {
        },
        imageSize = 200.dp,
        strokeWidth = 30.dp,
        colorNearPhoto = Emerald.copy(0.8f),
        dataContent = {
            Box(modifier = Modifier.size(40.dp).background(Color.White))
        },
        nameContent = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Alessandro",
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontStyle = Typography.headlineMedium.fontStyle,
                        fontSize = Typography.headlineMedium.fontSize,
                        color = Emerald,
                    )
                    Text(
                        text = "Del'Piero",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 16.dp),
                        fontStyle = Typography.headlineMedium.fontStyle,
                        fontSize = Typography.headlineMedium.fontSize,
                        color = Emerald,
                    )
                }

                Text(
                    text = "Ivanovich",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp),
                    fontStyle = Typography.headlineMedium.fontStyle,
                    fontSize = Typography.headlineMedium.fontSize,
                    color = Emerald,
                )

                Text(
                    text = "Developer",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp),
                    color = Emerald,
                    fontStyle = Typography.displaySmall.fontStyle,
                    fontSize = Typography.displaySmall.fontSize,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    )
}


fun DrawScope.drawUserCardFrame(
    imageSize: Dp,
    strokeWidth: Dp,
    colorNearPhoto: Color = Color.Blue,
    cornerRadius: Dp = 100.dp
) {
    val percentageBigCircle = strokeWidth.toPx() / (imageSize.toPx() / 2 + strokeWidth.toPx() / 2)

    val percentageForArcs = strokeWidth.toPx() / (cornerRadius.toPx() * 1.2f / 2)

    drawLine(
        brush = Brush.verticalGradient(
            colors = listOf(Color.Transparent, colorNearPhoto),
            startY = imageSize.toPx() / 2,
            endY = imageSize.toPx() / 2 + (strokeWidth.toPx())
        ),
        start = Offset(
            x = strokeWidth.toPx() / 2 + cornerRadius.toPx() / 2,
            y = imageSize.toPx() / 2 + strokeWidth.toPx() / 2,
        ),
        end = Offset(
            x = size.width / 2 - imageSize.toPx() / 2 - strokeWidth.toPx() / 2,
            y = imageSize.toPx() / 2 + strokeWidth.toPx() / 2,
        ),
        strokeWidth = strokeWidth.toPx()
    )
    drawArc(
        brush = Brush.radialGradient(
            center = Offset(
                x = cornerRadius.toPx() / 2 + strokeWidth.toPx() / 2,
                y = cornerRadius.toPx() / 2 + (imageSize.toPx() / 2 + strokeWidth.toPx() / 2)
            ),
            radius = cornerRadius.toPx() / 2 + strokeWidth.toPx() / 2,
            colorStops = arrayOf(1 - percentageForArcs to colorNearPhoto, 1f to Color.Transparent),
            tileMode = TileMode.Clamp
        ),
        style = Stroke(width = strokeWidth.toPx()),
        topLeft = Offset(
            x = strokeWidth.toPx() / 2,
            y = imageSize.toPx() / 2 + strokeWidth.toPx() / 2
        ),
        useCenter = false,
        size = Size(cornerRadius.toPx(), cornerRadius.toPx()),
        startAngle = 180f,
        sweepAngle = 90f
    )
    drawLine(
        brush = Brush.horizontalGradient(
            colors = listOf(Color.Transparent, colorNearPhoto),
            startX = 0f,
            endX = strokeWidth.toPx()
        ),
        start = Offset(
            x = strokeWidth.toPx() / 2,
            y = imageSize.toPx() / 2 + strokeWidth.toPx() / 2 + cornerRadius.toPx() / 2,
        ),
        end = Offset(
            x = strokeWidth.toPx() / 2,
            y = size.height - strokeWidth.toPx() / 2 - cornerRadius.toPx() / 2,
        ),
        strokeWidth = strokeWidth.toPx()
    )
    drawLine(
        brush = Brush.verticalGradient(
            colors = listOf(colorNearPhoto, Color.Transparent),
            startY = size.height - strokeWidth.toPx(),
            endY = size.height
        ),
        start = Offset(
            x = strokeWidth.toPx() / 2 + cornerRadius.toPx() / 2,
            y = size.height - strokeWidth.toPx() / 2,
        ),
        end = Offset(
            x = size.width - (strokeWidth.toPx() / 2 + cornerRadius.toPx() / 2),
            y = size.height - strokeWidth.toPx() / 2,
        ),
        strokeWidth = strokeWidth.toPx()
    )
    drawArc(
        brush = Brush.radialGradient(
            center = Offset(
                x = cornerRadius.toPx() / 2 + strokeWidth.toPx() / 2,
                y = size.height - cornerRadius.toPx() / 2 - strokeWidth.toPx() / 2,
            ),
            radius = cornerRadius.toPx() / 2 + strokeWidth.toPx() / 2,
            colorStops = arrayOf(1 - percentageForArcs to colorNearPhoto, 1f to Color.Transparent),
            tileMode = TileMode.Clamp
        ),
        style = Stroke(width = strokeWidth.toPx()),
        topLeft = Offset(
            x = strokeWidth.toPx() / 2,
            y = size.height - cornerRadius.toPx() - strokeWidth.toPx() / 2
        ),
        useCenter = false,
        size = Size(cornerRadius.toPx(), cornerRadius.toPx()),
        startAngle = 90f,
        sweepAngle = 90f
    )
    drawArc(
        brush = Brush.radialGradient(
            center = Offset(
                x = size.width - cornerRadius.toPx() / 2 - strokeWidth.toPx() / 2,
                y = size.height - cornerRadius.toPx() / 2 - strokeWidth.toPx() / 2,
            ),
            radius = cornerRadius.toPx() / 2 + strokeWidth.toPx() / 2,
            colorStops = arrayOf(1 - percentageForArcs to colorNearPhoto, 1f to Color.Transparent),
            tileMode = TileMode.Clamp
        ),
        style = Stroke(width = strokeWidth.toPx()),
        topLeft = Offset(
            x = size.width - cornerRadius.toPx() - strokeWidth.toPx() / 2,
            y = size.height - cornerRadius.toPx() - strokeWidth.toPx() / 2
        ),
        useCenter = false,
        size = Size(cornerRadius.toPx(), cornerRadius.toPx()),
        startAngle = 90f,
        sweepAngle = -90f
    )
    drawLine(
        brush = Brush.verticalGradient(
            colors = listOf(Color.Transparent, colorNearPhoto),
            startY = imageSize.toPx() / 2,
            endY = imageSize.toPx() / 2 + (strokeWidth.toPx())
        ),
        start = Offset(
            x = size.width - (strokeWidth.toPx() / 2 + cornerRadius.toPx() / 2),
            y = imageSize.toPx() / 2 + strokeWidth.toPx() / 2,
        ),
        end = Offset(
            x = size.width / 2 + imageSize.toPx() / 2 + strokeWidth.toPx() / 2,
            y = imageSize.toPx() / 2 + strokeWidth.toPx() / 2,
        ),
        strokeWidth = strokeWidth.toPx()
    )
    drawArc(
        brush = Brush.radialGradient(
            center = Offset(
                x = size.width - cornerRadius.toPx() / 2 - strokeWidth.toPx() / 2,
                y = cornerRadius.toPx() / 2 + (imageSize.toPx() / 2 + strokeWidth.toPx() / 2)
            ),
            radius = cornerRadius.toPx() / 2 + strokeWidth.toPx() / 2,
            colorStops = arrayOf(1 - percentageForArcs to colorNearPhoto, 1f to Color.Transparent),
            tileMode = TileMode.Clamp
        ),
        style = Stroke(width = strokeWidth.toPx()),
        topLeft = Offset(
            x = size.width - strokeWidth.toPx() / 2 - cornerRadius.toPx(),
            y = imageSize.toPx() / 2 + strokeWidth.toPx() / 2
        ),
        useCenter = false,
        size = Size(cornerRadius.toPx(), cornerRadius.toPx()),
        startAngle = -90f,
        sweepAngle = 90f
    )
    drawLine(
        brush = Brush.horizontalGradient(
            colors = listOf(colorNearPhoto, Color.Transparent),
            startX = size.width - strokeWidth.toPx(),
            endX = size.width
        ),
        start = Offset(
            x = size.width - strokeWidth.toPx() / 2,
            y = imageSize.toPx() / 2 + strokeWidth.toPx() / 2 + cornerRadius.toPx() / 2,
        ),
        end = Offset(
            x = size.width - strokeWidth.toPx() / 2,
            y = size.height - strokeWidth.toPx() / 2 - cornerRadius.toPx() / 2,
        ),
        strokeWidth = strokeWidth.toPx()
    )
    drawCircle(
        brush = Brush.radialGradient(
            center = Offset(
                x = size.width / 2,
                y = imageSize.toPx() / 2 + strokeWidth.toPx() / 2
            ),
            radius = imageSize.toPx() / 2 + strokeWidth.toPx() / 2,
            colorStops = arrayOf(1 - percentageBigCircle to colorNearPhoto, 1f to Color.Transparent),
            tileMode = TileMode.Clamp
        ),
        radius = imageSize.toPx() / 2,
        center = Offset(
            x = size.width / 2,
            y = imageSize.toPx() / 2 + strokeWidth.toPx() / 2
        ),
        style = Stroke(width = strokeWidth.toPx())
    )
}