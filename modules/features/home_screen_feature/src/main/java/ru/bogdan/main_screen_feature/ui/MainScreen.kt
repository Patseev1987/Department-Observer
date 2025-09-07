package ru.bogdan.main_screen_feature.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import ru.bogdan.main_screen_feature.utils.getHomeScreenComponent
import ui.theme.Emerald
import ui.theme.LocalSpacing
import ui.theme.Typography
import ru.bogdan.main_screen_feature.R
import ui.theme.MainGradient

@Composable
fun MainScreen(
    userId: String,
    modifier: Modifier = Modifier
) {

    val components = getHomeScreenComponent(userId)
    val viewModel: HomeScreenViewModel = viewModel(factory = components.getViewModelFactory())
    val state by viewModel.state.collectAsState()
    Scaffold(
        modifier = modifier,
        topBar = { },
        bottomBar = {
            MainScreenBottomNavigationBar(state.navItems) {
                viewModel.handleIntent(HomeScreenIntent.NavItemClicked(it))
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.user_card),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            UserCard(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = MainGradient
                    )
                    .padding(paddingValues),
                imgUrl = "https://i.guim.co.uk/img/static/sys-images/Sport/Pix/pictures/2012/9/5/1346853660009/Alessandro-Del-Piero--008.jpg?width=465&dpr=1&s=none&crop=none",
                imageSize = 200.dp,
                strokeWidth = 15.dp,
                colorNearPhoto = Emerald,
                name = "Alessandro",
                surname = "Del'Piero",
                occupation = "Developer",
                patronymic = "Ivanovich",
            ) {
                LazyColumn(
                ) {
                    repeat(10) {
                        item {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(Color.Red)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun UserCard(
    imgUrl: String,
    name: String,
    surname: String,
    modifier: Modifier = Modifier,
    patronymic: String? = null,
    occupation: String? = null,
    imageSize: Dp = 80.dp,
    strokeWidth: Dp = 10.dp,
    colorNearPhoto: Color = Color.Blue,
    content: @Composable BoxScope.() -> Unit
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
        AsyncImage(
            modifier = Modifier
                .size(imageSize - strokeWidth)
                .offset(y = strokeWidth)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .background(Color.Red),
            model = imgUrl,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            clipToBounds = true,
        )
        Spacer(modifier = Modifier.height(spacing.large))
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = name,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontStyle = Typography.headlineMedium.fontStyle,
                    fontSize = Typography.headlineMedium.fontSize,
                    color = Emerald,
                )
                Text(
                    text = surname,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = spacing.medium),
                    fontStyle = Typography.headlineMedium.fontStyle,
                    fontSize = Typography.headlineMedium.fontSize,
                    color = Emerald,
                )
            }
            patronymic?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = spacing.small),
                    fontStyle = Typography.headlineMedium.fontStyle,
                    fontSize = Typography.headlineMedium.fontSize,
                    color = Emerald,
                )
            }
        }
        occupation?.let {
            Text(
                text = it,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = spacing.small),
                color = Emerald,
                fontStyle = Typography.displaySmall.fontStyle,
                fontSize = Typography.displaySmall.fontSize,
                fontWeight = FontWeight.Bold,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(
                    start = strokeWidth + spacing.medium,
                    end = strokeWidth + spacing.medium,
                    bottom = strokeWidth + spacing.medium,
                    top =  spacing.medium,
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = spacing.medium,
                        topEnd = spacing.medium,
                        bottomEnd = 25.dp,
                        bottomStart = 25.dp
                    )
                )
                .padding(spacing.medium)

        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun UserCardPreview(

) {
    UserCard(
        modifier = Modifier.background(Color.Black),
        imgUrl = "https://thumbsd.dreamstime.com/b/black-construction-worker-18075617.jpg",
        imageSize = 200.dp,
        strokeWidth = 30.dp,
        colorNearPhoto = Emerald.copy(0.8f),
        name = "Alessandro",
        surname = "Del'Piero",
        patronymic = "Ivanovich",
        occupation = "Developer",
    ) {

    }
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
            // colors = listOf(colorNearPhoto,colorNearPhoto,colorNearPhoto,colorNearPhoto, Color.Blue),
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

@Composable
fun MainScreenBottomNavigationBar(
    navigationItems: List<ObserverNavigationItem>,
    onClick: (ObserverNavigationItem) -> Unit
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        navigationItems.forEach { navigationItem ->
            NavigationBarItem(
                selected = true,
                onClick = {
                    onClick.invoke(navigationItem)
                },
                icon = {
                    Icon(
                        painter = painterResource(navigationItem.drawableId),
                        contentDescription = navigationItem.title,
                    )
                }
            )
        }
    }
}