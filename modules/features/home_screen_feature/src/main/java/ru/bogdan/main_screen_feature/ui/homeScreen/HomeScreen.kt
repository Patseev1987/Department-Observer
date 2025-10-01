package ru.bogdan.main_screen_feature.ui.homeScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import domain.mechanic.MachineType
import ru.bogdan.core_ui.R
import ru.bogdan.core_ui.ui.common.box.BoxWithBackgroundPhoto
import ru.bogdan.core_ui.ui.common.loadingModifier.loadingShimmer
import ru.bogdan.core_ui.ui.theme.Beige
import ru.bogdan.core_ui.ui.theme.Emerald
import ru.bogdan.core_ui.ui.theme.LocalAppTypography
import ru.bogdan.core_ui.ui.theme.LocalSpacing
import ru.bogdan.main_screen_feature.ui.homeScreen.userCard.UserCard
import ru.bogdan.main_screen_feature.utils.getHomeScreenComponent

@Composable
fun HomeScreen(modifier: Modifier) {
    val component = getHomeScreenComponent()
    val viewModel: HomeScreenViewModel = viewModel(factory = component.getViewModelFactory())
    val state = viewModel.state.collectAsState()
    val typography = LocalAppTypography.current
    val spacing = LocalSpacing.current
    BoxWithBackgroundPhoto(
        drawableId = R.drawable.user_card,
    ) {
        UserCard(
            modifier = modifier.testTag("user_card"),
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
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(spacing.small),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        Text(
                            text = state.value.role,
                            style = typography.headlineCursiveLarge,
                            color = Emerald
                        )
                    }
                    item {
                        PaiChart(
                            modifier = Modifier.fillMaxWidth().testTag("pai_chart"),
                            state = state,
                        ) {
                            viewModel.handleIntent(HomeScreenIntent.ShowRepairList(it))
                        }
                    }

                    item {
                        InfoList(
                            modifier = Modifier.fillMaxWidth().testTag("info_list"),
                            state = state,
                            onClick = { viewModel.handleIntent(HomeScreenIntent.ShowInfoList(it)) }
                        )
                    }

                }
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
        placeholder = painterResource(R.drawable.icon),
        contentDescription = state.value.surname,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .loadingShimmer(state.value.isLoading)
    )
}

@Composable
fun NameContent(
    state: State<HomeScreenState>
) {
    val spacing = LocalSpacing.current
    val typography = LocalAppTypography.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                text = state.value.name,
                style = typography.headlineCursiveNormal,
                color = Emerald
            )
            Spacer(Modifier.width(spacing.medium))
            Text(
                text = state.value.surname,
                style = typography.headlineCursiveNormal,
                color = Emerald
            )
        }
        Spacer(Modifier.height(spacing.small))
        if (state.value.patronymic.isNotBlank()) {
            Text(
                text = state.value.patronymic,
                style = typography.headlineCursiveNormal,
                color = Emerald
            )
        }
    }
}

@Composable
fun PaiChart(
    state: State<HomeScreenState>,
    modifier: Modifier = Modifier,
    onClick: (Boolean) -> Unit,
) {
    val spacing = LocalSpacing.current
    val typography = LocalAppTypography.current

    Column(
        modifier = modifier
            .background(
                color = Beige,
                shape = RoundedCornerShape(spacing.medium),
            )
            .clickable {
                onClick(!state.value.isShowRepairList)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .padding(top = spacing.small),
            text = stringResource(R.string.info),
            style = typography.bodyNormal,
            textAlign = TextAlign.Center,
            lineHeight = 46.sp,
            fontWeight = Bold,

            )
        Icon(
            if (state.value.isShowRepairList) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.small),
        ) {
            Row(
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
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    state.value.infoAboutMachines.forEach { info ->
                        TextWithCircle(
                            modifier = Modifier.fillMaxWidth(),
                            title = info.title,
                            color = info.color,
                            count = info.count,
                        )
                    }

                }
            }
            AnimatedVisibility(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 500.dp),
                visible = state.value.isShowRepairList
            ) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = spacing.small)
                        .testTag("repair_list"),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(spacing.small),
                ) {
                    item {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.list_of_repair_machines),
                            textAlign = TextAlign.Center,
                            style = typography.bodyNormal,
                            fontWeight = Bold,
                        )
                    }
                    itemsIndexed(
                        items = state.value.repairList,
                        key = { _, repair -> repair.id }
                    ) { index, repair ->
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = "${index + 1}.",
                                style = typography.bodyLarge
                            )
                            Text(
                                modifier = Modifier
                                    .padding(start = spacing.medium)
                                    .weight(1f),
                                text = "${getStringFromMachineType(repair.type)}\n${repair.name}",
                                style = typography.bodySmall,
                                textAlign = TextAlign.Center,
                                lineHeight = 24.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TextWithCircle(title: String, count: Int, color: Color, modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current
    val typography = LocalAppTypography.current
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(spacing.medium)
                .background(color, shape = CircleShape)
        )
        Spacer(Modifier.width(spacing.small))
        Text(
            text = "$title: $count",
            maxLines = 1, overflow = TextOverflow.Ellipsis,
            style = typography.bodySmall,
        )
    }
}

@Composable
fun getStringFromMachineType(machineType: MachineType): String =
    when (machineType) {
        MachineType.TURNING -> {
            stringResource(R.string.turning)
        }

        MachineType.TURNING_CNC -> {
            stringResource(R.string.turning_cnc)
        }

        MachineType.MILLING_HORIZONTAL -> {
            stringResource(R.string.milling_horizontal)
        }

        MachineType.MILLING_UNIVERSAL -> {
            stringResource(R.string.milling_universal)
        }

        MachineType.MILLING_CNC -> {
            stringResource(R.string.milling_cnc)
        }
    }


@Composable
fun InfoList(
    state: State<HomeScreenState>,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val typography = LocalAppTypography.current
    Column(
        modifier = modifier
            .background(
                color = Beige,
                shape = RoundedCornerShape(spacing.medium),
            )
            .clickable { onClick(!state.value.isShowInfoList) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.news),
            textAlign = TextAlign.Center,
            style = typography.bodyNormal,
            fontWeight = Bold,
        )
        Spacer(modifier = Modifier.height(spacing.small))
        Icon(
            if (state.value.isShowInfoList) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(spacing.small))
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 500.dp),
            visible = state.value.isShowInfoList
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(spacing.small),
            ) {
                item {
                    Text(
                        modifier = Modifier.fillMaxWidth().testTag("news"),
                        text = stringResource(R.string.info_news),
                        textAlign = TextAlign.Center,
                        style = typography.bodyNormal,
                        fontWeight = Bold,
                    )
                }
                itemsIndexed(
                    items = state.value.info,
                    key = { _, info -> info.id }
                ) { index, info ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(spacing.extraSmall),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "${index + 1}.",
                            style = typography.bodyLarge
                        )
                        Spacer(Modifier.width(spacing.small))
                        Column(
                            modifier = Modifier
                                .padding(spacing.extraSmall)
                                .weight(1f)
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(start = spacing.medium),
                                text = info.name,
                                style = typography.bodySmall,
                                fontWeight = Bold,
                                textAlign = TextAlign.Center,
                                lineHeight = 24.sp
                            )
                            Spacer(Modifier.height(spacing.small))
                            Text(
                                modifier = Modifier
                                    .padding(start = spacing.medium),
                                text = info.info,
                                style = typography.bodySmall,
                                textAlign = TextAlign.Start,
                                lineHeight = 18.sp
                            )
                        }

                    }
                }
            }
        }
    }
}
