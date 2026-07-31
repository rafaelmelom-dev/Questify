package com.example.questify.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val FontMarker: FontFamily = FontFamily.SansSerif
val FontFutura: FontFamily = FontFamily.SansSerif
val FontGeorgia: FontFamily = FontFamily.Serif
val FontTypewriter: FontFamily = FontFamily.Serif

val Typography = Typography(
    displayLarge = TextStyle(fontFamily = FontMarker, fontWeight = FontWeight.Black, fontSize = 65.sp),
    titleLarge = TextStyle(fontFamily = FontTypewriter, fontWeight = FontWeight.Bold, fontSize = 37.sp),
    titleMedium = TextStyle(fontFamily = FontFutura, fontWeight = FontWeight.Bold, fontSize = 30.sp),
    titleSmall = TextStyle(fontFamily = FontFutura, fontWeight = FontWeight.Bold, fontSize = 22.sp),
    bodyLarge = TextStyle(fontFamily = FontGeorgia, fontWeight = FontWeight.Normal, fontSize = 20.sp),
    bodyMedium = TextStyle(fontFamily = FontGeorgia, fontWeight = FontWeight.Normal, fontSize = 18.sp),
    labelLarge = TextStyle(fontFamily = FontTypewriter, fontWeight = FontWeight.Bold, fontSize = 25.sp),
)
