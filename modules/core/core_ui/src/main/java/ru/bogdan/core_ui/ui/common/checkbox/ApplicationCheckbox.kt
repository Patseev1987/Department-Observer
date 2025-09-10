package ru.bogdan.core_ui.ui.common.checkbox

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.bogdan.core_ui.ui.theme.Emerald

@Composable
fun TransparentCheckbox(
    checked: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = Emerald,
    onCheckedChange: ((Boolean) -> Unit),
) {
    Checkbox(
        modifier = modifier,
        enabled = enabled,
        colors = CheckboxDefaults.colors(

        ).copy(
            checkedBorderColor = color,
            checkedCheckmarkColor = color,
            checkedBoxColor = Color.Transparent,
            uncheckedBoxColor = Color.Transparent,
            uncheckedBorderColor = color,
        ),
        checked = checked,
        onCheckedChange = onCheckedChange,
    )
}