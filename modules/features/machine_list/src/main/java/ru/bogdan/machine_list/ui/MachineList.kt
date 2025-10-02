package ru.bogdan.machine_list.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import domain.mechanic.MachineModel
import domain.mechanic.MachineState
import domain.mechanic.MachineType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import navigation.NavigationEvent
import ru.bogdan.core_ui.R
import ru.bogdan.core_ui.ui.common.box.BoxWithBackgroundPhoto
import ru.bogdan.core_ui.ui.common.button.AppOutlinedButton
import ru.bogdan.core_ui.ui.common.checkbox.TransparentCheckbox
import ru.bogdan.core_ui.ui.common.loadingModifier.loadingShimmer
import ru.bogdan.core_ui.ui.theme.Beige
import ru.bogdan.core_ui.ui.theme.Emerald
import ru.bogdan.core_ui.ui.theme.LocalAppTypography
import ru.bogdan.core_ui.ui.theme.LocalSpacing
import ru.bogdan.machine_list.ui.machineItem.MachineItem
import ru.bogdan.machine_list.utils.getMachineListComponent


@Composable
fun MachineList(
    onMachineClick: (NavigationEvent.MachineScreen) -> Unit,
    onNavigateEvent: (NavigationEvent.LoginScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val component = getMachineListComponent()
    val viewModel: MachineListViewModel = viewModel(factory = component.getViewModelFactory())
    val state = viewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiAction.collect { action ->
            when (action) {
                is MachineListUiAction.GoToLoginScreen -> {
                    onNavigateEvent(NavigationEvent.LoginScreen)
                }
            }
        }
    }

    DisposableEffect(lifecycleOwner, component) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.handleIntent(MachineListIntent.RefreshData)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }

    }

    BoxWithBackgroundPhoto(
        drawableId = R.drawable.machines,
    ) {
        Column(modifier = modifier) {
            FilterBlock(
                state = state,
                modifier = Modifier.fillMaxWidth(),
                onFilterClick = {
                    viewModel.handleIntent(MachineListIntent.ChangeFilterShowState)
                },
                onModelFilterClicked = {
                    viewModel.handleIntent(MachineListIntent.SetMachineModelFilter(it))
                },
                onStateFilterClicked = {
                    viewModel.handleIntent(MachineListIntent.SetMachineStateFilter(it))
                },
                onDropFilterClicked = {
                    viewModel.handleIntent(MachineListIntent.DropFilters)
                },
                onApplyFilterClicked = {
                    viewModel.handleIntent(MachineListIntent.ApplyFilters)
                },
                onTypeFilterClicked = {
                    viewModel.handleIntent(MachineListIntent.SetMachineTypeFilter(it))
                },
                onYearsRAngeChanged = {
                    viewModel.handleIntent(MachineListIntent.SetMachineYearFilter(it))
                }
            )


            MachineListContent(
                state = state,
                modifier = Modifier
                    .padding(top = spacing.small)
                    .fillMaxSize(),
            ) {
                onMachineClick(NavigationEvent.MachineScreen(it))
            }
        }
    }
}


@Composable
fun MachineListContent(
    state: State<MachineListState>,
    modifier: Modifier = Modifier,
    onMachineItemClick: (String) -> Unit,
) {
    val spacing = LocalSpacing.current
    val state = state.value
    LazyColumn(
        modifier = modifier.loadingShimmer(state.isLoading),
        verticalArrangement = Arrangement.spacedBy(spacing.medium),
    ) {
        items(
            items = state.machines,
            key = { it.id }
        ) {
            MachineItem(
                onMachineClick = { machineId ->
                    onMachineItemClick(machineId)
                },
                machine = it,
            )
        }
    }
}


@Preview
@Composable
private fun PreviewMachineList() {
    MachineList(
        modifier = Modifier.fillMaxSize(),
        onMachineClick = {},
        onNavigateEvent = {},)
}

