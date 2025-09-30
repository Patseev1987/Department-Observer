package ru.bogdan.machine_list.ui.machineDetails

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import domain.mechanic.MachineDocument
import ru.bogdan.core_ui.R
import ru.bogdan.core_ui.ui.common.box.BoxWithBackgroundPhoto
import ru.bogdan.core_ui.ui.common.button.AppOutlinedButton
import ru.bogdan.core_ui.ui.common.loadingModifier.loadingShimmer
import ru.bogdan.core_ui.ui.theme.*
import ru.bogdan.machine_list.utils.getMachineDetailsComponent

@Composable
fun MachineDetails(
    machineId: String,
    modifier: Modifier = Modifier,
) {
    val component = getMachineDetailsComponent(machineId)
    val viewModel: MachineDetailsViewModel = viewModel(factory = component.getViewModelFactory())
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiAction.collect { action ->
            when (action) {
                is MachineDetailsUiAction.ShowToast -> {
                    Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    MachineDetailsContent(
        machineState = state,
        modifier = modifier,
        onStateClick = {
            viewModel.handleIntent(MachineDetailsIntent.SelectState(it))
        },
        onDismissRequest = {
            viewModel.handleIntent(MachineDetailsIntent.HideDialog)
        },
        onExpandChange = {
            viewModel.handleIntent(MachineDetailsIntent.ExpandChange(it))
        },
        onDismissRequestMenu = {
            viewModel.handleIntent(MachineDetailsIntent.HideStates)
        },
        onDocClicked = {
            viewModel.handleIntent(MachineDetailsIntent.ShowDialog(it))
        },
        onAcceptClicked = {
            viewModel.handleIntent(MachineDetailsIntent.SaveDoc)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MachineDetailsContent(
    machineState: State<MachineDetailState>,
    onStateClick: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onDismissRequestMenu: () -> Unit,
    onExpandChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onDocClicked: (MachineDocument) -> Unit,
    onAcceptClicked: () -> Unit,
) {
    val state = machineState.value
    val spacing = LocalSpacing.current
    val typography = LocalAppTypography.current

    BoxWithBackgroundPhoto(
        drawableId = R.drawable.machine_details_background
    ) {

        Column(
            modifier = modifier.loadingShimmer(state.isLoading),
        ) {
            AsyncImage(
                placeholder = painterResource(id = R.drawable.icon),
                modifier = Modifier
                    .border(
                        width = spacing.small,
                        color = state.color,
                        shape = RoundedCornerShape(spacing.medium)
                    )
                    .size(200.dp)
                    .clip(shape = RoundedCornerShape(spacing.medium))
                    .align(Alignment.CenterHorizontally),
                model = state.imageUrl,
                contentDescription = state.name,
                contentScale = ContentScale.FillBounds,
            )
            Spacer(modifier = Modifier.size(spacing.small))
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = state.name,
                        style = typography.headline1,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Emerald
                    )
                }
                item {
                    Text(
                        text = state.type,
                        textAlign = TextAlign.Center,
                        style = typography.bodyLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Emerald
                    )
                }
                item {
                    ExpandableMenu(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        expanded = state.expanded,
                        onExpandChange = onExpandChange,
                        onStateClick = onStateClick,
                        onDismissRequest = onDismissRequestMenu,
                        states = state.states,
                        label = stringResource(R.string.state),
                        stateValue = state.state,
                        textColor = Emerald,
                        labelColor = Emerald,
                    )
                }
                item {
                    Text(
                        text = stringResource(R.string.year_of_manufacture, state.yearOfManufacture),
                        style = typography.bodyNormal,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Emerald
                    )
                }
                item {
                    SheetWithTitleMapData(
                        title = stringResource(R.string.oils_title),
                        cornerRadius = 20.dp,
                        data = state.oils,
                        titleStyle = typography.headlineCursiveLarge.copy(color = Emerald),
                        borderColor = Emerald,
                        content = {
                            OilItem(it, color = Emerald)
                        },
                        color = Emerald
                    )
                }
                if (state.parts.isNotEmpty()) {
                    item {
                        SheetWithTitleMapData(
                            title = stringResource(R.string.parts_title),
                            data = state.parts,
                            cornerRadius = 20.dp,
                            titleStyle = typography.headlineCursiveLarge.copy(color = Emerald),
                            borderColor = Emerald,
                            content = {
                                PartItem(it, color = Emerald)
                            },
                            color = Emerald
                        )
                    }
                }
                if (state.docs.isNotEmpty()) {
                    item {
                        SheetWithTitle(
                            title = stringResource(R.string.doc_title),
                            data = state.docs,
                            cornerRadius = 20.dp,
                            titleStyle = typography.headlineCursiveLarge.copy(color = Emerald),
                            borderColor = Emerald,

                            content = {
                                DocItem(
                                    doc = it
                                )
                            }
                        ) {
                            onDocClicked(it)
                        }
                    }
                }
            }
        }
    }
    if (state.isShowDialog) {
        BasicAlertDialog(
            onDismissRequest = {
                onDismissRequest()
            },
        ) {
            Surface(
                modifier = Modifier.wrapContentHeight(),
                shape = RoundedCornerShape(spacing.medium),
                color = Beige
            ) {
                Column(
                    modifier = Modifier.padding(spacing.medium),
                ) {
                    Text(
                        text = stringResource(R.string.do_you_want, state.docName),
                    )
                    Spacer(modifier = Modifier.height(spacing.large))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        AppOutlinedButton(
                            onClick = onDismissRequest,
                            color = Color.Black,
                        ) {
                            Text(
                                text = stringResource(R.string.cancel),
                                color = Color.Black,
                                fontSize = 20.sp
                            )
                        }
                        AppOutlinedButton(
                            onClick = onAcceptClicked,
                            color = Color.Black,
                        ) {
                            Text(
                                text = stringResource(R.string.accept),
                                color = Color.Black,
                                fontSize = 20.sp
                            )
                        }
                    }
                }

            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableMenu(
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    onStateClick: (String) -> Unit,
    stateValue: String,
    states: List<String>,
    label: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black,
    containerColor: Color = Color.Transparent,
    labelColor: Color = Color.Black,
    menuContainerColor: Color = Beige
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandChange,
    ) {
        TextField(
            modifier = modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
            value = stateValue,
            onValueChange = {},
            textStyle = AppTypography().bodyLarge,
            readOnly = true,
            singleLine = true,
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    Icons.Filled.ArrowDropDown,
                    null,
                    modifier.rotate(if (expanded) 180f else 0f),
                    tint = textColor
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                cursorColor = Color.White,
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                focusedLabelColor = labelColor,
                unfocusedLabelColor = labelColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,

                ),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            containerColor = menuContainerColor,
        ) {
            states.forEach { state ->
                DropdownMenuItem(
                    text = { Text(state, style = MaterialTheme.typography.bodyLarge) },
                    onClick = { onStateClick(state) },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Preview
@Composable
fun ExpandableMenuPreview() {
    var ex by remember { mutableStateOf(false) }
    val list = listOf("Bob", "John", "Philip", "Jack")
    var s by remember { mutableStateOf(list[0]) }
    ExpandableMenu(
        expanded = ex,
        onExpandChange = {
            ex = !ex
        },
        onDismissRequest = {
            ex = false
        },
        onStateClick = {
            s = it
            ex = false
        },
        stateValue = s,
        states = list,
        label = "State",
        labelColor = Emerald,
        textColor = Emerald,
    )

}