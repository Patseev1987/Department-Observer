package ru.bogdan.machine_list.ui.machineItem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.mechanic.Machine
import ru.bogdan.core_ui.ui.common.extansions.getColor
import ru.bogdan.core_ui.ui.theme.Beige
import ru.bogdan.core_ui.ui.theme.LocalAppTypography
import ru.bogdan.core_ui.ui.theme.LocalSpacing
import ru.bogdan.core_ui.R


@Composable
fun MachineItem(
    machine: Machine,
    onMachineClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    boarderWidth: Dp = 6.dp,
) {
    MachineItemContent(
        modifier = modifier,
        machine = machine,
        boarderWidth = boarderWidth,
        onMachineClick = onMachineClick,
    )
}

@Composable
fun MachineItemContent(
    modifier: Modifier,
    machine: Machine,
    onMachineClick: (String) -> Unit,
    boarderWidth: Dp
) {
    val spacing = LocalSpacing.current
    val typography = LocalAppTypography.current
    Card(
        modifier = modifier,
        onClick = { onMachineClick(machine.id) },
        border = BorderStroke(width = boarderWidth, color = machine.state.getColor()),
        colors = CardDefaults.cardColors(
            containerColor = Beige.copy(alpha = 0.7f),
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.medium)
        ) {
            AsyncImage(
                model = machine.imageUrl,
                placeholder = painterResource(R.drawable.icon),
                contentDescription = machine.name,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(35.dp)),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    style = typography.bodyLarge,
                    text = machine.model.model,
                )
                Spacer(Modifier.height(spacing.small))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    style = typography.bodyLarge,
                    text = machine.name,
                )
            }
        }
        Spacer(Modifier.height(spacing.small))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            style = typography.bodyNormal,
            text = machine.description,
        )
        Spacer(Modifier.height(spacing.small))
    }
}

@Preview
@Composable
private fun PreviewMachineItem() {
    MachineItem(
        machine = Machine.NONE,
        onMachineClick = {}
    )
}
