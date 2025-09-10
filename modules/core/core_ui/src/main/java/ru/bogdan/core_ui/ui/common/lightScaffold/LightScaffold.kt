package ru.bogdan.core_ui.ui.common.lightScaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.bogdan.core_ui.ui.theme.LocalSpacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LightScaffold(
    content: @Composable BoxScope.(PaddingValues) -> Unit,
    modifier: Modifier = Modifier,
    topBar: @Composable (RowScope.() -> Unit)? = null,
    bottomBar: @Composable (RowScope.() -> Unit)? = null
) {
    val topPadding = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()
    val bottomPadding = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()
    val topBarHeight = remember {
        if (topBar == null) 0.dp else 56.dp
    }
    val bottomBarHeight = remember {
        if (bottomBar == null) 0.dp else 56.dp
    }
val spacing = LocalSpacing.current

    Box(
        modifier = modifier,
    ) {
        topBar?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = topPadding)
                    .requiredHeightIn(max = 56.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.End,
                content = topBar
            )
        }
        content(
            PaddingValues(
                top = topPadding + topBarHeight,
                bottom = bottomPadding + bottomBarHeight,
                start = spacing.small,
                end = spacing.small
            ),
        )
        bottomBar?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = bottomPadding)
                    .requiredHeightIn(max = 56.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceAround,
                content = bottomBar
            )
        }
    }
}

@Preview(
    device = Devices.PHONE,
)
@Composable
private fun PreviewLightScaffold() {
    LightScaffold(
        topBar = {
            Button(
                modifier = Modifier.size(200.dp),
                onClick = { }) {

                Text("Text1")
            }
            Button(onClick = { }) {
                Text("Text4")
            }
        },
        bottomBar = {
            Button(onClick = { }) {
                Text("Text2")
            }
            Button(onClick = { }) {
                Text("Text3")
            }
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue)
            )
        }
    )
}