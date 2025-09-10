package ru.bogdan.machine_list.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.bogdan.machine_list.utils.getMachineLsListComponent
import ru.bogdan.core_ui.ui.theme.LocalSpacing
import ru.bogdan.core_ui.ui.theme.MainGradient
import ru.bogdan.machine_list.R

@Composable
fun MachineList(
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val component = getMachineLsListComponent()
    val viewModel: MachineListViewModel = viewModel(factory = component.getViewModelFactory())
    Box(
       modifier = modifier
    ){
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.machines),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = MainGradient
                )
        )

    }
}