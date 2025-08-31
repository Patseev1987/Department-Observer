package ru.bogdan.main_screen_feature.ui.listOfMachines

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import domain.mechanic.Machine

@Composable
fun ListOfMachines(
    machines: List<Machine>,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
) {
    Column(
        modifier = modifier.padding(paddingValues),

    ) {
        Row(
            modifier = Modifier. fillMaxWidth()
        ){

        }
    }
}

@Preview
@Composable
private fun PreviewListOfMachines() {
    ListOfMachines()
}