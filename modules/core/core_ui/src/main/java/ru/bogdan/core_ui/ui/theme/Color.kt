package ru.bogdan.core_ui.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


val MainRed = Color(0xFF950303)
val LightRed = Color(0xFFFD5F5F)
val MainGray = Color(0xFF616161)
val LightGrey = Color(0xFFC0C0C0)
val Emerald = Color(0xFF1BAF89)
val Beige = Color(0xFFE8CF9B)
val Tiffany = Color(0xFF3DC093)
val NotWhite = Color(0xFFEFEFEF)

//Gradients
val MainGradient = Brush.radialGradient(
    listOf(
        Color.Black,
        Color.Black.copy(alpha = 0.6f),
        Color.Black,
        Color.Black,
        Color.Transparent,
    ),
    radius = 1500f,
)