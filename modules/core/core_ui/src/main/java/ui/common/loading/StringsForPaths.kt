package ui.common.loading

import androidx.compose.ui.graphics.vector.PathParser

private const val  eye = "M773,148C528,367 202,324 4,148C51.32,100.68 110.48,64.35 176,39.88C365.96,-31.04 609.42,-2.19 773,148Z"

private const val leftOrion = "M192,33C142.88,131.23 142.39,181.51 199,261"

private const val rightOrion = "M604,43C672.41,126.33 629.76,234 626.5,244.5"

val paths = listOf(
    PathParser().parsePathString(eye).toPath(),
    PathParser().parsePathString(leftOrion).toPath(),
    PathParser().parsePathString(rightOrion).toPath(),
)