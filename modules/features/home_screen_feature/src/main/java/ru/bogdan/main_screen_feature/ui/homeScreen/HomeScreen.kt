package ru.bogdan.main_screen_feature.ui.homeScreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import domain.mechanic.MachineState
import ru.bogdan.main_screen_feature.R
import ru.bogdan.main_screen_feature.ui.homeScreen.userCard.UserCard
import ru.bogdan.main_screen_feature.utils.getHomeScreenComponent
import ui.theme.*

@Composable
fun HomeScreen(
    modifier: Modifier,
    userId: String,

    ) {
    val component = getHomeScreenComponent(userId)
    val viewModel: HomeScreenViewModel = viewModel(factory = component.getViewModelFactory())
    val state = viewModel.state.collectAsState()
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.user_card),
        )
        Box(
            modifier = Modifier
                .background(MainGradient)
                .fillMaxSize()
        )
        UserCard(
            modifier = Modifier.fillMaxSize(),
            imageSize = 200.dp,
            strokeWidth = 15.dp,
            colorNearPhoto = Emerald,
            photoContent = {
                UserPhoto(state = state)
            },
            nameContent = {
                NameContent(state = state)
            },
            dataContent = {
                PaiChart(
                    modifier = Modifier.fillMaxWidth(),
                    state = state,
                )
            }
        )
    }
}

@Composable
fun UserPhoto(
    state: State<HomeScreenState>
) {
    AsyncImage(
        model = state.value.photo ?: "",
        contentDescription = state.value.surname,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun NameContent(
    state: State<HomeScreenState>
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                text = state.value.name,
                style = Typography.headlineLarge,
                color = Emerald
            )
            Spacer(Modifier.width(spacing.medium))
            Text(
                text = state.value.surname,
                style = Typography.headlineLarge,
                color = Emerald
            )
        }
        Spacer(Modifier.height(spacing.small))
        if (state.value.patronymic.isNotBlank()) {
            Text(
                text = state.value.patronymic,
                style = Typography.headlineLarge,
                color = Emerald
            )
            Spacer(Modifier.height(spacing.small))
        }
        Text(
            text = state.value.role.name,
            style = Typography.headlineLarge,
            color = Emerald
        )
    }
}

@Composable
fun PaiChart(
    state: State<HomeScreenState>,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier.background(
            color = Beige,
            shape = RoundedCornerShape(spacing.medium),
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = modifier.padding(spacing.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Canvas(modifier = Modifier.size(100.dp)) {
                var startAngle = 0f
                state.value.infoAboutMachines.forEach { info ->
                    drawArc(
                        color = info.color,
                        startAngle = startAngle,
                        sweepAngle = info.percentage,
                        useCenter = true,
                        style = Fill,
                        size = Size(100.dp.toPx(), 100.dp.toPx()),
                    )
                    startAngle += info.percentage
                }
            }
            Spacer(Modifier.width(spacing.medium))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                state.value.infoAboutMachines.forEach { info ->
                    TextWithColor(
                        modifier = Modifier.fillMaxWidth(),
                        title = info.title,
                        color = info.color,
                        count = info.count,
                    )
                }

            }
        }
    }
}

@Composable
fun TextWithColor(title: String, count: Int, color: Color, modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier
            .size(spacing.medium)
            .background(color, shape = CircleShape))
        Spacer(Modifier.width(spacing.small))
        Text(
            text = "$title: $count",
            maxLines = 1,
            fontSize = 22.sp,
        )
    }
}