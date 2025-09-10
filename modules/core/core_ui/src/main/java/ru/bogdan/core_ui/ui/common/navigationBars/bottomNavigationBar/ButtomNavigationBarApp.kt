package ru.bogdan.core_ui.ui.common.navigationBars.bottomNavigationBar

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavigationAppBar(

    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier.height(48.dp),
    ) {
        content()
    }
}

@Composable
fun RowScope.BoxItemNavigation(
    isSelected: Boolean,
    iconId: Int,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    labelSize: TextUnit = 14.sp,
    iconSize: Dp = 24.dp,
    tint: Color = Color.Blue
) {
    val selectedSize = animateDpAsState(
        targetValue = if (!isSelected) iconSize else 30.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy)
    )
    Box(
        modifier = modifier
            .height(52.dp)
            .weight(1f)
            .clickable {
                onClick()
            },
    ) {
        Icon(
            modifier = Modifier
                .size(selectedSize.value)
                .align(if (!isSelected) Alignment.Center else Alignment.TopCenter),
            painter = painterResource(id = iconId),
            contentDescription = label,
            tint = tint
        )
        if (isSelected) {
            Text(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = label,
                fontSize = labelSize,
                color = tint,
            )
        }
    }
}