@Composable
fun FilterBlock(
    state: State<MachineListState>,
    onFilterClick: () -> Unit,
    onModelFilterClicked: (MachineModel) -> Unit,
    onStateFilterClicked: (MachineState) -> Unit,
    onTypeFilterClicked: (MachineType) -> Unit,
    onApplyFilterClicked: () -> Unit,
    onDropFilterClicked: () -> Unit,
    onYearsRAngeChanged: (IntRange) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = state.value
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .padding(spacing.small)
            .border(2.dp, Emerald)
            .padding(spacing.small),
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.End)
                .clickable {
                    onFilterClick()
                },
            painter = painterResource(R.drawable.outline_filter_list_24),
            contentDescription = "filters",
            tint = Emerald,
        )
        AnimatedVisibility(state.isFiltersShow) {
            LazyColumn(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
            ) {
                item {
                    Spacer(modifier = Modifier.height(spacing.medium))
                    ModelFilter(
                        state = state,
                    ) {
                        onModelFilterClicked(it)
                    }
                    Spacer(modifier = Modifier.height(spacing.medium))
                    StateMachineFilter(
                        state = state
                    ) {
                        onStateFilterClicked(it)
                    }
                    Spacer(modifier = Modifier.height(spacing.medium))
                    TypeMachineFilter(
                        state = state
                    ) {
                        onTypeFilterClicked(it)
                    }
                    Spacer(modifier = Modifier.height(spacing.medium))
                    YearsRangeFilter(
                        state = state,
                    ) {
                        onYearsRAngeChanged(it)
                    }
                    Spacer(modifier = Modifier.height(spacing.medium))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        AppOutlinedButton(
                            onClick = {
                                onApplyFilterClicked()
                            },
                            color = Emerald,
                        ) {
                            Text(text = stringResource(R.string.apply_filter), color = Emerald)
                        }
                        AppOutlinedButton(
                            onClick = {
                                onDropFilterClicked()
                            },
                            color = Emerald,
                        ) {
                            Text(text = stringResource(R.string.drop_filter), color = Emerald)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModelFilter(
    state: MachineListState,
    modifier: Modifier = Modifier,
    onFilterChanged: (MachineModel) -> Unit,
) {
    val spacing = LocalSpacing.current
    val filters = state.filterState.modeFilter
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.moldel_filter),
            style = LocalAppTypography.current.headlineCursiveLarge,
            color = Emerald
        )
        MachineModel.entries.forEach { entry ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TransparentCheckbox(
                    checked = entry in filters,
                    onCheckedChange = {
                        onFilterChanged(entry)
                    }
                )
                Spacer(modifier = Modifier.width(spacing.medium))
                Text(
                    text = entry.model,
                    color = Emerald,
                    style = LocalAppTypography.current.bodySmall,
                )
            }
        }
    }
}

@Composable
fun StateMachineFilter(
    state: MachineListState,
    modifier: Modifier = Modifier,
    onFilterChanged: (MachineState) -> Unit,
) {
    val spacing = LocalSpacing.current
    val filters = state.filterState
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.state_filter),
            style = LocalAppTypography.current.headlineCursiveLarge,
            color = Emerald
        )
        MachineState.entries.forEachIndexed { index, entry ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TransparentCheckbox(
                    checked = entry in filters.stateFilter,
                    onCheckedChange = {
                        onFilterChanged(entry)
                    }
                )
                Spacer(modifier = Modifier.width(spacing.medium))
                Text(
                    text = filters.stateDescriptions[index],
                    color = Emerald,
                    style = LocalAppTypography.current.bodySmall,
                )
            }
        }
    }
}

@Composable
fun TypeMachineFilter(
    state: MachineListState,
    modifier: Modifier = Modifier,
    onFilterChanged: (MachineType) -> Unit,
) {
    val spacing = LocalSpacing.current
    val filters = state.filterState
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.type_filter),
            style = LocalAppTypography.current.headlineCursiveLarge,
            color = Emerald
        )
        MachineType.entries.forEachIndexed { index, entry ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TransparentCheckbox(
                    checked = entry in filters.typeFilter,
                    onCheckedChange = {
                        onFilterChanged(entry)
                    }
                )
                Spacer(modifier = Modifier.width(spacing.medium))
                Text(
                    text = filters.typeDescriptions[index],
                    color = Emerald,
                    style = LocalAppTypography.current.bodySmall,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YearsRangeFilter(
    state: MachineListState,
    modifier: Modifier = Modifier,
    onRangeChanged: (IntRange) -> Unit,
) {
    val spacing = LocalSpacing.current
    val filters = state.filterState
    val rangeSliderState = remember {
        RangeSliderState(
            activeRangeStart = filters.yearFilter.first.toFloat(),
            activeRangeEnd = filters.yearFilter.last.toFloat(),
            valueRange = filters.minYear.toFloat()..filters.maxYear.toFloat(),
        )
    }
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.years_filter),
            style = LocalAppTypography.current.headlineCursiveLarge,
            color = Emerald
        )
        rangeSliderState.onValueChangeFinished = {
            onRangeChanged(rangeSliderState.activeRangeStart.toInt()..rangeSliderState.activeRangeEnd.toInt())
        }
        RangeSlider(
            state = rangeSliderState,
            colors = SliderDefaults.colors().copy(
                activeTickColor = Emerald,
                activeTrackColor = Emerald,
                inactiveTickColor = Beige,
                inactiveTrackColor = Beige,
                thumbColor = Emerald,
            )
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = spacing.small),
            text = stringResource(
                R.string.years_range,
                rangeSliderState.activeRangeStart.toInt(),
                rangeSliderState.activeRangeEnd.toInt()
            ),
            color = Emerald,
        )
    }
}