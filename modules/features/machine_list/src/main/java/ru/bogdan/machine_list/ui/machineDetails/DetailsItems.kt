package ru.bogdan.machine_list.ui.machineDetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.mechanic.MachineDocument
import domain.mechanic.Oil
import domain.mechanic.PartOfMachine
import ru.bogdan.core_ui.R
import ru.bogdan.core_ui.ui.theme.Emerald
import ru.bogdan.core_ui.ui.theme.LocalAppTypography
import ru.bogdan.core_ui.ui.theme.LocalSpacing


@Composable
fun OilItem(
    oil: Oil,
    modifier: Modifier = Modifier,
    color: Color = Color.Black
) {
    val spacing = LocalSpacing.current
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = oil.imageUrl,
                placeholder = painterResource(R.drawable.icon),
                contentDescription = oil.name,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.Crop,
            )
            Spacer(Modifier.width(spacing.medium))
            Text(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                style = LocalAppTypography.current.bodyNormal,
                text = oil.name,
                color = color
            )
        }
        Spacer(Modifier.height(spacing.medium))
        Text(
            modifier = Modifier.align(Alignment.Start),
            textAlign = TextAlign.Start,
            style = LocalAppTypography.current.bodySmall,
            text = oil.description,
            color = color
        )
    }
}

@Preview
@Composable
private fun OilItemPreview() {
    OilItem(
        oil = Oil.NONE,
    )
}

@Composable
fun PartItem(
    part: PartOfMachine,
    modifier: Modifier = Modifier,
    color: Color = Color.Black
) {
    val spacing = LocalSpacing.current
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.small)
        ) {
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                style = LocalAppTypography.current.bodyNormal,
                text = part.name,
                color = color
            )
            Spacer(Modifier.height(spacing.medium))
            Text(
                textAlign = TextAlign.Start,
                style = LocalAppTypography.current.bodySmall,
                text = part.description,
                color = color
            )
        }
    }
}

@Preview
@Composable
private fun PartItemPreview() {
    PartItem(
        part = PartOfMachine.NONE
    )
}

@Composable
fun DocItem(
    doc: MachineDocument,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = LocalAppTypography.current.bodyNormal,
            text = doc.name,
            color = Emerald,
        )
        Spacer(Modifier.height(spacing.medium))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = LocalAppTypography.current.bodySmall,
            text = doc.description,
            color = Emerald,
        )
    }
}
