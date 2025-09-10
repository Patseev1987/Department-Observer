package ru.bogdan.machine_list.ui.machineDetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import domain.mechanic.Oil
import ru.bogdan.core_ui.ui.theme.LocalAppTypography
import ru.bogdan.core_ui.ui.theme.LocalSpacing


@Composable
fun <T> SheetWithTitleMapData(
    title: String,
    data: Map<T, Int>,
    content: @Composable BoxScope.(T) -> Unit,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.headlineLarge,
    cornerRadius: Dp = 4.dp,
    borderColor: Color = Color.Black,
    borderWidth: Dp = 1.dp,
    color: Color = Color.Black,
) {
    var expand by rememberSaveable { mutableStateOf(false) }

    val cornerRadiusTitle = animateDpAsState(
        targetValue = if (data.isEmpty() || !expand) cornerRadius else 0.dp,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 100,
        )
    )
    Column(modifier = modifier.padding(16.dp)) {
        CommonPartWithSheets(
            title = title,
            titleStyle = titleStyle,
            borderWidth = borderWidth,
            borderColor = borderColor,
            cornerRadius = cornerRadius,
            cornerRadiusTitle = cornerRadiusTitle,
            expand = expand,
            onExpand = {
                expand = it
            }
        )
        AnimatedVisibility(
            visible = expand,
            enter = expandVertically(
                animationSpec = tween(delayMillis = 200, durationMillis = 300, easing = LinearOutSlowInEasing)
            ),
            exit = shrinkVertically(animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)),
        ) {
            SheetWithTitleFullFormWithMapData(
                data = data,
                cornerRadius = cornerRadius,
                borderColor = borderColor,
                borderWidth = borderWidth,
                content = content,
                color = color
            )
        }
    }
}

@Composable
fun <T> SheetWithTitleFullFormWithMapData(
    data: Map<T, Int>,
    content: @Composable BoxScope.(T) -> Unit,
    cornerRadius: Dp = 4.dp,
    borderColor: Color = Color.Black,
    borderWidth: Dp = 2.dp,
    color: Color = Color.Black,
) {
    var counter = 0
    val spacing = LocalSpacing.current
    Column(modifier = Modifier.fillMaxWidth()) {
        data.forEach { (key, value) ->
            counter++
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = -borderWidth * counter)
                    .border(
                        width = borderWidth,
                        color = borderColor,
                        shape = if (counter != data.size) RectangleShape else RoundedCornerShape(
                            bottomStart = cornerRadius,
                            bottomEnd = cornerRadius,
                        )
                    )
                    .drawWithContent {
                        drawContent()
                        drawLine(
                            color = borderColor,
                            strokeWidth = borderWidth.toPx(),
                            start = Offset(x = size.width * 0.7f, 0f),
                            end = Offset(x = size.width * 0.7f, size.height),
                        )
                    }
                    .padding(spacing.medium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.75f)
                        .padding(end = spacing.small),
                    contentAlignment = Alignment.Center
                ) {
                    content(key)
                }
                Box(
                    modifier = Modifier
                        .weight(0.25f)
                        .offset(y = -borderWidth * counter),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = value.toString(),
                        style = LocalAppTypography.current.bodyLarge,
                        maxLines = 1,
                        color = color
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSheetWithTitle() {
    SheetWithTitleMapData(
        modifier = Modifier.padding(20.dp),
        title = "Oil",
        cornerRadius = 16.dp,
        borderWidth = 2.dp,
        data = mapOf(
            Oil.NONE to 20,
        ),
        content = {
            OilItem(
                oil = it,
            )
        }
    )
}


@Composable
fun <T> SheetWithTitle(
    title: String,
    data: List<T>,
    content: @Composable BoxScope.(T) -> Unit,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.headlineLarge,
    cornerRadius: Dp = 4.dp,
    borderColor: Color = Color.Black,
    borderWidth: Dp = 1.dp,
    onClick: (T) -> Unit = {},
) {
    var expand by rememberSaveable { mutableStateOf(false) }

    val cornerRadiusTitle = animateDpAsState(
        targetValue = if (data.isEmpty() || !expand) cornerRadius else 0.dp,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 100,
        )
    )
    Column(modifier = modifier.padding(16.dp)) {
        CommonPartWithSheets(
            title = title,
            titleStyle = titleStyle,
            borderWidth = borderWidth,
            borderColor = borderColor,
            cornerRadius = cornerRadius,
            cornerRadiusTitle = cornerRadiusTitle,
            expand = expand,
            onExpand = {
                expand = it
            }
        )
        AnimatedVisibility(
            visible = expand,
            enter = expandVertically(
                animationSpec = tween(delayMillis = 200, durationMillis = 300, easing = LinearOutSlowInEasing)
            ),
            exit = shrinkVertically(animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)),
        ) {
            SheetWithTitleFullForm(
                data = data,
                cornerRadius = cornerRadius,
                borderColor = borderColor,
                borderWidth = borderWidth,
                content = content,
                onClick = onClick
            )
        }
    }
}

@Composable
fun <T> SheetWithTitleFullForm(
    data: List<T>,
    content: @Composable BoxScope.(T) -> Unit,
    cornerRadius: Dp = 4.dp,
    borderColor: Color = Color.Black,
    borderWidth: Dp = 2.dp,
    onClick: (T) -> Unit = {},
) {
    var counter = 0
    val spacing = LocalSpacing.current
    Column(modifier = Modifier.fillMaxWidth()) {
        data.forEach { item ->
            counter++
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = -borderWidth * counter)
                    .border(
                        width = borderWidth,
                        color = borderColor,
                        shape = if (counter != data.size) RectangleShape else RoundedCornerShape(
                            bottomStart = cornerRadius,
                            bottomEnd = cornerRadius,
                        )
                    )
                    .padding(spacing.medium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onClick(item)
                        }
                        .padding(end = spacing.small),
                    contentAlignment = Alignment.Center
                ) {
                    content(item)
                }
            }
        }
    }
}

@Composable
fun CommonPartWithSheets(
    title: String,
    titleStyle: TextStyle,
    borderWidth: Dp,
    borderColor: Color,
    cornerRadius: Dp,
    cornerRadiusTitle: State<Dp>,
    expand: Boolean,
    onExpand: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(
                    topStart = cornerRadius,
                    topEnd = cornerRadius,
                    bottomEnd = cornerRadiusTitle.value,
                    bottomStart = cornerRadiusTitle.value,
                )
            )
            .padding(cornerRadius)
            .clickable {
                onExpand(!expand)
            }
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            style = titleStyle,
        )
        Icon(
            modifier = Modifier.align(Alignment.CenterEnd),
            imageVector = if (expand) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
            contentDescription = null,
            tint = borderColor
        )

    }
}